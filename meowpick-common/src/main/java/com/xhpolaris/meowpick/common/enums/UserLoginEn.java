package com.xhpolaris.meowpick.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserLoginEn implements BaseEnum {
    meowchat("中台", Name.meowchat,-5),
    password("账号密码", Name.email, -4),
    email("邮件", Name.email, -3),
    phone("电话", Name.phone, -2),
    weapp("微信小程序", Name.weapp, -1),
    wx("微信", Name.wx, 0),
    qq("QQ", Name.qq, 1),
    tiktok("抖音", Name.tiktok, 2),
    weibo("微博", Name.weibo, 3),
    apple("苹果", Name.apple, 4),
    google("谷歌", Name.google, 5),
    facebook("脸书", Name.facebook, 6),
    twitter("推特", Name.twitter, 7),
    ;
    private final String  msg;
    private final String  value;
    private final Integer code;

    public interface Name {
        String meowchat = "UserLoginEn.meowchat";
        String password = "UserLoginEn.usernamePassword";
        String email    = "UserLoginEn.email";
        String phone    = "UserLoginEn.phone";
        String weapp    = "UserLoginEn.weapp";
        String wx       = "UserLoginEn.wx";
        String qq       = "UserLoginEn.qq";
        String tiktok   = "UserLoginEn.tiktok";
        String weibo    = "UserLoginEn.weibo";
        String apple    = "UserLoginEn.apple";
        String google   = "UserLoginEn.google";
        String facebook = "UserLoginEn.facebook";
        String twitter  = "UserLoginEn.twitter";
    }
}
