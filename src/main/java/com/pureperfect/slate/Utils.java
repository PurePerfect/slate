package com.pureperfect.slate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Some helper methods.
 * 
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
class Utils
{
	static List<Method> findMethodsWithAnnotation(Object o,
			Class<? extends Annotation> annotationClass)
	{
		return findMethodsWithAnnotation(o.getClass(), annotationClass);
	}

	static List<Method> findMethodsWithAnnotation(Class<?> clazz,
			Class<? extends Annotation> annotationClass)
	{

		Method[] array = clazz.getMethods();

		LinkedList<Method> methods = new LinkedList<Method>();

		for (Method m : array)
		{
			if (m.getAnnotation(annotationClass) != null)
				methods.add(m);
		}

		return methods;
	}
}
