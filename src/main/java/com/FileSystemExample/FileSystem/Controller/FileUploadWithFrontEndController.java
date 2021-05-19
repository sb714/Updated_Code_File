package com.FileSystemExample.FileSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadWithFrontEndController {

	@GetMapping("/files")
	ModelAndView fileUpload()
	{
		return new ModelAndView("index.html");
	}
	
	@GetMapping("/multipleUpload")
	ModelAndView MultipleFileUpload()
	{
		return new ModelAndView("multiple.html");
	}
}
