package cn.zno.smse.common.interceptor;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zno.smse.common.constants.Constants;
import cn.zno.smse.common.util.DateUtil;
import cn.zno.smse.pojo.SystemUser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SpentTimeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	final Logger logger = LoggerFactory.getLogger(SpentTimeInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		SystemUser currentUser = (SystemUser) SecurityUtils.getSubject().getPrincipal();

		if (currentUser != null) {
			long t0 = System.currentTimeMillis();
			String result = invocation.invoke();
			logger.info("用户：[{}]，访问：[{}]，参数：[{}]，用时：[{}]{}", 
					currentUser.getName(),
					ServletActionContext.getRequest().getRequestURI(),
					JSONObject.fromObject(ActionContext.getContext().getParameters()),
					DateUtil.readableFormat(System.currentTimeMillis() - t0),
					Constants.LINE_SEPARATOR);
			return result;
		}
		
		return invocation.invoke();
	}

}
