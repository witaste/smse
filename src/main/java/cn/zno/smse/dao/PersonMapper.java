package cn.zno.smse.dao;

import cn.zno.smse.pojo.Person;
import cn.zno.smse.pojo.PersonExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PersonMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int countByExample(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int deleteByExample(PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int insert(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int insertSelective(Person record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    List<Person> selectByExample(PersonExample example);
    List<Person> selectByExample(PersonExample example ,RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") Person record, @Param("example") PersonExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table PERSON
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") Person record, @Param("example") PersonExample example);
}