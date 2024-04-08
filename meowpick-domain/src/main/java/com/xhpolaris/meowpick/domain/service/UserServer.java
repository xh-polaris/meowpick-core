package com.xhpolaris.meowpick.domain.service;

import com.xhpolaris.meowpick.domain.model.valobj.UserCmd;
import com.xhpolaris.meowpick.domain.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.repository.IUserRepository;
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
