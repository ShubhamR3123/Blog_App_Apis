package com.shubhamit.blog_app_api.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shubhamit.blog_app_api.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

	@Override
	public String uplodImage(String path, MultipartFile file) throws IOException {
		log.info("Initiated Dao Call for uplodImage");

		// file name
		String name = file.getOriginalFilename();

		// random name generate file
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

		// fullpath
		
		String filePath=path+File.separator+fileName1;
		
		//create folder if not created
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}

		
		//File copy
		
		Files.copy(file.getInputStream(),Paths.get(filePath));
		log.info("Completed Dao Call for uplodImage");
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		log.info("Initiated Dao Call for get resource");
		String fullPath=path+File.separator+fileName;
		
		InputStream is=new  FileInputStream(fullPath);
		log.info("Completed Dao Call for uplodImage");
		//db logic to return  inputStream
		return is;
	}

}
