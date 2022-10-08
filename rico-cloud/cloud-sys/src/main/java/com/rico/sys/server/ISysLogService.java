package com.rico.sys.server;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rico.api.entity.SysLog;
import com.rico.comm.Search;


/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author rico
 * @since 2020-07-15
 */
public interface ISysLogService extends IService<SysLog> {

	/**
	 * 日志分页列表
	 *
	 * @param search 搜索和分页对象
	 * @return 日志分页列表
	 */
	IPage<SysLog> listPage(Search search);
}
