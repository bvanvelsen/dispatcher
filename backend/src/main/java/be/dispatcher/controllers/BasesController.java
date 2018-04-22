package be.dispatcher.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import be.dispatcher.domain.location.emergencybases.Base;
import be.dispatcher.domain.location.emergencybases.BaseType;
import be.dispatcher.repositories.BaseRespository;

@RestController
@RequestMapping("api/bases/")
public class BasesController {

	@Autowired
	private BaseRespository baseRespository;

	@RequestMapping(value = "{type}", method = RequestMethod.GET)
	public List<Base> getAllFireDepartments(@PathVariable("type") BaseType baseType) {
		return baseRespository.getAll(baseType);
	}
}