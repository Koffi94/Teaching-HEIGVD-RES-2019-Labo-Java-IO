package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\tHello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int counterLine = 1; //Compteur de ligne
  private boolean nextLine = false , maybeWindows = false; // flags


  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    this.write(str.toCharArray(), off, len); // Call the next one

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    // Input checks (to avoid buffer overflow)
   if(cbuf.length < 1 || off < 0 || off > (cbuf.length - 1) || len < 0 || len > (cbuf.length - off)) {
      System.err.println("Bad Input");
      return;
    }

    for (int i = off; i < off + len; i++) {
      this.write(cbuf[i]); // Call the next one
    }
  }

  @Override
  public void write(int c) throws IOException {
    // If it's the first line, start with the "line header"
    if(counterLine == 1) {
      out.write(counterLine++ + "\t");
    }

    // If there is a LF then up the nextline flag to put the "line header" for next line
    if((char)c == '\n') {
      nextLine = true;
    }

    // If there is a CR, wait next char to see if it's Windows or MAC OS to put the "line header"
    if((char)c == '\r') {
      maybeWindows = true;
    }

    // If we have CRLF then it's Windows -> down the maybeWindows flag
    if(maybeWindows && (char)c == '\n') {
      maybeWindows = false;
    }

    // If we have CR + anything else except LF then it's MAC OS -> write the "line header" before the "anything else" and down th maybeWindows flag
    if (maybeWindows && (char)c != '\r'){
      out.write(counterLine++ + "\t");
      maybeWindows = false;
    }

    // Write the current char
    out.write(c);

    // If we got a LF then write the "line header"
    if(nextLine) {
      out.write(counterLine++ + "\t");
      nextLine = false;
    }
  }
}
