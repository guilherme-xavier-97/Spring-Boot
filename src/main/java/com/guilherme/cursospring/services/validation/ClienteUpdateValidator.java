package com.guilherme.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.guilherme.cursospring.domain.Cliente;
import com.guilherme.cursospring.dto.ClienteDTO;
import com.guilherme.cursospring.repositories.ClienteRepository;
import com.guilherme.cursospring.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		//Esse if testa se o email informado é do próprio cliente ou um aleatório. Se for do mesmo, ele deixa repetir, se for outro, não
		if(aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "O email informado já está sendo utilizado"));
		}

		//Esse for serve pra pegar a lista de erros personalizados que eu criei e mandar pra lista de erros do próprio Spring
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage())
					.addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}