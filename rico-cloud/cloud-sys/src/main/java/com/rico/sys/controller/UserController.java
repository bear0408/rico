package com.rico.sys.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.rico.api.entity.User;
import com.rico.sys.server.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author rico
 * @data 2021/12/6
 */
@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);



    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public  Object selectPage() {
        logger.info("成功调到了分页查询接口");
        Page<User> page = new Page(0, 10);
        IPage<User> p = userService.selectUserPage(page, 1);
        List<User> list=page.getRecords();
        return p;
    }

    @GetMapping("/selectByUser")
    public List<User> selectByUser(){
        return userService.selectByUser();
    }


    @GetMapping("/getUser")
   // @PostMapping("/getUser")
    public User getUser(@RequestParam(name="id") int id){
        return userService.getUser(id);
    }


    @GetMapping("/insertUser")
    public void insertT(User user){
        user=new User();
        user.setComment("很好");
        user.setName("张三");
        user.setDeleteFlag(1);
        userService.insertT(user);
    }

    @GetMapping("/deleteByUser")
    public void deleteById(int id){
        userService.deleteById(id);
    }

    @GetMapping("/updateUser")
    public void updateT(User user){
        user=new User();
        user.setId(1);
        user.setComment("very good");
        user.setName("李市长");
        userService.updateT(user);
    }

}
