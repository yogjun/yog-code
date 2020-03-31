package cn.yog.core.dao;

import cn.yog.core.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import java.util.List;

public abstract class BaseDao<T> implements IDao<T> {

    @Autowired
    protected BaseMapper<T> mapper;

    public BaseMapper<T> getMapper() {
        return mapper;
    }

    /**
     * 根据主键进行查询,必须保证结果唯一
     * 单个字段做主键时,可以直接写主键的值
     * 联合主键时,key可以是实体类,也可以是Map
     */
    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    /**
     * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
     */
    @Override
    public List<T> select(T entity) {
        return mapper.select(entity);
    }

    /**
     * 根据实体类不为null的字段进行查询,条件全部使用=号and条件
     */
    @Override
    public T selectOne(T entity) {
        List<T> result = mapper.select(entity);
        if (!CollectionUtils.isEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    /**
     * example 自定义查询
     */
    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    /**
     * 插入一条数据
     * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     */
    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    /**
     * 批量插入数据
     */
    @Override
    public int saveList(List<T> entities) {
        return mapper.insertList(entities);
    }

    /**
     * 插入一条数据,只插入不为null的字段,不会影响有默认值的字段
     * 支持Oracle序列,UUID,类似Mysql的INDENTITY自动增长(自动回写)
     * 优先使用传入的参数值,参数值空时,才会使用序列、UUID,自动增长
     */
    @Override
    public int saveNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     */
    @Override
    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    /**
     * 根据实体类中字段不为null的条件进行删除,条件全部使用=号and条件
     */
    @Override
    public int delete(T entity) {
        return mapper.delete(entity);
    }

    /**
     * example 自定义删除
     */
    @Override
    public int deleteByExample(Object example) {
        return mapper.deleteByExample(example);
    }

    /**
     * 根据主键进行更新,这里最多只会更新一条数据
     * 参数为实体类
     */
    @Override
    public int updateByKey(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    /**
     * 根据主键进行更新
     * 只会更新不是null的数据
     */
    @Override
    public int updateByKeyNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 根据Exmaple条件更新全部字段
     */
    @Override
    public int updateByExample(T entity, Object example) {
        return mapper.updateByExample(entity, example);
    }

    /**
     * 根据Exmaple条件更新非空(null)字段
     */
    @Override
    public int updateByExampleNotNull(T entity, Object example) {
        return mapper.updateByExampleSelective(entity, example);
    }

    /**
     * 根据实体类不为null的字段查询总数,条件全部使用=号and条件
     */
    @Override
    public int selectCount(T entity) {
        return mapper.selectCount(entity);
    }

    /**
     * example 自定义查询总数
     */
    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

}
