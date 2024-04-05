package com.xhpolaris.meowpick;

import com.xhpolaris.meowpick.infrastructure.dao.UserDao;
import com.xhpolaris.meowpick.infrastructure.pojo.UserCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestClass {
    @Autowired
    UserDao userDao;

    @Test
    void fun() {
        List<UserCollection> all = userDao.findAll();
        System.out.println("hello");
    }

}