package com.gothaxcity.securedoc_be.service.impl;

import com.gothaxcity.securedoc_be.entity.ConfirmationEntity;
import com.gothaxcity.securedoc_be.entity.CredentialEntity;
import com.gothaxcity.securedoc_be.entity.RoleEntity;
import com.gothaxcity.securedoc_be.entity.UserEntity;
import com.gothaxcity.securedoc_be.enumeration.Authority;
import com.gothaxcity.securedoc_be.enumeration.EventType;
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

import java.util.Map;

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

    private UserEntity createNewUser(String firstName, String lastName, String email) {
        var role = getRoleName(Authority.USER.name());
        return createUserEntity(firstName, lastName, email, role);
    }
}
