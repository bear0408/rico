package com.rico.sys.server;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.rico.api.entity.SysClient;
import com.rico.api.poi.SysClientPOI;
import com.rico.comm.Search;


import java.util.List;

/**
 * <p>
 * 客户端表 服务类
 * </p>
 *
 * @author rico
 * @since 2020-07-14
 */
public interface ISysClientService extends IService<SysClient> {

	/**
	 * 查询分页列表
	 *
	 * @param search 查询参数
	 * @return IPage
	 */
	IPage<SysClient> listPage(Search search);

	/**
	 * 设置状态
	 *
	 * @param ids    ID串，以逗号分隔
	 * @param status 状态
	 * @return boolean
	 */
	boolean status(String ids, String status);

	/**
	 * 导出
	 *
	 * @return List
	 */
	List<SysClientPOI> export();
}
