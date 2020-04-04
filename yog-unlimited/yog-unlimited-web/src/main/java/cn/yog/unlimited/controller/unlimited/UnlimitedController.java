package cn.yog.unlimited.controller.unlimited;

import cn.yog.core.bean.Result;
import cn.yog.core.util.ResultUtil;
import cn.yog.unlimited.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "unlimited", description = "下棋控制层")
@RestController
public class UnlimitedController extends BaseController {


    @ApiOperation(value = "获取棋盘信息", response = Result.class)
    @GetMapping("/unlimitedBoard")
    @Profiled
    public Result getBoardInfo() {
        return ResultUtil.success();
    }



}
