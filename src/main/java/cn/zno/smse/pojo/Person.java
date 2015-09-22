package cn.zno.smse.pojo;

import java.math.BigDecimal;
import java.util.Date;

import cn.zno.smse.common.util.DateUtil;

public class Person {
	
	// -----------------------------------
	// --------后追加字段------------------
	// -----------------------------------
	private Date start;
	private Date end;
	
	
	
    public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.NAME
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.AGE
     *
     * @mbggenerated
     */
    private BigDecimal age;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.BIRTHDAY
     *
     * @mbggenerated
     */
    private Date birthday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.TYPE
     *
     * @mbggenerated
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PERSON.SEX
     *
     * @mbggenerated
     */
    private String sex;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.ID
     *
     * @return the value of PERSON.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.ID
     *
     * @param id the value for PERSON.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.NAME
     *
     * @return the value of PERSON.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.NAME
     *
     * @param name the value for PERSON.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.AGE
     *
     * @return the value of PERSON.AGE
     *
     * @mbggenerated
     */
    public BigDecimal getAge() {
        return age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.AGE
     *
     * @param age the value for PERSON.AGE
     *
     * @mbggenerated
     */
    public void setAge(BigDecimal age) {
        this.age = age;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.BIRTHDAY
     *
     * @return the value of PERSON.BIRTHDAY
     *
     * @mbggenerated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.BIRTHDAY
     *
     * @param birthday the value for PERSON.BIRTHDAY
     *
     * @mbggenerated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.TYPE
     *
     * @return the value of PERSON.TYPE
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.TYPE
     *
     * @param type the value for PERSON.TYPE
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PERSON.SEX
     *
     * @return the value of PERSON.SEX
     *
     * @mbggenerated
     */
    public String getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PERSON.SEX
     *
     * @param sex the value for PERSON.SEX
     *
     * @mbggenerated
     */
    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age
				+ ", birthday=" + DateUtil.standardFormat(birthday) + ", type=" + type + ", sex=" + sex
				+ "]";
	}
}