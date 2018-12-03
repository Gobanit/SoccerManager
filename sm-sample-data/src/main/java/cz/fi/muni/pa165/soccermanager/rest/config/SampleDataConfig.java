package cz.fi.muni.pa165.soccermanager.rest.config;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.fi.muni.pa165.soccermanager.service.config.ServiceBeansConfig;

@Configuration
@Import(ServiceBeansConfig.class)
@ComponentScan(basePackageClasses = {SampleDataFacadeImpl.class})
public class SampleDataConfig {
    private static final Logger LOG = LoggerFactory.getLogger(SampleDataConfig.class);

    @Autowired
    private SampleDataFacade dataBoot;
    
    
    @PostConstruct
    public void initData() throws IOException {
        LOG.debug("initData()");
        dataBoot.initData();
    }
}