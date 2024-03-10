package com.xhpolaris.meowpick.domain.user.repository;


import com.xhpolaris.meowpick.domain.user.model.entity.LoginCmd;
import com.xhpolaris.meowpick.domain.user.model.entity.UserCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;

public interface IUserRepository {
    UserVO createUser(UserCmd.CreateCmd cmd);

    UserVO getById(String id);

    UserVO getByPhone(String phone);

    UserVO registry(LoginCmd.CreateCmd cmd);
}
