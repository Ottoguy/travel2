package com.agency.travel2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Travel2Application implements org.springframework.boot.CommandLineRunner{

	Logger logger = LoggerFactory.getLogger(Travel2Application.class);
	@Autowired
	private YAMLConfig myConfig;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		//SpringApplication.run(Travel2Application.class, args);
		SpringApplication app = new SpringApplication(Travel2Application.class);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		//logger.trace("Logging at TRACE level");
		//logger.debug("Logging at DEBUG level");
		//logger.info("Logging at INFO level");
		//logger.warn("Logging at WARN level");
		//logger.error("Logging at ERROR level");

		logger.info("Application {} ({}) has started ...", myConfig.getName(), myConfig.getVersion());
		logger.info(myConfig.toString());

		logger.info("Env.JAVA_HOME = {}", env.getProperty("JAVA_HOME"));
		logger.info("Env.CUSTOM = {}", env.getProperty("CUSTOM"));
	}

}
