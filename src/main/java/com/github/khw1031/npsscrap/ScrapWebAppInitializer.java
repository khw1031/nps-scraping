package com.github.khw1031.npsscrap;

import btworks.wskit.server.service.WCryptoServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ScrapWebAppInitializer {

    @Bean
    public ServletRegistrationBean<HttpServlet> countryServlet() {
        Map<String, String> param = new HashMap<>();
        param.put("properties", "src/main/resources/application.properties");
        ServletRegistrationBean<HttpServlet> servRegBean = new ServletRegistrationBean<>();
        servRegBean.setServlet(new WCryptoServlet());
        servRegBean.setInitParameters(param);
        servRegBean.addUrlMappings("/securetest");
        return servRegBean;
    }

}
