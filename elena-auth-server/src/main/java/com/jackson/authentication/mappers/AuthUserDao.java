package com.jackson.authentication.mappers;

import com.jackson.authentication.models.vo.AuthUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author JieXing
 * @Description: 用户表dao
 * @date 2021/8/13
 * @time 22:31
 */
@Mapper
public interface AuthUserDao {
    AuthUserVO getUserInfoByAccountNum(@Param("accountNum") String accountNum);
}
