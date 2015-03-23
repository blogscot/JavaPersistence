package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIOStorage implements Storable {

  private ArrayList<MusicItem> musicList = new ArrayList<>();
  private int currentItemIndex = 0;
  private int musicCollectionLength = 0;

  public FileIOStorage() {
    // TODO Auto-generated constructor stub
  }

  public void load(File filename) {

    // Using try with resources, Java 7 feature
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String artistLine;
      
      // Clear all details at start, in case of multiple loads
      musicList.clear();

      // Read in all music items into memory
      while ((artistLine = br.readLine()) != null) {
        String albumLine = br.readLine();
        musicList.add(new MusicItem(artistLine, albumLine));
      }

      musicCollectionLength = musicList.size();

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void save(File filename, MusicItem item) {

    // Using try with resources, Java 7 feature
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {

      bw.write(Integer.toString(musicCollectionLength));
      bw.newLine();
      bw.write(item.getArtist());
      bw.newLine();
      bw.write(item.getAlbum());
      bw.newLine();
      
      // reload file 
      load(filename);

    } catch (IOException ex) {
      System.out.println("File I/O error.");
    }
  }

  /**
   * Returns the first MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getFirstItem() {
    return getItem(0);
  }

  /**
   * Returns the MusicItem at the index position
   * 
   * @param index
   *          the MusicCollection index
   * @return a MusicItem instance
   */
  private MusicItem getItem(int index) {

    // Check a collection exists and the index is valid
    if (musicCollectionLength > 0 && index < musicCollectionLength) {
      return musicList.get(index);
    }
    System.err.println("MusicCollection: Invalid index value: " + index);
    return new MusicItem("", "");
  }

  /**
   * Returns the next MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getNext() {

    if (musicCollectionLength > 0) {

      // increment and wrap the index
      // Note: modulus by 0 is not pretty
      currentItemIndex = ++currentItemIndex % musicCollectionLength;
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "");
  }

  /**
   * Returns the previous MusicItem in the Music Collection
   * 
   * @return a MusicItem instance
   */
  public MusicItem getPrevious() {

    if (musicCollectionLength > 0) {
      if (--currentItemIndex < 0) {

        // wrap the index
        currentItemIndex = musicCollectionLength - 1;
      }
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "");
  }
}
