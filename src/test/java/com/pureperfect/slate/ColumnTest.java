package com.pureperfect.slate;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pureperfect.slate.stubs.ColumnAnnotationStub;

/**
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class ColumnTest
{
	@Test
	public void testComparator()
	{
		// Make sure things are getting sorted in proper order
		List<Method> methods = Utils.findMethodsWithAnnotation(
				ColumnAnnotationStub.class, Column.class);

		Assert.assertEquals("getFirstName", methods.get(0).getName());
		Assert.assertEquals("getLastName", methods.get(1).getName());

		Collections.sort(methods, new ColumnComparator());

		Assert.assertEquals("getFirstName", methods.get(1).getName());
		Assert.assertEquals("getLastName", methods.get(0).getName());
	}
}
