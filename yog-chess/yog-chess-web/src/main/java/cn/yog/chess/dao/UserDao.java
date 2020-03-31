package cn.yog.chess.dao;

import cn.yog.chess.entity.user.User;
import cn.yog.chess.mapper.user.UserMapper;
import cn.yog.core.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends BaseDao<User> {
    @Autowired
    private UserMapper userMapper;
}
