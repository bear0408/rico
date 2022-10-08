package com.rico.sys.controller;

import com.rico.api.entity.SysUser;
import com.rico.api.poi.SysUserPOI;
import com.rico.comm.Search;
import com.rico.comm.api.Result;
import com.rico.comm.exception.BaseException;
import com.rico.comm.util.CollectionUtil;
import com.rico.comm.util.CryptoUtil;
import com.rico.comm.util.ExcelUtil;

import com.rico.sys.server.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(tags = "用户管理")
public class SysUserController extends BaseController {

    private final ISysUserService sysUserService;

    @Autowired
    private final PasswordEncoder passwordEncoder;


    /**
     * 用户列表
     *
     * @param search 　搜索关键词
     * @return Result
     */

    @GetMapping("/page")
    @ApiOperation(value = "用户列表", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", required = true, value = "当前页", paramType = "form"),
            @ApiImplicitParam(name = "size", required = true, value = "每页显示数据", paramType = "form"),
            @ApiImplicitParam(name = "keyword", required = true, value = "模糊查询关键词", paramType = "form"),
            @ApiImplicitParam(name = "startDate", required = true, value = "创建开始日期", paramType = "form"),
            @ApiImplicitParam(name = "endDate", required = true, value = "创建结束日期", paramType = "form"),
            @ApiImplicitParam(name = "prop", required = true, value = "排序属性", paramType = "form"),
            @ApiImplicitParam(name = "order", required = true, value = "排序方式", paramType = "form"),
    })
    public Result<?> page(Search search, SysUser sysUser) {
        return Result.data(sysUserService.listPage(search, sysUser));
    }

    /**
     * 设置用户，支持新增或修改
     *
     * @param sysUser 用户信息
     * @return Result
     */

    @PostMapping("/set")
    @ApiOperation(value = "设置用户", notes = "新增或修改用户")
    public Result<?> set(@Valid @RequestBody SysUser sysUser) {
        String password = sysUser.getPassword();
        if (StringUtils.isNotBlank(password) && sysUser.getId() == null) {
            password = passwordEncoder.encode(CryptoUtil.encodeMD5(password));
            sysUser.setPassword(password);
        }
        return Result.condition(sysUserService.saveOrUpdate(sysUser));
    }

    /**
     * 用户信息
     *
     * @param id Id信息
     * @return Result
     */

    @GetMapping("/get")
    @ApiOperation(value = "用户信息", notes = "根据ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "用户ID", paramType = "form"),
    })
    public Result<?> get(@RequestParam String id) {
        return Result.data(sysUserService.getById(id));
    }

    /**
     * 用户删除
     *
     * @param ids id字符串，根据,号分隔
     * @return Result
     */

    @PostMapping("/del")
    @ApiOperation(value = "用户删除", notes = "用户删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form")
    })
    public Result<?> del(@RequestParam String ids) {
        return Result.condition(sysUserService.removeByIds(CollectionUtil.stringToCollection(ids)));
    }

    /**
     * 设置用户状态
     *
     * @param ids    id字符串，根据,号分隔
     * @param status 状态标识，启用或禁用
     * @return Result
     */

    @PostMapping("/set-status")
    @ApiOperation(value = "用户状态", notes = "状态包括：启用、禁用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", required = true, value = "多个用,号隔开", paramType = "form"),
            @ApiImplicitParam(name = "status", required = true, value = "状态", paramType = "form")
    })
    public Result<?> setStatus(@RequestParam String ids, @RequestParam String status) {
        return Result.condition(sysUserService.status(ids, status));
    }

    /**
     * 设置用户密码
     *
     * @param user 用户信息
     * @return Result
     */

    @PostMapping("/set-password")
    @ApiOperation(value = "用户密码设置", notes = "用户密码设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", required = true, value = "用户ID", paramType = "form"),
            @ApiImplicitParam(name = "password", required = true, value = "密码", paramType = "form")
    })
    public Result<?> setPassword(@RequestBody SysUser user) {
        String pwd = null;
        if (StringUtils.isNotBlank(user.getPassword())) {
            pwd = passwordEncoder.encode(CryptoUtil.encodeMD5(user.getPassword()));
        }
        user.setPassword(pwd);
        if (user.getId() == null) {
            throw new BaseException("请求ID不能为空");
        }
        return Result.condition(sysUserService.updateById(user));
    }

    /**
     * 用户信息导出
     */

    @PostMapping("/export")
    @ApiOperation(value = "导出用户", notes = "导出用户")
    public void export(@ApiIgnore HttpServletResponse response) {
        List<SysUserPOI> sysUserPOIS = sysUserService.export();
        //使用工具类导出excel
        ExcelUtil.exportExcel(sysUserPOIS, null, "用户", SysUserPOI.class, "user", response);
    }
}
