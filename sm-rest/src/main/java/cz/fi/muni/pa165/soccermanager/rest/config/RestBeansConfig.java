package cz.fi.muni.pa165.soccermanager.rest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
    
}
