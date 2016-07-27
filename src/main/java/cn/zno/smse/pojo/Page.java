package cn.zno.smse.pojo;

import org.apache.ibatis.session.RowBounds;

public class Page {

	// easyUI 默认参数
	String page;
	String rows;

	// 分页信息
	public RowBounds getRowBounds() {
		int pageNo = Integer.parseInt((page == null || page == "0") ? "1" : page);
		int pageSize = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
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
}
