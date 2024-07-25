package com.gothaxcity.securedoc_be.event;

import com.gothaxcity.securedoc_be.entity.UserEntity;
import com.gothaxcity.securedoc_be.enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?, ?> data;
}
