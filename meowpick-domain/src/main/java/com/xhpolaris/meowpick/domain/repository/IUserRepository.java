package com.xhpolaris.meowpick.domain.repository;


import com.xhpolaris.meowpick.domain.model.valobj.UserCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;

public interface IUserRepository {
    UserVO createUser(UserCmd.CreateCmd cmd);

    UserVO getById(String id);

    UserVO getByPhone(String phone);

    UserVO registry(LoginCmd.CreateCmd cmd);
}
