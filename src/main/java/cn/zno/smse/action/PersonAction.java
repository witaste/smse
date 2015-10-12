package cn.zno.smse.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.Person;
import cn.zno.smse.service.PersonService;

public class PersonAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PersonService personService;

	private Person person;

	public String initAdd() {
		return ADD;
	}

	public String initDetail() {
		person = personService.getRecord(person);
		return DETAIL;
	}

	public String initEdit() {
		person = personService.getRecord(person);
		return EDIT;
	}

	public String initList() {
		return LIST;
	}

	public String saveAdd() {
		jsonObject = personService.saveAdd(person);
		return JSON;
	}

	public String saveEdit() {
		jsonObject = personService.saveEdit(person);
		return JSON;
	}

	// 异步加载列表 
	public String getList() {
		jsonObject = personService.getList(person, getRowBounds());
		return JSON;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
