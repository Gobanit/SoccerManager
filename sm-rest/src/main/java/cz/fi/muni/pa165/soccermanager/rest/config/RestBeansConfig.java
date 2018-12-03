package cz.fi.muni.pa165.soccermanager.rest.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cz.fi.muni.pa165.soccermanager.service.config.ServiceBeansConfig;

/**
 * Class configuring WebMVC for REST API
 * 
 * @author Michal Randak
 *
 */
@EnableWebMvc
@Configuration
@Import({ServiceBeansConfig.class, SampleDataConfig.class})
@ComponentScan(basePackages = {"cz.fi.muni.pa165.soccermanager.rest.controllers", "cz.fi.muni.pa165.soccermanager.rest.assemblers"})
public class RestBeansConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new AllowOriginInterceptor()); 
//    }
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    
}
