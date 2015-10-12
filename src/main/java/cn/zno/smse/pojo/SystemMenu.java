package cn.zno.smse.pojo;

import java.math.BigDecimal;

public class SystemMenu {
	
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.ID
     *
     * @mbggenerated
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.NAME
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.URL
     *
     * @mbggenerated
     */
    private String url;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.ICON
     *
     * @mbggenerated
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.SHOW
     *
     * @mbggenerated
     */
    private String show;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.PID
     *
     * @mbggenerated
     */
    private String pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column SMSE_MENU.SORT
     *
     * @mbggenerated
     */
    private BigDecimal sort;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.ID
     *
     * @return the value of SMSE_MENU.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.ID
     *
     * @param id the value for SMSE_MENU.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.NAME
     *
     * @return the value of SMSE_MENU.NAME
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.NAME
     *
     * @param name the value for SMSE_MENU.NAME
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.URL
     *
     * @return the value of SMSE_MENU.URL
     *
     * @mbggenerated
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.URL
     *
     * @param url the value for SMSE_MENU.URL
     *
     * @mbggenerated
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.ICON
     *
     * @return the value of SMSE_MENU.ICON
     *
     * @mbggenerated
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.ICON
     *
     * @param icon the value for SMSE_MENU.ICON
     *
     * @mbggenerated
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.SHOW
     *
     * @return the value of SMSE_MENU.SHOW
     *
     * @mbggenerated
     */
    public String getShow() {
        return show;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.SHOW
     *
     * @param show the value for SMSE_MENU.SHOW
     *
     * @mbggenerated
     */
    public void setShow(String show) {
        this.show = show == null ? null : show.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.PID
     *
     * @return the value of SMSE_MENU.PID
     *
     * @mbggenerated
     */
    public String getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.PID
     *
     * @param pid the value for SMSE_MENU.PID
     *
     * @mbggenerated
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column SMSE_MENU.SORT
     *
     * @return the value of SMSE_MENU.SORT
     *
     * @mbggenerated
     */
    public BigDecimal getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column SMSE_MENU.SORT
     *
     * @param sort the value for SMSE_MENU.SORT
     *
     * @mbggenerated
     */
    public void setSort(BigDecimal sort) {
        this.sort = sort;
    }
}