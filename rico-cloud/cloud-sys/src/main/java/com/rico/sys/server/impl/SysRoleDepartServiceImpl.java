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
package com.rico.sys.server.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rico.api.entity.SysRoleDepart;
import com.rico.comm.Search;
import com.rico.comm.util.PageUtil;

import com.rico.sys.mapper.SysRoleDepartMapper;
import com.rico.sys.server.ISysRoleDepartService;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 角色和部门关联表 服务实现类
 * </p>
 *
 * @author rico
 * @since 2021-04-05
 */
@Service
public class SysRoleDepartServiceImpl extends ServiceImpl<SysRoleDepartMapper, SysRoleDepart> implements ISysRoleDepartService {

		@Override
		public IPage<SysRoleDepart> listPage(Search search) {
			LambdaQueryWrapper<SysRoleDepart> queryWrapper = new LambdaQueryWrapper<>();
			if (StrUtil.isNotBlank(search.getStartDate())) {
				queryWrapper.between(SysRoleDepart::getCreateTime, search.getStartDate(), search.getEndDate());
			}
			if (StrUtil.isNotBlank(search.getKeyword())) {
				queryWrapper.like(SysRoleDepart::getId, search.getKeyword());
			}
			queryWrapper.orderByDesc(SysRoleDepart::getCreateTime);
			return this.baseMapper.selectPage(PageUtil.getPage(search), queryWrapper);
		}
}
