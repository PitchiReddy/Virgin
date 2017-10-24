package com.virginvoyages;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableOAuth2Client
@EnableFeignClients
@ComponentScan(basePackages = "com.virginvoyages")
public class CrossReferenceServicesApplication implements CommandLineRunner {

	//@Autowired
	//private ApplicationContext appContext;
	
    public static void main(String[] args) throws Exception {
        new SpringApplication(CrossReferenceServicesApplication.class).run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
        
        /* 
         * Uncomment to see all loaded beans for troubleshooting ####
         */
        /*String[] beans = appContext.getBeanDefinitionNames();
        Arrays.sort(beans);
        System.out.println("\n\n\n#########################################");
        for (String bean : beans) {
            System.out.println(bean);
        }
        System.out.println("#########################################\n\n\n");   */  
        
       
       /*  Uncomment to see datasource */
         
        /*System.out.println("\n\n\n#########################################");
        System.out.println("Data Source URL ===>"+appContext.getEnvironment().getProperty("spring.datasource.url"));
        System.out.println("Data Source Username ===>"+appContext.getEnvironment().getProperty("spring.datasource.username"));
        System.out.println("Data Source Password ===>"+appContext.getEnvironment().getProperty("spring.datasource.password"));
        System.out.println("#########################################\n\n\n");*/
       
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
