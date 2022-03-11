package com.guilherme.cursospring.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	//Essa função serve pra não deixar caracteres em branco ou impróprios na url, caso o usuario digite
	public static String ajustarString (String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	public static List<Integer> converterStringEmLista (String s) {
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i< vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		
		return list;
		
	}
}
