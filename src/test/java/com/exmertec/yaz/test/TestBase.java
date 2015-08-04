package com.exmertec.yaz.test;

import com.exmertec.yaz.BaseDao;
import com.exmertec.yaz.model.User;
import com.exmertec.yaz.model.UserType;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public abstract class TestBase {
    @PersistenceContext
    protected EntityManager entityManager;

    protected Long prepareUser(String name) {
        return buildUser().name(name).save();
    }

    protected UserBuilder buildUser() {
        return new UserBuilder();
    }

    protected class UserDao extends BaseDao<User> {
        public UserDao() {
            super(TestBase.this.entityManager, User.class);
        }
    }

    protected class UserBuilder {
        User user = new User();

        public UserBuilder name(String name) {
            user.setName(name);
            return this;
        }

        public UserBuilder points(Integer points) {
            user.setPoints(points);
            return this;
        }

        public UserBuilder type(UserType type) {
            user.setType(type);
            return this;
        }

        public Long save() {
            UserDao userDao = new UserDao();
            userDao.save(user);

            return user.getId();
        }
    }
}
