package com.rico.sys.feign;

import com.rico.api.entity.SysDict;
import com.rico.api.feign.ISysDictProvider;
import com.rico.comm.api.Result;
import com.rico.comm.constant.ProviderConstant;
import com.rico.sys.server.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * 字典远程调用
 * @author pangu
 */
@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "字典远程调用")
public class SysDictProvider implements ISysDictProvider {

    private final ISysDictService sysDictService;

    @Override
    @ApiOperation(value = "字典值", notes = "根据code和dictKey获取值")
    @GetMapping(ProviderConstant.PROVIDER_DICT_VALUE)
    public Result<String> getValue(String code, String dictKey) {
        return sysDictService.getValue(code, dictKey);
    }

    @Override
    @ApiOperation(value = "字典列表", notes = "根据code获取字典列表")
    @GetMapping(ProviderConstant.PROVIDER_DICT_LIST)
    public Result<List<SysDict>> getList(String code) {
        return sysDictService.getList(code);
    }
}
