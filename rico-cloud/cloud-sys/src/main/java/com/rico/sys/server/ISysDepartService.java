package com.rico.sys.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rico.api.entity.SysDepart;
import com.rico.api.poi.SysDepartPOI;
import com.rico.api.vo.SysDepartVO;


import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface ISysDepartService extends IService<SysDepart> {

	/**
	 * 查询部门树
	 *
	 * @return 部门响应对象列表
	 */
	//@Cached(name="sysDepartService.tree", expire = 3600)
	List<SysDepartVO> tree();

	/**
	 * 查询部门列表
	 *
	 * @param search 查询条件
	 * @return 部门响应对象列表
	 */
	List<SysDepartVO> searchList(Map<String, Object> search);

	/**
	 * 部门导出
	 *
	 * @return 导出数据列表
	 */
	List<SysDepartPOI> export();

	/**
	 * 通过此部门id查询于此相关的部门ids
	 *
	 * @param deptId 部门ID
	 * @return ID列表
	 */
	List<Long> selectDeptIds(Long deptId);
}
