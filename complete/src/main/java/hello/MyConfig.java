package hello;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@EnableWebMvc
public class MyConfig implements WebMvcConfigurer {

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {

	    return new MyRequestMappingHandler();
	}
}
