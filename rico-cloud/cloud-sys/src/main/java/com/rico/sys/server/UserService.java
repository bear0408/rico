package com.rico.sys.server;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.rico.api.entity.User;

import com.rico.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author rico
 * @data 2021/12/6
 */
@Service
public class UserService {

    @Autowired
    private UserMapper mapper;

    public IPage<User> selectUserPage(Page<User> page, Integer state) {
        return mapper.selectUserList(page, state);
    }

    public List<User> selectByUser(){

        List<User> users=mapper.selectByUser();
        return users;
    }


    public User getUser(int id){
        return mapper.getUser(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void insertT(User user){
        mapper.insertT(user);
    }

    public void deleteById(int id){
        mapper.deleteById(id);
    }

    public void updateT(User user){
        mapper.updateT(user);
    }
}
