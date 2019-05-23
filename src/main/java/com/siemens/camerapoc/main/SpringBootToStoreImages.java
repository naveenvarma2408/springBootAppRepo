package com.siemens.camerapoc.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication(scanBasePackages = { "com.siemens" })
public class SpringBootToStoreImages {
	
	@Value("${pid.path}")
	private static String pidPath;

	public static void main(String[] args) {

		SpringApplicationBuilder app = new SpringApplicationBuilder(SpringBootToStoreImages.class)
				.web(WebApplicationType.SERVLET);
		app.build().addListeners(new ApplicationPidFileWriter("/opt/shutdown.pid"));
		app.run();

		// SpringApplication.run(SpringBootToStoreImages.class, args);
	}

}
