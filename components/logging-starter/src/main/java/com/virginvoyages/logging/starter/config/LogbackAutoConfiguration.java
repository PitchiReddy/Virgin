package com.virginvoyages.logging.starter.config;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.catalina.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import ch.qos.logback.access.tomcat.LogbackValve;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogbackAutoConfiguration {
	
	@Bean
    @ConditionalOnProperty(name = "logback.path")
    public EmbeddedServletContainerCustomizer containerCustomizer(
            final @Value("${logback.path:}") String path) throws FileNotFoundException {
        final File file = ResourceUtils.getFile(path);
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (container instanceof TomcatEmbeddedServletContainerFactory) {
                    ((TomcatEmbeddedServletContainerFactory) container)
                            .addContextCustomizers(new TomcatContextCustomizer() {

                                @Override
                                public void customize(Context context) {
                                    LogbackValve logbackValve = new LogbackValve();
                                    logbackValve.setFilename(file.getAbsolutePath());
                                    context.getPipeline().addValve(logbackValve);
                                }

                            });
                }
            }
        };

    }

}