package com.xhpolaris.meowpick.infrastructure.mapstruct;

import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.infrastructure.pojo.UserCollection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMap {
    UserMap instance = Mappers.getMapper(UserMap.class);

    UserCollection cmd2db(UserCmd.CreateCmd cmd);
    UserCollection cmd2db(LoginCmd.CreateCmd cmd);

    UserVO db2vo(UserCollection db);
}
