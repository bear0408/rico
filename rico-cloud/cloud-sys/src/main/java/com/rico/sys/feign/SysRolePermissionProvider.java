package com.rico.sys.feign;


import com.rico.api.feign.ISysRolePermissionProvider;
import com.rico.comm.constant.ProviderConstant;
import com.rico.sys.server.ISysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * 远程调用获取角色权限信息
 *
 * @author pangu
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "角色权限远程调用")
public class SysRolePermissionProvider implements ISysRolePermissionProvider {

	private final ISysRolePermissionService sysRolePermissionService;

	@Override
	@GetMapping(ProviderConstant.PROVIDER_ROLE_PERMISSION)

	@ApiOperation(value = "获取菜单列表", notes = "根据角色ID获取菜单列表")
	//配置一级缓存
//	@Cached(name = "getPermission-", key = "#roleId", expire = 120)
	//缓存30秒钟自动刷新，从getUserById方法取一次，如果key在600秒内没有访问则不再自动刷新
//	@CacheRefresh(refresh = 120, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
//	//当缓存访问未命中的情况下，对并发进行的加载行为进行保护，同一个JVM中同一个key只有一个线程去加载，其它线程等待结果
//	@CachePenetrationProtect
	public List<String> getMenuIdByRoleId(String roleId) {
		log.error("feign调用getPermission, roleId:{}", roleId);
		List<String> permissionList = sysRolePermissionService.getMenuIdByRoleId(roleId);
		log.error("permissionList:{}", permissionList);
		return permissionList;
	}
}
