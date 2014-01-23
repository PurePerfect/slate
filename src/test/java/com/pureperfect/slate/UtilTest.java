package com.pureperfect.slate;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.pureperfect.slate.stubs.ColumnAnnotationStub;

/**
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class UtilTest
{
	@Test
	public void testFindMethodsWithAnnotation()
	{
		Object testObj = new ColumnAnnotationStub();

		List<Method> results = Utils.findMethodsWithAnnotation(testObj,
				Column.class);

		Assert.assertEquals(2, results.size());

		for (Method m : results)
		{
			Assert.assertTrue("getFirstName".equals(m.getName())
					|| "getLastName".equals(m.getName()));
		}
	}
}
