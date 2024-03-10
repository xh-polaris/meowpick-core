package com.xhpolaris.meowpick.common.authorize;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MeowUserDetailService extends UserDetailsService {
    @Override
    default UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
        return loadUserByToken(UserLoginEn.weapp, username);
    }

    MeowUser loadUserByToken(UserLoginEn loginType, String token);

    MeowUser loadUserById(String id);
}
