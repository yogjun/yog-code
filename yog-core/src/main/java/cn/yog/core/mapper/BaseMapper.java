package cn.yog.core.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface BaseMapper<T> extends
        Mapper<T>,
        MySqlMapper<T> {

}
