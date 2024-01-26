package com.xhpolaris.meowpick.domain.user.service;

import com.xhpolaris.meowpick.domain.user.model.entity.UserCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServer {
    private final IUserRepository userRepository;

    public UserVO createUser(UserCmd.CreateCmd cmd) {
        return userRepository.createUser(cmd);
    }

}
