package com.pureperfect.slate.stubs;

import com.pureperfect.slate.Column;

/**
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class ColumnAnnotationStub
{
	private String firstName;

	private String middleName;

	private String lastName;

	@Column(2)
	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	@Column(1)
	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
}
