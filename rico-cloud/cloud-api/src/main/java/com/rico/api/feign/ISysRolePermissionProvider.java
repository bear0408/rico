package com.rico.api.feign;

import com.rico.comm.constant.FeignConstant;
import com.rico.comm.constant.ProviderConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * 角色权限远程调用接口
 * @author rico
 */
@FeignClient(name = FeignConstant.MATE_CLOUD_SYSTEM)
public interface ISysRolePermissionProvider {

    /**
     * 通过roleId获取权限列表
     * @param roleId　角色ID
     * @return List
     */
    @GetMapping(ProviderConstant.PROVIDER_ROLE_PERMISSION)
    List<String> getMenuIdByRoleId(@RequestParam("roleId") String roleId);
}
