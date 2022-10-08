package com.rico.api.feign;

import com.rico.api.dto.UserInfo;
import com.rico.api.entity.SysUser;
import com.rico.comm.api.Result;
import com.rico.comm.constant.FeignConstant;
import com.rico.comm.constant.ProviderConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 用户调用类
 *
 * @author rico
 */
@FeignClient(name = FeignConstant.MATE_CLOUD_SYSTEM)
public interface ISysUserProvider {

    /**
     * 根据id查询用户信息
     *
     * @param id id
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_ID)
    Result<SysUser> getUserById(@RequestParam("id") Long id);

    /**
     * 根据userName查询用户信息
     * @param userName　用户名
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_USERNAME)
    Result<UserInfo> getUserByUserName(@RequestParam("userName") String userName);

    /**
     * 根据手机号查询用户信息
     * @param mobile　手机号码
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_USER_MOBILE)
    Result<UserInfo> getUserByMobile(@RequestParam("mobile") String mobile);

}
