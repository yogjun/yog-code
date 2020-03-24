package cn.yog.chess.controller;

import cn.yog.core.bean.Result;
import cn.yog.core.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.perf4j.aop.Profiled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "cess", description = "下棋控制层")
@RestController
public class ChessController extends BaseController {

    @ApiOperation(value = "登录", response = Integer.class)
    @GetMapping("/login")
    @Profiled
    public Result login() {
        return ResultUtil.success();
    }

}
