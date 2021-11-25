package com.nly.base.service;

import com.nly.util.PageBean;

import java.util.List;

/**
 * @program:boot-es7
 * @author: nly
 */
public interface IBaseESService <T,ID>{
    /**
     * 根据获取对像
     * @param entityId
     * @return
     */
    T get(ID entityId);

    /**
     * 新增
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 修改
     * @param entity
     * @return
     * @throws Exception
     */
    T update(T entity) throws Exception;

    /**
     * 删除
     * @param entityId
     * @return
     */
    T  delete(ID entityId);

    /**
     * 下拉框
     * @param pageBean
     * @return
     * @throws Exception
     */
    List<T> selectCombox(PageBean pageBean) throws Exception;

    /**
     * 分页查询
     * @param pageBean
     * @return
     * @throws Exception
     * @throws Exception
     */
    PageBean search(PageBean pageBean) throws Exception, Exception;
    /**
     * 聚合
     * @return
     */
    Object aggregationCount();
}
