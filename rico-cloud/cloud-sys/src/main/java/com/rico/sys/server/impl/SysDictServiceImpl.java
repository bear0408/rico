package com.rico.sys.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rico.api.entity.SysDict;
import com.rico.comm.Search;
import com.rico.comm.api.Result;

import com.rico.sys.mapper.SysDictMapper;
import com.rico.sys.server.ISysDictService;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-07-01
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    @Override
    public Result<String> getValue(String code, String dictKey) {
        LambdaQueryWrapper<SysDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDict::getCode, code);
        lambdaQueryWrapper.eq(SysDict::getDictKey, dictKey);
        return Result.data(baseMapper.selectOne(lambdaQueryWrapper).getDictValue());
    }

    @Override
    public Result<List<SysDict>> getList(String code) {
        LambdaQueryWrapper<SysDict> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysDict::getCode, code);
        return Result.data(baseMapper.selectList(lambdaQueryWrapper));
    }

    @Override
    public IPage<SysDict> listPage(Page page, Search search) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(search.getStartDate())) {
            queryWrapper.between(SysDict::getCreateTime, search.getStartDate(), search.getEndDate());
        }
        if (StrUtil.isNotBlank(search.getKeyword())) {
            queryWrapper.and(i -> i
                    .or().like(SysDict::getId, search.getKeyword())
                    .or().like(SysDict::getCode, search.getKeyword()));
        }
        queryWrapper.eq(SysDict::getParentId, 0);
        return this.page(page, queryWrapper);
    }
}
