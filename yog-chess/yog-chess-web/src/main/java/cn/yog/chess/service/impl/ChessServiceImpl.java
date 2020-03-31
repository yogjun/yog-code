package cn.yog.chess.service.impl;

import cn.yog.chess.dao.UserDao;
import cn.yog.chess.entity.user.User;
import cn.yog.chess.service.ChessService;
import cn.yog.core.bean.Result;
import cn.yog.core.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zixiao
 * @version V1.0
 * @date 2019/7/4 15:12
 */
@Service
public class ChessServiceImpl implements ChessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private Jedis jedis;

    @Override
    public Result getUserInfo(Integer id) {
//        User user = userDao.selectByKey(id);
//        jedis.set("user","123456");
//        jedis.get("user");
//        return ResultUtil.success(user);
        User another = new User();
        another.setId(1);
        another.setRoomId("DPS007");
        another = userDao.selectOne(another);
        return ResultUtil.success(another);
//        return ResultUtil.success(jedis.get("user"));
    }

    @Override
    public Result getUserrColor(HttpServletRequest request) {
        User user = new User();
        user.setSessionId(request.getSession().getId());
        user = userDao.selectOne(user);
        return ResultUtil.success(user.getColor());
    }
}
