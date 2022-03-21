package com.guilherme.cursospring.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.guilherme.cursospring.services.exceptions.FileException;

@Service
public class ImageService {
	
	//o BufferedImage indica que estamos tratando de um "tipo" imagem
	public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		//pega o nome do arquivo e sua extensão
		String extension = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		//Testa se é do formato jpg ou png, se não for, não aceita
		if(!extension.equals("jpg") && !extension.equals("png")) {
			throw new FileException("Apenas formatos JPG ou PNG são permitidos");
		}
		
		try {
			BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
			//Se for png, vai converter pra jpg
			if("png".equals(extension)) {
				image = pngToJpg(image);
			}
			
			return image;			
		}
		
		catch (IOException e) {
			throw new FileException("Erro ao ler arquivo de imagem");
		}
		
	}

	public BufferedImage pngToJpg(BufferedImage image) {
		//Esse código pra converter png pra jpg tem pronto na internet, só copiar e colar
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	
	//Esse código gera um Input Stream da imagem, porque o meu método que salva no S3 usa o InputStream
	public InputStream getInputStream(BufferedImage image, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

}
