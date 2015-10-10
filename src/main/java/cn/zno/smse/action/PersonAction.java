package cn.zno.smse.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.Person;
import cn.zno.smse.service.PersonService;

public class PersonAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PersonService personService;

	private Person person;

	public String goAdd() {
		return "goAdd";
	}

	public String goDetail() {
		person = personService.getRecord(person);
		return "goDetail";
	}

	public String goEdit() {
		person = personService.getRecord(person);
		return "goEdit";
	}

	public String goList() {
		return "goList";
	}

	public String saveAdd() {
		jsonObject = personService.saveAdd(person);
		return JSON;
	}

	public String saveEdit() {
		jsonObject = personService.saveEdit(person);
		return JSON;
	}

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
