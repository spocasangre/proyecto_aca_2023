package com.example.project;

import com.example.project.image.FilesStorageService;
import freemarker.core.CommandLine;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.annotation.Resource;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
public class ProjectApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ProjectApplication.class);
	}

	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}

}
