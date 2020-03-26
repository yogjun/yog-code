package cn.yog.chess.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class BaseController {

    Logger log = LoggerFactory.getLogger(this.getClass());
    
    protected ModelMap            model;
    @Resource
    protected HttpServletRequest  request;
    @Resource
    protected HttpServletResponse response;

    /**
     * 设置request
     */
    @ModelAttribute
    private final void initRequest(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 设置response
     */
    @ModelAttribute
    private final void initResponse(HttpServletResponse response) {
        this.response = response;
    }

    /**
     * 设置model
     */
    @ModelAttribute
    private final void initModelMap(ModelMap model) {
        this.model = model;
    }
}
