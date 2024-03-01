package com.xhpolaris.meowpick.support;

import com.xhpolaris.meowpick.domain.user.service.UserServer;
import com.xhpolaris.meowpick.security.authorize.MeowUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailServer implements UserDetailsService {
    private final UserServer server;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MeowUser("eicen", "eicen", "1", List.of());
    }
}
