package hello;

import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomController {

	  @RequestMapping("/hello")
	    public String index() {
	        return "Custom Greetings from Spring Boot!";
	    }
}
