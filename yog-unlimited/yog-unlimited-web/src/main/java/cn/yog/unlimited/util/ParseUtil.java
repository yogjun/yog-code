package cn.yog.unlimited.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import cn.yog.core.bean.Pager;
import cn.yog.core.bean.Result;
import cn.yog.core.bean.ResultCode;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ParseUtil {
    /**
     * 从服务获取到的Result里取出List
     * */
    public static <T> List<T> getListFromResultlist(Result result, Class<T> clazz){
        if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
            List<T> data = JSONObject.parseArray(result.getData().toString(), clazz);
            if (!CollectionUtils.isEmpty(data)) {
                return data;
            }
        }
        return Lists.newArrayList();
    }

    /**
     * 从服务获取到的Result里取出List
     * */
    public static <T> List<T> getListFromResultpage(Result result, Class<T> clazz){
        if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
            Pager page = JSONObject.parseObject(result.getData().toString(), Pager.class);
            if (!CollectionUtils.isEmpty(page.getDocs())) {
                List<T> data = JSONObject.parseArray(page.getDocs().toString(),clazz);
                return data;
            }
        }
        return Lists.newArrayList();
    }

    /**
     * 从服务获取到的Result里取出Pager
     * */
    public static <T> Pager<T> getPageFromResultpage(Result result, Class<T> clazz){
        List<T> data = Lists.newArrayList();
        long count = 0;
        if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
            Pager page = JSONObject.parseObject(result.getData().toString(), Pager.class);
            if (!CollectionUtils.isEmpty(page.getDocs())) {
               data = JSONObject.parseArray(page.getDocs().toString(),clazz);
            }
            count = (long) page.getCount();
        }
        Pager<T> p = new Pager(count, data);
        return p;
    }

    /**
     * 从服务获取到的Result里取出bean
     * */
    public static <T> T getBeanFromResultbean(Result result, Class<T> clazz){
        if (ResultCode.SUCCESS.getCode().equals(result.getCode()) && result.getData() != null) {
            return JSONObject.parseObject(result.getData().toString(), clazz);
        }
        return null;
    }
}
