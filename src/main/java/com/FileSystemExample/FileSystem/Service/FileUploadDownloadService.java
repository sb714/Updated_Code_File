package com.FileSystemExample.FileSystem.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadDownloadService {
	
	private Path fileStoragePath;
	private String fileStorageLocation;

	// file.storage.location:temp}  temparary location if "fileStorageLocation" this is null / empty
	public FileUploadDownloadService(@Value("${file.storage.location:temp}") String fileStorageLocation) {
		this.fileStorageLocation = fileStorageLocation;
		fileStoragePath = Paths.get(fileStorageLocation).normalize();
		try {
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Issue in creating file direcoty");
		}
	}

	// going to store file in above directory
	public String save(MultipartFile file) {
		String fileNAme = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		Path filePath = Paths.get(fileStoragePath + "\\" + fileNAme);
		// copy file
		try {
			Files.copy(file.getInputStream(), filePath , StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("Issue in Stroring the file" , e);
		}
		return fileNAme;
		
	}

	public org.springframework.core.io.Resource downLoadFile(String fileName) {
		org.springframework.core.io.Resource resource;
		Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
		try {
			resource = new UrlResource(path.toUri());
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("Issue in the reading the file",e);
		}
		if(resource.exists() && resource.isReadable())
		{
			return resource;
		}else 
		{
			throw new RuntimeException("file does not exist or readable");
		}
	}

}
