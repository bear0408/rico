package com.rico.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rico.api.entity.SysDepart;
import com.rico.api.vo.SysDepartVO;


import java.util.List;

/**
 * <p>
 * 组织机构表 Mapper 接口
 * </p>
 *
 * @author xuzf
 * @since 2020-06-28
 */
public interface SysDepartMapper extends BaseMapper<SysDepart> {

    List<SysDepartVO> tree();

}
