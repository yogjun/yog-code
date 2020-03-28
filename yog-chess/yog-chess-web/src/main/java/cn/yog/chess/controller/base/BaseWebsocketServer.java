package cn.yog.chess.controller.base;

import cn.yog.chess.controller.demo.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yog
 * @date：Created in 2020/3/28 23:05
 */
public abstract class BaseWebsocketServer {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    protected static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
}
