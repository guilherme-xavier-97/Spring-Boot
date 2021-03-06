package com.guilherme.cursospring.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.guilherme.cursospring.services.exceptions.FileException;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	//a classe MultipartFIle indica que um arquivo será passado na requisição. Então (acho) que esse primeiro método serve só pra preparar a requisição pra receber um arquivo
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			// Pega o nome do arquivo no meu diretório
			String fileName = multipartFile.getOriginalFilename();	
			// faz a leitura do arquivo
			InputStream inputStream = multipartFile.getInputStream();
			// da o tipo do arquivo
			String contentType = multipartFile.getContentType();

			return uploadFile(inputStream, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: "+ e.getMessage());
		}

	}

	//Esse segundo método é o que de fato salva a imagem (acho)
	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {

		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("iniciando upload");
			s3client.putObject(bucketName, fileName, inputStream, meta);
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter URL");
		}
	}
}