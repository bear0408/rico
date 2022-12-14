package com.rico.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rico.api.entity.SysDepart;
import com.rico.api.poi.SysDepartPOI;
import com.rico.comm.api.Result;
import com.rico.comm.tree.ForestNodeMerger;
import com.rico.comm.util.CollectionUtil;
import com.rico.comm.util.ExcelUtil;

import com.rico.sys.server.ISysDepartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构表 前端控制器
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */

@RestController
@AllArgsConstructor
@RequestMapping("/depart")
@Api(tags = "部门管理")
public class SysDepartController extends BaseController {

    private final ISysDepartService sysDepartService;

    /**
     * 部门列表
     *
     * @param search 　根据关键字搜索
     * @return Result
     */
    @GetMapping("/list")
    @ApiOperation(value = "部门列表", notes = "部门列表，根据search查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
            @ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
            @ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
    })
    public Result<?> list(@ApiIgnore @RequestParam Map<String, Object> search) {
        return Result.data(sysDepartService.searchList(search));
    }

    /**
     * 部门树
     *
     * @return Result
     */

    @GetMapping("/tree")
    @ApiOperation(value = "部门树", notes = "部门树")
    public Result<?> tree() {
        return Result.data(ForestNodeMerger.merge(sysDepartService.tree()));
    }

    /**
     * 部门设置
     *
     * @param sysDepart 　SysDepart对象
     * @return Result
     */
    @PostMapping("/set")
    @ApiOperation(value = "部门设置", notes = "部门设置,支持新增或修改")
    public Result<?> set(@Valid @RequestBody SysDepart sysDepart) {
        return Result.condition(sysDepartService.saveOrUpdate(sysDepart));
    }

    /**
     * 部门信息
     *
     * @param id id
     * @return Result
     */
    @GetMapping("/get")
    @ApiOperation(value = "部门信息", notes = "部门信息,根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "菜单ID", paramType = "form"),
    })
    public Result<?> get(@RequestParam String id) {
        LambdaQueryWrapper<SysDepart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysDepart::getId, id);
        return Result.data(sysDepartService.getOne(queryWrapper));
    }

    /**
     * 部门删除
     *
     * @param ids 　多个id采用逗号分隔
     * @return Result
     */
    @PostMapping("/del")
    @ApiOperation(value = "部门删除", notes = "部门删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    public Result<?> del(@RequestParam String ids) {
        return Result.condition(sysDepartService.removeByIds(CollectionUtil.stringToCollection(ids)));
    }

    /**
     * 部门导出
     */
    @PostMapping("/export")
    @ApiOperation(value = "部门导出", notes = "部门导出")
    public void export(HttpServletResponse response) {
        List<SysDepartPOI> sysDepartPOIS = sysDepartService.export();
        //使用工具类导出excel
        ExcelUtil.exportExcel(sysDepartPOIS, null, "部门", SysDepartPOI.class, "depart", response);
    }
}

