package cn.zno.smse.dao;

import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.pojo.SystemAccessPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemAccessPermissionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int countByExample(SystemAccessPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int deleteByExample(SystemAccessPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int insert(SystemAccessPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int insertSelective(SystemAccessPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    List<SystemAccessPermission> selectByExample(SystemAccessPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    SystemAccessPermission selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SystemAccessPermission record, @Param("example") SystemAccessPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SystemAccessPermission record, @Param("example") SystemAccessPermissionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemAccessPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_ACCESS_PERMISSION
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemAccessPermission record);
}