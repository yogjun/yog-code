package cn.yog.chess.service;

import cn.yog.core.bean.Result;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public interface ChessService {

    /**
     * 查询客诉单列表
     */
    Result getUserInfo(Integer id);
    Result getUserrColor(HttpServletRequest request);
}
