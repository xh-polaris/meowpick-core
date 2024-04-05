package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.common.exceptions.BizException;
import com.xhpolaris.meowpick.domain.model.valobj.LoginCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.repository.IUserRepository;
import com.xhpolaris.meowpick.infrastructure.dao.UserDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.UserMap;
import com.xhpolaris.meowpick.infrastructure.pojo.UserCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepository implements IUserRepository {
    private final UserDao userDao;

    @Override
    public UserVO createUser(UserCmd.CreateCmd cmd) {
        UserCollection db = UserMap.instance.cmd2db(cmd);

        userDao.insert(db);
        return UserMap.instance.db2vo(db);
    }

    @Override
    public UserVO getById(String id) {
        return userDao.findById(id).map(UserMap.instance::db2vo).orElseThrow(BizException::NotFind);
    }

    @Override
    public UserVO getByPhone(String phone) {
        return Optional.ofNullable(userDao.findByPhone(phone))
                       .map(UserMap.instance::db2vo)
                       .orElseThrow(BizException::NotFind);
    }

    @Override
    public UserVO registry(LoginCmd.CreateCmd cmd) {
        UserCollection db = UserMap.instance.cmd2db(cmd);
        userDao.save(db);

        return UserMap.instance.db2vo(db);
    }

}
