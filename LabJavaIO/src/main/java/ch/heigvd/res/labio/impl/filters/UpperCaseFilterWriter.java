package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  // Added this override method to pass the first test
  @Override
  public void write(String str) throws IOException {
    out.write(str.toUpperCase());
  }

  // Added this override method to pass the second test
  @Override
  public void write(char cbuf[]) throws IOException {
    String str = new String(cbuf);
    out.write(str.toUpperCase());
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    // Basics input checks
    if(str.isEmpty() || off < 0 || off > str.length() - 1 || len < 0 || len > str.length() - 1 - off) {
      System.err.println("Input error");
      return;
    }

    out.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // Basics input checks
    if(cbuf.length < 1 || off < 0 || off > cbuf.length - 1 || len < 0 || len > cbuf.length - 1 - off) {
      System.err.println("Input error");
      return;
    }

    // Uppercase chars from off to off + len
    String str = new String(cbuf);
    out.write(str.toUpperCase(), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    out.write(Character.toUpperCase(c));
  }
}
