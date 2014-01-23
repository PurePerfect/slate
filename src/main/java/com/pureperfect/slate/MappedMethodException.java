package com.pureperfect.slate;

/**
 * Thrown when there is an error with a column mapping annotation.
 * 
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class MappedMethodException extends RuntimeException
{
	private static final long serialVersionUID = 000400;

	public MappedMethodException(Throwable cause)
	{
		super(cause);
	}
}
