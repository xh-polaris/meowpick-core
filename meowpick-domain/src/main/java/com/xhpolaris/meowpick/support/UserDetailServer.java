package com.xhpolaris.meowpick.support;

import com.xhpolaris.meowpick.common.authorize.MeowUser;
import com.xhpolaris.meowpick.common.authorize.MeowUserDetailService;
import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.user.service.UserServer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailServer implements MeowUserDetailService {
    private final UserServer server;

    @Override
    public MeowUser loadUserByToken(UserLoginEn loginType, String token) {
        return new MeowUser("eicen", "eicen", "65e43a7aa6fd34617f043a8e", List.of());
    }
}
