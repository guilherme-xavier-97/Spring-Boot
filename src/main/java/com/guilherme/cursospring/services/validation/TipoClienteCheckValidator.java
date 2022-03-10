package com.guilherme.cursospring.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.guilherme.cursospring.domain.enums.TipoCliente;
import com.guilherme.cursospring.dto.ClienteTelefoneEnderecoDTO;
import com.guilherme.cursospring.resources.exceptions.FieldMessage;
import com.guilherme.cursospring.services.validation.utils.BR;

public class TipoClienteCheckValidator implements ConstraintValidator<TipoClienteCheck, ClienteTelefoneEnderecoDTO> {
	@Override
	public void initialize(TipoClienteCheck ann) {
	}

	@Override
	public boolean isValid(ClienteTelefoneEnderecoDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOucnpj())) {
			list.add(new FieldMessage("cpfOucnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOucnpj())) {
			list.add(new FieldMessage("cpfOucnpj", "CNPJ inválido"));
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