package com.xhpolaris.meowpick.infrastructure.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserActionMap {
    UserActionMap instance = Mappers.getMapper(UserActionMap.class);
}
