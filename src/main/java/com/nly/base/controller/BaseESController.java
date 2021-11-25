package com.nly.base.controller;

import com.nly.base.service.IBaseESService;
import com.nly.util.PageBean;
import com.nly.util.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**公共控制层
 * @program:boot-es7
 * @author: nly
 */
public  abstract class BaseESController<T, ID>{
    public abstract IBaseESService<T, ID> getBaseESService();

    /**
     * 根据ID获取对象
     * @param entityId
     * @return
     */
    @GetMapping(value = "/get", produces = "application/json; charset=UTF-8")
    public Object get(ID entityId) {
        T entity = getBaseESService().get(entityId);
        return R.result(entity);
    }

    /**
     * 新增
     * @param entity
     * @return
     */
    @PostMapping(value = "/save", produces = "application/json; charset=UTF-8")
    public Object save(@RequestBody T entity) {
        entity = getBaseESService().save(entity);
        return R.result(entity);
    }

    /**
     * 修改
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/update", produces = "application/json; charset=UTF-8")
    public Object update(@RequestBody T entity) throws Exception {
        entity=getBaseESService().update(entity);
        return R.result(entity);
    }

    /**
     * 删除
     * @param entityId
     * @return
     */
    @GetMapping(value = "/delete", produces = "application/json; charset=UTF-8")
    public Object delete(ID entityId) {
        T entity = getBaseESService().delete(entityId);
        return R.result(entity);
    }

    /**
     * 下拉框
     * @param pageBean
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/selectCombox", produces = "application/json; charset=UTF-8")
    public Object selectCombox(@RequestBody PageBean pageBean) throws Exception {
        return R.result(getBaseESService().selectCombox(pageBean));
    }

    /**
     * 分页查询
     * @param pageBean
     * @return
     * @throws Exception
     * @throws Exception
     */
    @PostMapping(value = "/search", produces = "application/json; charset=UTF-8")
    public Object search(@RequestBody PageBean pageBean) throws Exception, Exception {
        pageBean=getBaseESService().search(pageBean);
        return R.result(pageBean);
    }

    /**
     * 统计
     * @return
     */
    @PostMapping(value = "/aggregationCount", produces = "application/json; charset=UTF-8")
    public Object aggregationCount() {
        return R.result(getBaseESService().aggregationCount());
    }


}
