package com.rico.api.feign;

import com.rico.api.entity.SysDict;
import com.rico.comm.api.Result;
import com.rico.comm.constant.FeignConstant;
import com.rico.comm.constant.ProviderConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

/**
 * 字典远程调用接口类
 * @author rico
 */
@FeignClient(name = FeignConstant.MATE_CLOUD_SYSTEM)
public interface ISysDictProvider {

    /**
     * 根据code和dictKey获取值
     * @param code code
     * @param dictKey key
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_DICT_VALUE)
    Result<String> getValue(@RequestParam("code") String code, @RequestParam("dictKey") String dictKey);

    /**
     * 根据code获取字典列表
     * @param code　code
     * @return Result
     */
    @GetMapping(ProviderConstant.PROVIDER_DICT_LIST)
    Result<List<SysDict>> getList(@RequestParam("code") String code);

}
