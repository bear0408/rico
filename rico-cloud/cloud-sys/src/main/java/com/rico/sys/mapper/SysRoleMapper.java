package com.rico.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rico.api.entity.SysRole;
import com.rico.api.vo.SysRoleVO;


import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRoleVO> tree();

}
