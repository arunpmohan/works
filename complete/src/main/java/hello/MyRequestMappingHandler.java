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

	// @Override
	// protected RequestMappingInfo getMappingForMethod(Method method, Class<?>
	// handlerType) {
	//
	// RequestMappingInfo mappingForMethod = super.getMappingForMethod(method,
	// handlerType);
	//
	// System.out.println(mappingForMethod.getName());
	//
	// // Check if this class extends a super. and that super is annotated with
	// @Controller.
	// Class superClass = handlerType.getSuperclass();
	//
	//
	// if (superClass.isAnnotationPresent(Controller.class)) {
	// // We have a super class controller.
	//
	// if (handlerType.isAnnotationPresent(Primary.class)) {
	// // We have a @Primary on the child.
	// return mappingForMethod;
	// }
	// } else {
	// // We do not have a super class, therefore we need to look for other
	// implementations of this class.
	// Map<String, Object> controllerBeans =
	// getApplicationContext().getBeansWithAnnotation(org.springframework.stereotype.Controller.class);
	//
	// List<Map.Entry<String, Object>> classesExtendingHandler =
	// controllerBeans.entrySet().stream().filter(e ->
	// AopUtils.getTargetClass(e.getValue()).getSuperclass().getName().equalsIgnoreCase(handlerType
	// .getName()) &&
	// !AopUtils.getTargetClass(e.getValue()).getName().equalsIgnoreCase(handlerType.getName()))
	// .collect(Collectors.toList());
	//
	//
	// if (classesExtendingHandler == null || classesExtendingHandler.isEmpty())
	// {
	// // No classes extend this handler, therefore it is the only one.
	// return mappingForMethod;
	// } else {
	// // Classes extend this handler,
	//
	// // If this handler is marked with @Primary and no others are then return
	// info;
	// List<Map.Entry<String, Object>> classesWithPrimary =
	// classesExtendingHandler
	// .stream()
	// .filter(e -> e.getValue().getClass().isAnnotationPresent(Primary.class)
	// &&
	// !AopUtils.getTargetClass(e.getValue().getClass()).getName().equalsIgnoreCase
	// (handlerType.getName()))
	// .collect(Collectors.toList());
	// if (classesWithPrimary == null || classesWithPrimary.isEmpty()) {
	// // No classes are marked with primary.
	// return null;
	// } else {
	// // One or more classes are marked with @Primary,
	//
	// if (classesWithPrimary.size() == 1 &&
	// AopUtils.getTargetClass(classesWithPrimary.get(0).getValue
	// ()).getClass().getName().equalsIgnoreCase(handlerType.getName())) {
	// // We have only one and it is this one, return it.
	// return mappingForMethod;
	// } else if (classesWithPrimary.size() == 1 &&
	// !AopUtils.getTargetClass(classesWithPrimary.get(0)
	// .getValue()).getClass().getName().equalsIgnoreCase(handlerType.getName()))
	// {
	// // Nothing.
	// } else {
	// // nothing.
	// }
	// }
	// }
	// }
	//
	//
	//
	// // If it does, and it is marked with @Primary, then return info.
	//
	// // else If it does not extend a super with @Controller and there are no
	// children, then return info;
	//
	// return null;
	// }
}
