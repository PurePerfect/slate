package com.pureperfect.slate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import junit.framework.Assert;

import org.junit.Test;

import com.pureperfect.slate.stubs.ColumnAnnotationStub;

/**
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class WriterTest
{
	@Test
	public void testFormat()
	{
		final Writer writer = new Writer(",");

		Assert.assertEquals("Hello World", writer.format("Hello World")
				.toString());

		Assert.assertEquals("\"\"Hello World", writer.format("\"Hello World")
				.toString());

		Assert.assertEquals("\"\"Hello World\"\"",
				writer.format("\"Hello World\"").toString());

		Assert.assertEquals("\"Hello, World\"", writer.format("Hello, World")
				.toString());

		Assert.assertEquals("\"Hello\n World\"", writer.format("Hello\n World")
				.toString());

		Assert.assertEquals("\"Hello\r World\"", writer.format("Hello\r World")
				.toString());

		Assert.assertEquals("\" Hello World\"", writer.format(" Hello World")
				.toString());

		Assert.assertEquals("\" Hello World \"", writer.format(" Hello World ")
				.toString());
	}

	@Test
	public void testFormatAlwaysUseDoubleQuotes()
	{
		final Writer writer = new Writer(",", "\r\n", true);

		Assert.assertEquals("\"Hello World\"", writer.format("Hello World")
				.toString());

		Assert.assertEquals("\"\"\"Hello World\"", writer.format("\"Hello World")
				.toString());

		Assert.assertEquals("\"\"\"Hello World\"\"\"",
				writer.format("\"Hello World\"").toString());

		Assert.assertEquals("\"Hello, World\"", writer.format("Hello, World")
				.toString());

		Assert.assertEquals("\"Hello\n World\"", writer.format("Hello\n World")
				.toString());

		Assert.assertEquals("\"Hello\r World\"", writer.format("Hello\r World")
				.toString());

		Assert.assertEquals("\" Hello World\"", writer.format(" Hello World")
				.toString());

		Assert.assertEquals("\" Hello World \"", writer.format(" Hello World ")
				.toString());
		
		System.out.println(writer.format("\"gaze\n"));
	}

	@Test
	public void testWrite() throws Exception
	{
		Writer writer = new Writer(",", "\n", false);

		File f = new File("write.test");

		PrintStream out = new PrintStream(f);

		writer.write(out, 1, 2, 3);
		writer.nextLine(out);
		writer.write(out, 4, 5, 6);

		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new FileReader(f));

		Assert.assertEquals("1,2,3", in.readLine());
		Assert.assertEquals("4,5,6", in.readLine());

		in.close();

		// Clean up
		f.delete();
	}

	@Test
	public void testWriteMapped() throws Exception
	{
		Writer writer = new Writer(":", "\n", false);

		File f = new File("writeMapped.test");

		PrintStream out = new PrintStream(f);

		ColumnAnnotationStub person = new ColumnAnnotationStub();

		person.setFirstName("Chris");
		person.setLastName("Folsom");

		ColumnAnnotationStub person2 = new ColumnAnnotationStub();

		person2.setFirstName("Mom");
		person2.setLastName("Your");

		writer.writeMapped(out, person);
		writer.nextLine(out);
		writer.writeMapped(out, person2);

		out.flush();
		out.close();

		BufferedReader in = new BufferedReader(new FileReader(f));

		Assert.assertEquals("Folsom:Chris", in.readLine());
		Assert.assertEquals("Your:Mom", in.readLine());

		in.close();

		// Clean up
		f.delete();
	}
}
