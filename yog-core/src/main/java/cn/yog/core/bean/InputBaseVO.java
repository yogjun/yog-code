package cn.yog.core.bean;

import com.alibaba.fastjson.JSON;

/**
 * @author yog
 * @version:V1.0
 */
public class InputBaseVO {

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
