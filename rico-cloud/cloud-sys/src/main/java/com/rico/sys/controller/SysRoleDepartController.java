/*
 * Copyright 2020-2030, MateCloud, DAOTIANDI Technology Inc All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: pangu(7333791@qq.com)
 */
package com.rico.sys.controller;

import com.rico.api.entity.SysRoleDepart;
import com.rico.comm.Search;
import com.rico.comm.api.Result;
import com.rico.comm.util.CollectionUtil;
import com.rico.sys.server.ISysRoleDepartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

/**
 * <p>
 * 角色和部门关联表 前端控制器
 * </p>
 *
 * @author rico
 * @since 2021-04-05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys-role-depart")
@Api(value = "角色和部门关联表", tags = "角色和部门关联表接口")
public class SysRoleDepartController extends BaseController {

    private final ISysRoleDepartService sysRoleDepartService;

    /**
     * 分页列表
     *
     * @param search 　搜索关键词
     * @return Result
     */

    @GetMapping("/page")
    @ApiOperation(value = "角色和部门关联表列表", notes = "分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
        @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
        @ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
        @ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
        @ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
    })
    public Result<?> page(Search search) {
		return Result.data(sysRoleDepartService.listPage(search));
    }

    /**
     * 角色和部门关联表信息
     *
     * @param id Id
     * @return Result
     */

    @GetMapping("/get")
    @ApiOperation(value = "角色和部门关联表信息", notes = "根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "ID", paramType = "form"),
    })
    public Result<?> get(@RequestParam String id) {
		return Result.data(sysRoleDepartService.getById(id));
	}

    /**
    * 角色和部门关联表设置
    *
    * @param sysRoleDepart SysRoleDepart 对象
    * @return Result
    */

    @PostMapping("/set")
    @ApiOperation(value = "角色和部门关联表设置", notes = "角色和部门关联表设置,支持新增或修改")
    public Result<?> set(@Valid @RequestBody SysRoleDepart sysRoleDepart) {
		return Result.condition(sysRoleDepartService.saveOrUpdate(sysRoleDepart));
	}

    /**
    * 角色和部门关联表删除
    *
    * @param ids id字符串，根据,号分隔
    * @return Result
    */

    @PostMapping("/del")
    @ApiOperation(value = "角色和部门关联表删除", notes = "角色和部门关联表删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    public Result<?> del(@RequestParam String ids) {
		return Result.condition(sysRoleDepartService.removeByIds(CollectionUtil.stringToCollection(ids)));
	}
}

