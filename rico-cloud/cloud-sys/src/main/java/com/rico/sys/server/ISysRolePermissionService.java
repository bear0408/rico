package com.rico.sys.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rico.api.entity.SysRolePermission;


import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-02
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据角色查询菜单ID列表
     *
     * @param roleId 角色ID
     * @return 菜单ID列表
     */
    List<String> getMenuIdByRoleId(String roleId);

}
