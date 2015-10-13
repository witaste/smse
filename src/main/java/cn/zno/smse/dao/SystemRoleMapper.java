package cn.zno.smse.dao;

import cn.zno.smse.pojo.SystemRole;
import cn.zno.smse.pojo.SystemRoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SystemRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int countByExample(SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int deleteByExample(SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int insert(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int insertSelective(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    List<SystemRole> selectByExample(SystemRoleExample example);
    List<SystemRole> selectByExample(SystemRoleExample example,RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    SystemRole selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SystemRole record, @Param("example") SystemRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ROLE
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemRole record);
    
    List<SystemRole> selectByMenuId(String menuId);
    List<SystemRole> selectByUserId(String userId);
}