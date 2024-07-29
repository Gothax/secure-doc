package com.gothaxcity.securedoc_be.service.impl;

import com.gothaxcity.securedoc_be.cache.CacheStore;
import com.gothaxcity.securedoc_be.domain.RequestContext;
import com.gothaxcity.securedoc_be.dto.UserDto;
import com.gothaxcity.securedoc_be.entity.ConfirmationEntity;
import com.gothaxcity.securedoc_be.entity.CredentialEntity;
import com.gothaxcity.securedoc_be.entity.RoleEntity;
import com.gothaxcity.securedoc_be.entity.UserEntity;
import com.gothaxcity.securedoc_be.enumeration.Authority;
import com.gothaxcity.securedoc_be.enumeration.EventType;
import com.gothaxcity.securedoc_be.enumeration.LoginType;
import com.gothaxcity.securedoc_be.event.UserEvent;
import com.gothaxcity.securedoc_be.exception.ApiException;
import com.gothaxcity.securedoc_be.repository.ConfirmationRepository;
import com.gothaxcity.securedoc_be.repository.CredentialRepository;
import com.gothaxcity.securedoc_be.repository.RoleRepository;
import com.gothaxcity.securedoc_be.repository.UserRepository;
import com.gothaxcity.securedoc_be.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.gothaxcity.securedoc_be.utils.UserUtils.createUserEntity;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
//    private final BCryptPasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;
    private final CacheStore<String, Integer> userCache;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        var userEntity = userRepository.save(createNewUser(firstName, lastName, email));
        var credentialEntity = new CredentialEntity(password, userEntity);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTRATION, Map.of("key", confirmationEntity.getKey())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    @Override
    public void verifyAccountKey(String key) {
        ConfirmationEntity confirmationEntity = getUserConfirmation(key);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        UserEntity userEntityByEmail = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntityByEmail.getId());
        switch (loginType) {
            case LOGIN_ATTEMPT -> {
                if (userCache.get(userEntityByEmail.getEmail()) == null) {
                    userEntityByEmail.setLoginAttempts(0);
                    userEntityByEmail.setAccountNonLocked(true);
                }
                userEntityByEmail.setLoginAttempts(userEntityByEmail.getLoginAttempts() + 1);
                userCache.put(userEntityByEmail.getEmail(), userEntityByEmail.getLoginAttempts());
                if (userCache.get(userEntityByEmail.getEmail()) > 5) {
                    userEntityByEmail.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                userEntityByEmail.setAccountNonLocked(true);
                userEntityByEmail.setLoginAttempts(0);
                userEntityByEmail.setLastLogin(LocalDateTime.now());
                userCache.evict(userEntityByEmail.getEmail());
            }
        }
        userRepository.save(userEntityByEmail);
    }


    @Override
    public UserDto getUserByUserId(String userId) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public CredentialEntity getUserCredentialById(Long id) {
        return null;
    }

    private UserEntity getUserEntityByEmail(String email) {
        Optional<UserEntity> userByEmail = userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(()-> new ApiException("User not found"));
    }

    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByKey(key).orElseThrow(()-> new ApiException("Confirmation key not found"));
    }

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleName(Authority.USER.name());
        return createUserEntity(firstName, lastName, email, role);
    }


}
