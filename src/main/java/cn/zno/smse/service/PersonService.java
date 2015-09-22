package cn.zno.smse.service;

import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import cn.zno.smse.pojo.Person;

public interface PersonService {
	public Map<String, Object> saveAdd(Person person);

	public Map<String, Object> saveEdit(Person person);

	public Map<String, Object> getList(Person person, RowBounds rowBounds);

	public Person getRecord(Person person);
}
