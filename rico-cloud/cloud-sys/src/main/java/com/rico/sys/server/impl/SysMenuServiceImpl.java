package com.rico.sys.server.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rico.api.entity.SysMenu;
import com.rico.api.poi.SysMenuPOI;
import com.rico.api.vo.SysMenuVO;
import com.rico.comm.Search;
import com.rico.comm.tree.ForestNodeMerger;
import com.rico.comm.util.CollectionUtil;


import com.rico.sys.mapper.SysMenuMapper;

import com.rico.sys.server.ISysMenuService;
import com.rico.sys.util.TreeUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author xuzf
 * @since 2020-06-18
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Override
    public List<SysMenuVO> routes(String roleId) {
        //1. 获取用户的菜单列表，待扩展
        List<SysMenu> menus = this.baseMapper.routes("1");
        //2. 生成菜单树
        return ForestNodeMerger.merge(TreeUtil.buildTree(menus));
    }

    @Override
    public List<SysMenu> searchList(Search search) {
        LambdaQueryWrapper<SysMenu> lambda = Wrappers.<SysMenu>query().lambda();
        if (StrUtil.isNotBlank(search.getStartDate())) {
            lambda.between(SysMenu::getCreateTime, search.getStartDate(), search.getEndDate());
        }
        if (StrUtil.isNotBlank(search.getKeyword())) {
            lambda.like(SysMenu::getName, search.getKeyword()).or().like(SysMenu::getId, search.getKeyword());
        }
        lambda.orderByAsc(SysMenu::getSort);
        return this.baseMapper.selectList(lambda);
    }

    @Override
    public boolean saveAll(SysMenu sysMenu) {
        return saveOrUpdate(sysMenu);
    }

    @Override
    public boolean status(String ids, String status) {
        if (StrUtil.isNotBlank(ids)) {
            return false;
        }
        Collection<? extends Serializable> collection = CollectionUtil.stringToCollection(ids);
        for (Object id : collection) {
            SysMenu sysMenu = this.baseMapper.selectById(CollectionUtil.objectToLong(id, 0L));
            sysMenu.setStatus(status);
            this.baseMapper.updateById(sysMenu);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<SysMenuPOI> export() {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMenu::getIsDeleted, 0);
        queryWrapper.orderByAsc(SysMenu::getId);
        List<SysMenu> sysMenus = this.baseMapper.selectList(queryWrapper);
        return sysMenus.stream().map(sysMenu -> {
            SysMenuPOI sysMenuPOI = new SysMenuPOI();
            BeanUtils.copyProperties(sysMenu, sysMenuPOI);
            return sysMenuPOI;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean checkChild(Long id) {
        return this.getMenuChild(id) > 0L ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Long getMenuChild(Long menuId) {
        return this.baseMapper.selectCount(Wrappers.<SysMenu>lambdaQuery().select(SysMenu::getId).eq(SysMenu::getParentId, menuId));
    }
}
