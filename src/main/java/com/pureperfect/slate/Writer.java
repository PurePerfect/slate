package com.pureperfect.slate;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Used to create separated value spreadsheets based on a given delimiter (e.g.
 * ',', '\t', etc). Writer does not assume to do any flushing for you. It is
 * better that you flush your own streams at regular intervals since you know
 * your code better than I do.This class is thread safe.
 * 
 * @author J. Chris Folsom
 * @version 0.4
 * @since 0.4
 */
public class Writer
{
	private final CharSequence delimiter;

	private CharSequence nextLine = "\r\n";

	private final boolean alwaysUseDoubleQuotes;

	/**
	 * Create a new writer with the specified delimiter. The default next line
	 * characters are "\r\n" (CRLF).
	 * 
	 * @param delimiter
	 *            The delimiter to use.
	 */
	public Writer(final CharSequence delimiter)
	{
		this.delimiter = delimiter;
		this.nextLine = "\r\n";
		this.alwaysUseDoubleQuotes = false;
	}

	/**
	 * Specify the delimiter and the next line delimiter.
	 * 
	 * @param delimiter
	 *            The delimiter to use
	 * @param nextLine
	 *            the next line delimiter to use
	 * @param alwaysUseDoubleQuotes
	 *            Tell the writer that all values will always be wrapped in '"'
	 *            regardless of whether or not they technically need to be
	 */
	public Writer(final CharSequence delimiter, final CharSequence nextLine,
			boolean alwaysUseDoubleQuotes)
	{
		this.delimiter = delimiter;
		this.nextLine = nextLine;
		this.alwaysUseDoubleQuotes = alwaysUseDoubleQuotes;
	}

	/**
	 * Get the printed representation of what the value will be after being
	 * properly formatted.
	 * 
	 * @param value
	 *            The value to format
	 * @return The formatted text.
	 */
	public CharSequence format(final Object value)
	{
		if (value == null)
		{
			return "null";
		}

		final String orig = value.toString();

		final StringBuilder builder = new StringBuilder(orig);

		boolean wrapWithQuotes = this.alwaysUseDoubleQuotes;

		/*
		 * Values with leading or trailing whitespace will be wrapped in quotes,
		 * but we only need to check if we are not already using double quotes
		 * anyway.
		 */
		if (!wrapWithQuotes
				&& (Character.isWhitespace(orig.charAt(0)) || Character
						.isWhitespace(builder.charAt(orig.length() - 1))))
		{
			wrapWithQuotes = true;
		}

		for (int i = 0; i < builder.length(); ++i)
		{
			final char current = builder.charAt(i);

			/*
			 * If any of these characters exist, we'll need to wrap the value in
			 * double quotes, but we only need to check if we are not already
			 * using double quotes anyway.
			 */
			if (!wrapWithQuotes
					&& (current == '\n' || current == '\r' || this.delimiter
							.equals(String.valueOf(current))))
			{
				wrapWithQuotes = true;
			}

			/*
			 * Double quotes need to be escaped by a double quote.
			 */
			else if (current == '\"')
			{
				builder.insert(i, '\"');

				/*
				 * We inserted a character so we need to move forward two
				 * characters not one.
				 */
				i++;
			}
		}

		if (wrapWithQuotes)
		{
			builder.insert(0, "\"");
			builder.append("\"");
		}

		return builder;
	}

	/**
	 * Indicate the end of the record and move to the next line.
	 * 
	 * @param out
	 *            The stream to write to
	 */
	public void nextLine(final PrintStream out)
	{
		out.print(this.nextLine);
	}

	/**
	 * Print the delimiter.
	 * 
	 * @param out
	 *            the PrintStream to print it to
	 */
	public void delimiter(PrintStream out)
	{
		out.print(this.delimiter);
	}

	/**
	 * Write a set of values to the given output stream. Values will be
	 * separated by the delimiter.
	 * 
	 * @param out
	 *            The stream to write to
	 * @param values
	 *            The values to write.
	 */
	public void write(final PrintStream out, final Object... values)
	{
		final int length = values.length;

		for (int i = 0; i < length; ++i)
		{
			out.print(this.format(values[i]));

			if (i < values.length - 1)
			{
				this.delimiter(out);
			}
		}
	}

	/**
	 * Writes out objects that are mapped using the {@link Column} annotation in
	 * their sorted column order.
	 * 
	 * @param out
	 *            The print stream to write to.
	 * 
	 * @param mappedObjects
	 *            The object instances to write
	 * @see Column
	 */
	public void writeMapped(final PrintStream out,
			final Object... mappedObjects)
	{
		for (Object o : mappedObjects)
		{
			List<Method> methods = Utils.findMethodsWithAnnotation(o,
					Column.class);

			Collections.sort(methods, new ColumnComparator());

			int current = 0;
			int delimStop = methods.size() - 1;

			for (Method m : methods)
			{
				try
				{
					out.print(this.format(m.invoke(o, (Object[]) null)));
				}
				catch (Throwable e)
				{
					throw new MappedMethodException(e);
				}

				if (current++ < delimStop)
					this.delimiter(out);
			}
		}
	}
}