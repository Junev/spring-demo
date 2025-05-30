package com.example.mybatis.mapper;

import com.example.mybatis.model.SysUserRole;
import com.example.mybatis.model.SysUserRoleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SysUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    long countByExample(SysUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    int deleteByExample(SysUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    int insert(SysUserRole row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    int insertSelective(SysUserRole row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    List<SysUserRole> selectByExampleWithRowbounds(SysUserRoleExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    List<SysUserRole> selectByExample(SysUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    int updateByExampleSelective(@Param("row") SysUserRole row, @Param("example") SysUserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbg.generated Thu Sep 19 10:37:21 CST 2024
     */
    int updateByExample(@Param("row") SysUserRole row, @Param("example") SysUserRoleExample example);
}