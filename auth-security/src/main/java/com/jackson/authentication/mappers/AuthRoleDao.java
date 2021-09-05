package com.jackson.authentication.mappers;

import com.jackson.authentication.models.vo.AuthRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author JieXing
 * @Description: 角色表dao
 * @date 2021/8/13
 * @time 22:53
 */
@Mapper
public interface AuthRoleDao {
    List<AuthRoleVO> getRolesByUserId(@Param("userId") String userId);
}
