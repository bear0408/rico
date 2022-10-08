package com.rico.sys.util;


import com.rico.api.entity.MenuMeta;
import com.rico.api.entity.SysMenu;
import com.rico.api.vo.SysMenuVO;
import com.rico.comm.enums.MenuTypeEnum;
import org.springframework.beans.BeanUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 树型工具类
 *
 * @author rico
 */
public class TreeUtil {

    public static final String HIDE_MENU = "1";

    /**
     * 对象转树节点
     *
     * @param sysMenus 系统菜单
     * @return List
     */
    public static List<SysMenuVO> buildTree(List<SysMenu> sysMenus) {
        List<SysMenuVO> trees = new ArrayList<>();
        sysMenus.forEach(sysMenu -> {
            SysMenuVO tree = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, tree);
            MenuMeta meta = new MenuMeta();
            meta.setIcon(sysMenu.getIcon());
            meta.setTitle(sysMenu.getName());
            meta.setHideMenu(HIDE_MENU.equals(sysMenu.getHidden()));
            // 只有当菜单类型为目录的时候，如果是顶级，则强制修改为Layout
            if (sysMenu.getParentId() == -1L && MenuTypeEnum.DIR.getCode().equals(sysMenu.getType())) {
                tree.setComponent("Layout");
                tree.setRedirect("noRedirect");
                tree.setAlwaysShow(true);
            }
            tree.setMeta(meta);
            if (MenuTypeEnum.DIR.getCode().equals(sysMenu.getType())) {
                tree.setTypeName(MenuTypeEnum.DIR.getMessage());
            } else if (MenuTypeEnum.MENU.getCode().equals(sysMenu.getType())) {
                tree.setTypeName(MenuTypeEnum.MENU.getMessage());
            } else if (MenuTypeEnum.BUTTON.getCode().equals(sysMenu.getType())) {
                tree.setTypeName(MenuTypeEnum.BUTTON.getMessage());
            }
            trees.add(tree);
        });
        return trees;
    }
}
