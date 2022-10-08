package com.rico.sys.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rico.api.entity.SysLog;
import com.rico.comm.Search;
import com.rico.comm.util.PageUtil;

import com.rico.sys.mapper.SysLogMapper;
import com.rico.sys.server.ISysLogService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author rico
 * @since 2020-07-15
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {

	@Override
	public IPage<SysLog> listPage(Search search) {
		LambdaQueryWrapper<SysLog> queryWrapper = Wrappers.lambdaQuery();
		// 查询开始日期和结束日期
		queryWrapper.between(StrUtil.isNotBlank(search.getStartDate()), SysLog::getCreateTime, search.getStartDate(), search.getEndDate());
		// 关键词查询
		if (StrUtil.isNotBlank(search.getKeyword())) {
			queryWrapper.and(i -> i
					.or().like(SysLog::getTitle, search.getKeyword())
					.or().like(SysLog::getTraceId, search.getKeyword()));
		}
		//　字段排序
		queryWrapper.orderByDesc(SysLog::getCreateTime);
		return this.baseMapper.selectPage(PageUtil.getPage(search), queryWrapper);
	}
}
