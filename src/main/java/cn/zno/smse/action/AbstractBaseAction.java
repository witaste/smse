package cn.zno.smse.action;

import org.apache.ibatis.session.RowBounds;

import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractBaseAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	
	// easyUI 默认参数
	String page;
	String rows;
	
	// 返回 JSON 
	Object jsonObject;

	// 页面跳转
	static final String JSON = "json";
	static final String ADD = "add";
	static final String DETAIL = "detail";
	static final String EDIT = "edit";
	static final String LIST = "list";
	
	
	
	// 分页信息 
	public RowBounds getRowBounds(){
		int pageNo = Integer.parseInt((page == null || page == "0") ? "1": page);
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10": rows);
		return new RowBounds((pageNo - 1) * pageSize, pageSize);
	}
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public Object getJsonObject() {
		return jsonObject;
	}
	public void setJsonObject(Object jsonObject) {
		this.jsonObject = jsonObject;
	}
}
