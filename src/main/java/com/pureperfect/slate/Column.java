package com.pureperfect.slate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Specify the order in which columns will be displayed during mapped writing.
 * E.g:
 * 
 * <pre>
 * <code>
 * 	public class Person {
 * 
 * 		{@literal @}Column(1)
 * 		public String getFirstName() { ... } 
 * 
 * 		{@literal @}Column(2)
 * 		public String getLastName() { ... } 
 * 	} 
 * </code>
 * </pre>
 * 
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Column
{
	/**
	 * The column number.
	 * 
	 * @return The column number.
	 */
	int value();
}