package cn.yog.core.util;

import com.google.common.collect.Lists;
import cn.yog.core.bean.Result;
import cn.yog.core.bean.ResultCode;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wenyuan
 * @date: 2019/1/15 3:41 PM
 * @version:V1.0
 */
public class ResultUtil {

    public static Result success(Object object) {
        return new Result(ResultCode.SUCCESS.getCode(), null, object);
    }

    public static <T> Result success(Object o, Class<T> clazz) {
        if (o == null) {
            return success(o);
        }
        T t;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(o, t);
        } catch (Exception e) {
            e.printStackTrace();
            return error();
        }
        return success(t);
    }

    /**
     * 集合信息
     */
    public static <T> Result successList(List list, Class<T> clazz) {
        if (CollectionUtils.isEmpty(list)) {
            list = Lists.newArrayList();
        }
        List<T> result = Lists.newArrayList();
        T       t;
        for (Object o : list) {
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(o, t);
                result.add(t);
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return success(result);
    }

    /**
     * 带分页信息
     */
    public static Result successPage(List docs, long count) {
        return new Result(docs, count);
    }

    public static Result successPage(List docs) {
        return successPage(docs, Long.parseLong(docs.size() + ""));
    }

    public static <T> Result successPage(List docs, Class<T> clazz) {
        return successPage(docs, clazz, Long.parseLong(docs.size() + ""));
    }

    public static <T> Result successPage(List docs, Class<T> clazz, long count) {
        if (CollectionUtils.isEmpty(docs)) {
            docs = Lists.newArrayList();
        }
        List<T> list = Lists.newArrayList();
        T       t;
        for (Object o : docs) {
            try {
                t = clazz.newInstance();
                BeanUtils.copyProperties(o, t);
                list.add(t);
            } catch (Exception e) {
                e.printStackTrace();
                return error();
            }
        }
        return successPage(list, count);
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS);
    }

    public static Result success(String msg) {
        Result<Object> result = new Result<Object>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Result error(Integer code, String msg) {
        Result<Object> result = new Result<Object>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(ResultCode resultCode) {
        Result<Object> result = new Result<Object>();
        result.setCode(resultCode.getCode());
        result.setMsg(resultCode.getMsg());
        return result;
    }

    public static Result error(String msg) {
        Result<Object> result = new Result<Object>();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }

    public static Result error() {
        return new Result(ResultCode.ERROR);
    }
}
