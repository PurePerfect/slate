package com.pureperfect.slate;

import java.lang.reflect.Method;

/**
 * Sorts methods with {@link Column} annotations in ascending order.
 * 
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
class ColumnComparator implements java.util.Comparator<Method>
{
	@Override
	public int compare(Method o1, Method o2)
	{
		/*
		 * This should be safe as long as we are not getting passed methods
		 * without column annotations. XD
		 */
		return o1.getAnnotation(Column.class).value()
				- o2.getAnnotation(Column.class).value();
	}
}
