package cn.zno.smse.common.security;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.SystemAccessPermission;
import cn.zno.smse.service.SystemService;

public class SectionFactory implements FactoryBean<Ini.Section>{

	public static final String PREMISSION_STRING = "authc, perms[%s]";
	
	private String iniConfig;
	
	@Autowired
	private SystemService systemService;
	
	@Override
	public Section getObject() throws Exception {
		Ini ini = new Ini();
		// 加载配置文件
		ini.load(iniConfig);
		Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
		// 加载数据库
		for(SystemAccessPermission permission : systemService.getPermissionAll()){
			section.put("/" + permission.getUrl(), String.format(PREMISSION_STRING, permission.getUrl()));
		}
		section.put("/**", "authc");
		return section;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getIniConfig() {
		return iniConfig;
	}

	public void setIniConfig(String iniConfig) {
		this.iniConfig = iniConfig;
	}
}
