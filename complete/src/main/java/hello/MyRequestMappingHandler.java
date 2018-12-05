package hello;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class MyRequestMappingHandler extends RequestMappingHandlerMapping {

	@Override
	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
		Map<RequestMappingInfo, HandlerMethod> mp = this.getHandlerMethods();
		HandlerMethod methodOld = mp.get(mapping);
		if (methodOld == null) {
			super.registerHandlerMethod(handler, method, mapping);
			return;
		}
		Annotation primaryNew = handler.getClass().getAnnotation(Primary.class);
		Annotation primaryOld = methodOld.getBeanType().getAnnotation(Primary.class);
		if (primaryOld != null)
			return;
		if (primaryNew != null) {
			super.unregisterMapping(mapping);
			super.registerMapping(mapping, handler, method);
			return;
		}
		super.registerMapping(mapping, handler, method);
	}

}
