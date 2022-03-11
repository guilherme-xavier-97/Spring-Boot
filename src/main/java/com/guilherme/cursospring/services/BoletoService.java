package com.guilherme.cursospring.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.guilherme.cursospring.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto (PagamentoComBoleto pgto, Date InstanteDoPedido) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(InstanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(calendar.getTime());
		
	}
}
