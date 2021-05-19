package com.FileSystemExample.FileSystem.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.FileSystemExample.FileSystem.DTO.FileUploadResponse;
import com.FileSystemExample.FileSystem.Service.FileUploadDownloadService;

@RestController
public class FileUploadDownload {
	
	@Autowired
	private FileUploadDownloadService fileUploadDownloadService;
	
	
	public FileUploadDownload(FileUploadDownloadService fileUploadDownloadService) {
		this.fileUploadDownloadService = fileUploadDownloadService;
	}


	@PostMapping("single/upload")
	FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file)
	{
		String fileName = fileUploadDownloadService.save(file);
		
		// fromCurrentContextPath -> will return //// http://localhost:8080  after this will append download and then file name 
		//  http://localhost:8080/download/filename.jpg
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
		
		String contentType = file.getContentType();
		
		FileUploadResponse response = new FileUploadResponse(fileName , contentType , url);
		return response;
	}
	
	@GetMapping("/download/{fileName}")
	ResponseEntity<org.springframework.core.io.Resource> downLoadSingleFile(@PathVariable String fileName , HttpServletRequest request)
	{
		org.springframework.core.io.Resource resource = fileUploadDownloadService.downLoadFile(fileName);
		//MediaType contentType = MediaType.IMAGE_JPEG;
		//MediaType contentType = MediaType.APPLICATION_PDF; // this is hardcoded
		// to generate the media type randomly
		String mineType = null;
		try {
			mineType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mineType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(mineType))
				//.header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName="+resource.getFilename()) //// download
				.header(HttpHeaders.CONTENT_DISPOSITION,"inline;fileName="+resource.getFilename()) // direct open the file
				.body(resource);	
	}
	
	// MultipartFile file -> means taking files from the system 
	@PostMapping("multiple/upload")
	List<FileUploadResponse> multipleFileUpload(@RequestParam("files") MultipartFile[] files)
	{
		if(files.length > 7)
		{
			throw new RuntimeException("too many files selected");
		}
		List<FileUploadResponse> fileUploadResponse = new ArrayList<>();
		Arrays.asList(files).stream()
		.forEach(file -> {
			
			String fileName = fileUploadDownloadService.save(file);
			String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(fileName).toUriString();
			
			String contentType = file.getContentType();
			
			FileUploadResponse response = new FileUploadResponse(fileName , contentType , url);
			fileUploadResponse.add(response);
			
		});
		return fileUploadResponse;
	}

	@GetMapping("zipDownload")
	void zipDownload(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException
	{
		// zipOutstram = zipentry + zipentry
		try(ZipOutputStream zos = new ZipOutputStream(response.getOutputStream()))
		{
			Arrays.asList(files).stream()
			.forEach(file -> {
				org.springframework.core.io.Resource resource = fileUploadDownloadService.downLoadFile(file);
				ZipEntry zipentry = new ZipEntry(resource.getFilename());
				try {
					zipentry.setSize(resource.contentLength());
					zos.putNextEntry(zipentry);
					org.springframework.util.StreamUtils.copy(resource.getInputStream(), zos);
					zos.closeEntry();
				} catch (IOException e) {
					System.out.println("Some exception occure");
				}
			});
			zos.finish();
			
		}
		response.setStatus(200);
		response.addHeader(HttpHeaders.CONTENT_DISPOSITION,"inline;fileName=zipFile");
	}
	
	
}
