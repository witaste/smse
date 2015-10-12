package cn.zno.smse.dao;

import cn.zno.smse.pojo.SystemUser;
import cn.zno.smse.pojo.SystemUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int countByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int deleteByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int insert(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int insertSelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    List<SystemUser> selectByExample(SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    SystemUser selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SystemUser record, @Param("example") SystemUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_USER
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemUser record);
}