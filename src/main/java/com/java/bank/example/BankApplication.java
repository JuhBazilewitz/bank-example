package com.java.bank.example;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class BankApplication {

	private final Environment env;

	private static final Logger log = LoggerFactory.getLogger(BankApplication.class);

	public BankApplication(Environment env){
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BankApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	@PostConstruct
	public void initApplication() {
		Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
		if (activeProfiles.contains("DEV")
				&& activeProfiles.contains("PROD")) {
			log.error("You have misconfigured your application! It should not run "
					+ "with both the 'dev' and 'prod' profiles at the same time.");
		}
	}

	private static void logApplicationStartup(Environment env) {

		String protocol = "http";

		if (env.getProperty("server.ssl.key-store") != null)
			protocol = "https";

		String serverPort = env.getProperty("server.port");

		String contextPath = env.getProperty("server.servlet.context-path");

		if (StringUtils.isBlank(contextPath))
			contextPath = "/";

		String hostAddress = "localhost";

		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}

		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URLs:\n\t" + "Local: \t\t{}://localhost:{}{}\n\t"
						+ "External: \t{}://{}:{}{}\n\t"
						+ "Profile(s): \t{}\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), protocol, serverPort, contextPath, protocol, hostAddress,
				serverPort, contextPath, env.getActiveProfiles());
	}
}
