package com.xhpolaris.meowpick.infrastructure.repository;

import com.xhpolaris.meowpick.domain.user.model.entity.UserCmd;
import com.xhpolaris.meowpick.domain.user.model.valobj.UserVO;
import com.xhpolaris.meowpick.domain.user.repository.IUserRepository;
import com.xhpolaris.meowpick.infrastructure.dao.UserDao;
import com.xhpolaris.meowpick.infrastructure.mapstruct.UserMap;
import com.xhpolaris.meowpick.infrastructure.pojo.UserCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRepository extends BasicRepository<UserCollection, UserVO> implements IUserRepository {
    private final UserDao userDao;

    @Autowired
    private MongoTemplate template;

    @Override
    public UserVO createUser(UserCmd.CreateCmd cmd) {
        UserCollection db = UserMap.instance.cmd2db(cmd);

        userDao.insert(db);
        return UserMap.instance.db2vo(db);
    }

    @Override
    public UserVO getById(String id) {
        Optional<UserCollection> db = userDao.findById(id);
        return null;
    }

    public void test(){
        Query query = new Query();
        Criteria criteria = new Criteria();

        criteria.and("name").is("tom")
                .and("age").gt(20);

        query.addCriteria(criteria);

        Sort sort = Sort.by(Sort.Direction.ASC, "age");
        Pageable pageable = PageRequest.of(0, 2, sort);

        List<UserCollection> users = template.find(query.with(pageable), UserCollection.class);
        System.out.println(users);

    }
}
