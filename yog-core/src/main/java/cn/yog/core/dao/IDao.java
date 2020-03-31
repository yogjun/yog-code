package cn.yog.core.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDao<T> {

    T selectByKey(Object key);

    List<T> select(T entity);

    T selectOne(T entity);

    List<T> selectByExample(Object example);

    int save(T entity);

    int saveList(List<T> entities);

    int saveNotNull(T entity);

    int deleteByKey(Object key);

    int delete(T entity);

    int deleteByExample(Object example);

    int updateByKey(T entity);

    int updateByKeyNotNull(T entity);

    int updateByExample(T entity, Object example);

    int updateByExampleNotNull(T entity, Object example);

    int selectCount(T entity);

    int selectCountByExample(Object example);
}
