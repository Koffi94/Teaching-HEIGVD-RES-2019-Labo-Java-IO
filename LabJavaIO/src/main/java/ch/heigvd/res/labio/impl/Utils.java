package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] ret = {"", ""};

    // Check if the argument is not empty
    if(lines.isEmpty()) {
      System.err.println("The argument is an empty string");
      return null;
    }

    // Add chars in tmp and only copy in ret[0] if we find a '\r' or '\n'
    int i = 0;
    String tmp = new String();
    for(; i < lines.length(); ++i) {
      tmp += lines.charAt(i);
      if((lines.charAt(i) == '\r') || (lines.charAt(i) == '\n')) {
        ret[0] = tmp;
        if ((lines.charAt(i) == '\r') && (i < lines.length() - 1) && (lines.charAt(i + 1) == '\n')) {
          ret[0] += '\n';
          ++i;
        }
        break;
      }
    }

    // Check if ret[0] is empty then ret[1] is the whole text
    // and check if it still have chars in lines after fill ret[0], to put the rest in ret[1]
    if(ret[0].isEmpty())
      ret[1] = lines;
    else if(i < lines.length() - 1)
      ret[1] = lines.substring(i + 1);

    return ret;
  }
}
