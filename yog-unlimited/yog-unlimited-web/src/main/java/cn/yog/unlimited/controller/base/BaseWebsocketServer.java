package cn.yog.unlimited.controller.base;

import cn.yog.unlimited.controller.demo.WebSocketServer;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author yog
 * @date：Created in 2020/3/28 23:05
 */
public abstract class BaseWebsocketServer {
    protected final static Logger logger = LoggerFactory.getLogger(BaseWebsocketServer.class.getName());
    protected static ConcurrentHashMap<String, CopyOnWriteArraySet<Session>> sessionPool = new ConcurrentHashMap<>();

    protected CopyOnWriteArraySet<Session> getSessionList(String key){
        return sessionPool.getOrDefault(key, Sets.newCopyOnWriteArraySet());
    }
}
