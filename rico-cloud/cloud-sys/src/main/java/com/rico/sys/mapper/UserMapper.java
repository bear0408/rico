package com.rico.sys.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.rico.api.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    IPage<User> selectUserList(Page<User> page, @Param("status")Integer state);

    @Select("select * from user_a")
    List<User> selectByUser();

    @Select("select * from user_a where id=#{id}")
    public User getUser(int id);

    @Insert("insert into user_a(name,comment,deleteFlag) values(#{name},#{comment},#{deleteFlag})")
    public void insertT(User user);

    @Delete("delete from user_a where id=#{id}")
    public void deleteById(int id);

    @Update("update user_a set name=#{name},sex=#{comment} where id=#{id}")
    public void updateT(User user);
}
