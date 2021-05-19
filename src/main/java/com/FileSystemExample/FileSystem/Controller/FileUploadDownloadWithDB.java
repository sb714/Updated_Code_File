/*package com.FileSystemExample.FileSystem.Controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.FileSystemExample.FileSystem.DTO.FileUploadResponse;
import com.FileSystemExample.FileSystem.Model.FileDocument;
import com.FileSystemExample.FileSystem.Repository.DocFileRepo;

@RestController
public class FileUploadDownloadWithDB {

	@Autowired
	private DocFileRepo docFileRepo;

	public FileUploadDownloadWithDB(DocFileRepo docFileRepo) {
		super();
		this.docFileRepo = docFileRepo;
	}

	@PostMapping("single/uploadDb")
	FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDocument fileDocument = new FileDocument();
		fileDocument.setFileName(fileName);
		try {
			fileDocument.setDocFile(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		docFileRepo.save(fileDocument);

		// fromCurrentContextPath -> will return //// http://localhost:8080 after this
		// will append download and then file name
		// http://localhost:8080/download/filename.jpg
		String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadDB/").path(fileName)
				.toUriString();

		String contentType = file.getContentType();

		FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
		return response;
	}

	@GetMapping("/download/{fileName}")
	ResponseEntity<byte[]> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {
		FileDocument doc = docFileRepo.findByFileName(fileName);

		// to generate the media type randomly

		String mineType = request.getServletContext().getMimeType(doc.getFileName());

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(mineType))
				// .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName="+resource.getFilename())
				// //// download
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + doc.getFileName()) // direct open the file
				.body(doc.getDocFile());
	}

}
*/