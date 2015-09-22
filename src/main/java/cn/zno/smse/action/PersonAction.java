package cn.zno.smse.action;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zno.smse.pojo.Person;
import cn.zno.smse.service.PersonService;

public class PersonAction extends AbstractBaseAction {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PersonService personService;

	private Person person;

	@Override
	public String goAdd() {
		return GOADD;
	}

	@Override
	public String goDetail() {
		person = personService.getRecord(person);
		return GODETAIL;
	}

	@Override
	public String goEdit() {
		person = personService.getRecord(person);
		return GOEDIT;
	}

	@Override
	public String goList() {
		return GOLIST;
	}

	@Override
	public String saveAdd() {
		jsonMap = personService.saveAdd(person);
		return JSON;
	}

	@Override
	public String saveEdit() {
		jsonMap = personService.saveEdit(person);
		return JSON;
	}

	@Override
	public String getList() {
		jsonMap = personService.getList(person, getRowBounds());
		return JSON;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
