package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {
    vistor.visit(rootDirectory);
    if(rootDirectory.isDirectory()) {
      // I sort the array to pass the test's format
      ArrayList<File> dirsFiles = new ArrayList<>(Arrays.asList(rootDirectory.listFiles()));
      Collections.sort(dirsFiles);

      // I need a double loop also to pass the test because the test need dir -> subdir -> files in subdir -> files in dir
      // and the method .listFiles() return dir -> files in dir -> subdir -> files in subdir -> etc
      for(File dirFile : dirsFiles) {
        if (dirFile.isDirectory()) {
          explore(dirFile, vistor);
        }
      }

      for(File dirFile : dirsFiles) {
        if (dirFile.isFile()) {
          explore(dirFile, vistor);
        }
      }
    }
  }
}
