package cn.yog.chess.controller.chess;

import cn.yog.chess.controller.base.BaseController;
import cn.yog.chess.dto.ChessBoardDto;
import cn.yog.chess.service.ChessService;
import cn.yog.core.bean.Result;
import cn.yog.core.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "chess", description = "下棋控制层")
@RestController
public class ChessController extends BaseController {

    @Autowired
    private ChessService chessService;

    @ApiOperation(value = "获取棋盘信息", response = Result.class)
    @GetMapping("/chessBoard")
    @Profiled
    public Result getBoardInfo() {
        return ResultUtil.success(new ChessBoardDto());
    }

    @ApiOperation(value = "获取session信息", response = Result.class)
    @GetMapping("/sessionId")
    @Profiled
    public Result getSessionId() {
        return ResultUtil.success(request.getSession().getId());
    }

    @ApiOperation(value = "获取user信息", response = Result.class)
    @GetMapping("/user/{id}")
    @Profiled
    public Result getUserInfo(@PathVariable Integer id) {
        return chessService.getUserInfo(id);
    }

    @ApiOperation(value = "获取user信息", response = Result.class)
    @GetMapping("/userColor")
    @Profiled
    public Result getUserrColor(HttpServletRequest request) {
        return chessService.getUserrColor(request);
    }


}
