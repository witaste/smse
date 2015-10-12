package cn.zno.smse.dao;

import cn.zno.smse.pojo.SystemMenu;
import cn.zno.smse.pojo.SystemMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int countByExample(SystemMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int deleteByExample(SystemMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int insert(SystemMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int insertSelective(SystemMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    List<SystemMenu> selectByExample(SystemMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    SystemMenu selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SystemMenu record, @Param("example") SystemMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SystemMenu record, @Param("example") SystemMenuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SystemMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table SMSE_MENU
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SystemMenu record);
}