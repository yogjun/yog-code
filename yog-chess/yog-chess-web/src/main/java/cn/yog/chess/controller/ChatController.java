package cn.yog.chess.controller;

import cn.yog.chess.controller.BaseController;
import cn.yog.chess.controller.websocket.ChatSocketServer;
import cn.yog.core.bean.Result;
import cn.yog.core.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Api(value = "chat", description = "聊天控制层")
@RestController
@RequestMapping("/chat")
public class ChatController extends BaseController {

    @Autowired
    private ChatSocketServer chatSocketServer;

    @GetMapping("/getInfo")
    @Profiled
    public Result imports(@PathParam(value="shipId") String shipId) throws Exception{
        for(int i=0;i<10;i++){
            Thread.sleep(500);
            chatSocketServer.sendTextMessage(shipId,"");
        }
        return ResultUtil.success();
    }

}
