package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * The FileIOStorage class.
 * 
 * This class loads and saves File IO storage files. 
 * The user may also add new music items, update existing items as well as
 * delete items in the music list (see Storable.java).
 * 
 * @author Iain Diamond
 * @version 20/04/2015
 * 
 */
public class FileIOStorage extends Storable {

  public FileIOStorage() {
  }

  /**
   * Loads the specified file into the music list.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  public void load(File filename) throws PersistenceException {

    String line = "";
    String values[];

    // Using try with resources, Java 7 feature
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

      // Clear the list before populating new data
      musicList.clear();

      // Read in all music items into memory
      while ((line = br.readLine()) != null) {
        values = line.split(", ");
        musicList.add(new MusicItem(values[0], values[1], values[2], values[3],
            Integer.parseInt(values[4]), values[5]));
      }

      musicCollectionLength = musicList.size();

    } catch (IOException ex) {
      throw new PersistenceException();
    }
  }

  /**
   * Saves the music list in the specified file.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  public void save(File filename) throws PersistenceException {

    String line = "";
    String s = ", "; // the comma separator

    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, false))) {

      for (MusicItem item : musicList) {
        line = item.getArtist() + s + item.getAlbum() + s + item.getTrack() + s
            + item.getDuration() + s + item.getYear() + s + item.getGenre();
        bw.write(line);
        bw.newLine();
      }
    } catch (IOException ex) {
      throw new PersistenceException();
    }
  }
}
