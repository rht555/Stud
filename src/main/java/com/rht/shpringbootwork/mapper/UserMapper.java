package com.rht.shpringbootwork.mapper;

import com.rht.shpringbootwork.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String value);
}
