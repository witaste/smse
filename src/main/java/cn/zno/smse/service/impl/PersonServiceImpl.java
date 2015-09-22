package cn.zno.smse.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zno.smse.common.constants.Constants;
import cn.zno.smse.common.util.StringUtil;
import cn.zno.smse.dao.PersonMapper;
import cn.zno.smse.pojo.Person;
import cn.zno.smse.pojo.PersonExample;
import cn.zno.smse.pojo.PersonExample.Criteria;
import cn.zno.smse.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PersonMapper personMapper;

	@Override
	public Map<String, Object> saveAdd(Person person) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String errorMsg = null;
		// 校验start
		if(person==null){
			errorMsg = "数据不存在！";
		}else if(StringUtil.isBlank(person.getName())){
			errorMsg = "请填写姓名！";
		}else if(StringUtil.isBlank(person.getSex())){
			errorMsg = "请填写性别！";
		}else if(person.getAge()==null){
			errorMsg = "请填写年龄！";
		}else if(person.getBirthday()==null){
			errorMsg = "请填写生日！";
		}else if(StringUtil.isBlank(person.getType())){
			errorMsg = "请填写身份证类型！";
		}
		if(errorMsg!=null){
			resultMap.put(Constants.ERROR, errorMsg);
			return resultMap;
		}
		// 校验end
		
		int result = personMapper.insertSelective(person);
		if(result==1){
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_INSERT);
		}else{
			resultMap.put(Constants.ERROR, Constants.ERROR_INSERT);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> saveEdit(Person person) {
		PersonExample personExample = new PersonExample();
		Criteria criteria = personExample.createCriteria();
		criteria.andIdEqualTo(person.getId());
		int result = personMapper.updateByExampleSelective(person, personExample);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		if(result==1){
			resultMap.put(Constants.SUCCESS, Constants.SUCCESS_UPDATE);
		}else{
			resultMap.put(Constants.ERROR, Constants.ERROR_UPDATE);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> getList(Person person, RowBounds rowBounds) {
		PersonExample personExample = new PersonExample();
		Criteria criteria = personExample.createCriteria();
		if (person != null) {
			if(person.getName()!=null){
				criteria.andNameLike("%" + person.getName() + "%");
			}
			if(person.getSex()!=null){
				criteria.andSexEqualTo(person.getSex());
			}
			if(person.getAge()!=null){
				criteria.andAgeEqualTo(person.getAge());
			}
			if(person.getStart()!=null){
				criteria.andBirthdayGreaterThanOrEqualTo(person.getStart());
			}
			if(person.getEnd()!=null){
				criteria.andBirthdayLessThan(person.getEnd());
			}
			if(person.getType()!=null){
				criteria.andTypeEqualTo(person.getType());
			}
			
		}

		Map<String, Object> dataMap = new HashMap<String, Object>();
		int cnt = personMapper.countByExample(personExample);
		List<Person> personList = personMapper.selectByExample(personExample,
				rowBounds);
		dataMap.put("total", cnt);
		dataMap.put("rows", personList);
		return dataMap;
	}

	@Override
	public Person getRecord(Person person) {
		PersonExample personExample = new PersonExample();
		Criteria criteria = personExample.createCriteria();
		criteria.andIdEqualTo(person.getId());
		List<Person> personList = personMapper.selectByExample(personExample);
		Person result = null;
		if(personList!=null && personList.size()==1){
			result = personList.get(0);
		}
		return result;
	}

}
