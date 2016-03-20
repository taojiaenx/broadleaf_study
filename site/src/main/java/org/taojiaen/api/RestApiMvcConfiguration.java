package org.taojiaen.api;

import org.broadleafcommerce.core.web.api.BroadleafRestApiMvcConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebMvc
@ComponentScan("org.taojiaen.api")
public class RestApiMvcConfiguration extends BroadleafRestApiMvcConfiguration {
    
}
