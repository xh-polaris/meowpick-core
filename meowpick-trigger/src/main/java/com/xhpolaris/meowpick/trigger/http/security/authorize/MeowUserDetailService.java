package com.xhpolaris.meowpick.trigger.http.security.authorize;

import com.xhpolaris.meowpick.common.enums.UserLoginEn;
import com.xhpolaris.meowpick.domain.user.MeowUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MeowUserDetailService extends UserDetailsService {
    @Override
    default UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
        return of(loadUserByToken(UserLoginEn.weapp, username));
    }

    MeowUser loadUserByToken(UserLoginEn loginType, String token);

    MeowUser loadUserById(String id);

    User of(MeowUser user);
}
