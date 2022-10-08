package com.rico.sys.server;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rico.api.entity.SysDict;
import com.rico.comm.Search;
import com.rico.comm.api.Result;



import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-01
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 根据编码和键获取值
     *
     * @param code
     * @param dictKey
     * @return
     */

    Result<String> getValue(String code, String dictKey);

    /**
     * 根据编码查询字典列表
     *
     * @param code
     * @return
     */

    Result<List<SysDict>> getList(String code);

    /**
     * 字典分页查询
     *
     * @param page
     * @param search
     * @return
     */
    IPage<SysDict> listPage(Page page, Search search);

}
