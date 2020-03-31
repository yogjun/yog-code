package cn.yog.chess.controller.chess;

import cn.yog.chess.controller.base.BaseWebsocketServer;
import cn.yog.chess.dao.UserDao;
import cn.yog.chess.dto.ChessAction;
import cn.yog.chess.entity.user.User;
import cn.yog.core.config.WebSocketConfig;
import cn.yog.core.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.tomcat.websocket.WsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yog
 * @date：Created in 2020/3/26 23:30
 */
@ServerEndpoint("/chessWs/{roomId}")
@Component
@RestController
public class ChessSocketServer extends BaseWebsocketServer {
    //连接的用户信息
    private Session session;
    private String roomId;

    private static UserDao userDao;

    // 注入的时候，给类的 service 注入
    @Autowired
    public void setChatService(UserDao userDao) {
        ChessSocketServer.userDao = userDao;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value="roomId") String roomId) {
        this.session = session;
        this.roomId = roomId;
        CopyOnWriteArraySet<Session> set = getSessionList(roomId);
        set.add(session);
        sessionPool.put(roomId,set);
        LogUtil.info(logger,"【websocket消息】有新的连接，用户为:{0}",session);

        User another = new User();
        another.setRoomId(roomId);
        another = userDao.selectOne(another);
        User user = new User();
        user.setSessionId(((WsSession)session).getHttpSessionId());
//        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        user.setRoomId(httpSession.getId());
        user.setRoomId(roomId);
        if(another==null) {
            user.setColor("b");
        }else {
            user.setColor("w");
        }
        userDao.save(user);
    }

    @OnClose
    public void onClose() {
//        getSessionList(roomId).remove(session);
        LogUtil.info(logger,"【websocket消息】连接断开，用户为:{0}",session);

        User user = new User();
        user.setSessionId(((WsSession)session).getHttpSessionId());
        user.setRoomId(roomId);
        user = userDao.selectOne(user);
        userDao.delete(user);
    }

    @OnMessage
    public void onMessage(String message) {
        ChessAction chessAction = (ChessAction) JSON.parseObject(message,ChessAction.class);
        sendRoomMessage(JSON.toJSONString(chessAction));
        LogUtil.info(logger,"【websocket消息】收到客户端消息，用户为:{0},信息为:{1}",session,message.toString());
    }

    /**
     * 在所在房间内广播信息
     * */
    public void sendRoomMessage(String message) {
        for(Session session : getSessionList(roomId)) {
            LogUtil.info(logger,"【websocket消息】在房间内广播信息，消息为:{0}",message.toString());
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
