package com.guilherme.cursospring.services;

import org.springframework.mail.SimpleMailMessage;

import com.guilherme.cursospring.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	void sendEmail(SimpleMailMessage msg);
}
