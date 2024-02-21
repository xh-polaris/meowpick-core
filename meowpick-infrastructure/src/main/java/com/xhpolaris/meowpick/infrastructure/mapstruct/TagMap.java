package com.xhpolaris.meowpick.infrastructure.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMap {
    TagMap instance = Mappers.getMapper(TagMap.class);
}
