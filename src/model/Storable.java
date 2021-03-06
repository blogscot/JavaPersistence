package model;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * The Storable abstract class.
 * 
 * This class provides abstract methods and implementation for each Storable 
 * type class, i.e. load and save storage files, plus add, update and delete
 * music items in the music list.
 * 
 * Methods for indexing through the music list are also provided.
 * 
 * @author Iain Diamond
 * @version 21/04/2015
 * 
 */

public abstract class Storable {
  
  // The application has a single shared music collection that may be 
  // persisted in multiple formats.
  protected static ArrayList<MusicItem> musicList = new ArrayList<>();
  protected int currentItemIndex = 0;
  protected int musicCollectionLength = 0;

  /**
   * Loads the specified file into the music list.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  public abstract void load(File filename) throws PersistenceException;

  /**
   * Adds a music item into the music list.
   * 
   */
  public void add(MusicItem item) {
    musicList.add(item);

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Saves the music list in the specified file.
   * 
   * @param filename
   *          the storage file
   * @throws PersistenceException
   */
  public abstract void save(File filename) throws PersistenceException;

  /**
   * Returns the first music item in the music list.
   * 
   * @return a music item
   */
  public MusicItem getFirstItem() {
    return getItem(0);
  }

  /**
   * Returns the music item at the index position.
   * 
   * @param index
   *          the music list index
   * @return a music item
   */
  private MusicItem getItem(int index) {

    // Check the collection exists and the index is valid
    if (musicCollectionLength > 0 && index < musicCollectionLength) {
      return musicList.get(index);
    }
    System.err.println("MusicCollection: Invalid index value: " + index);
    return new MusicItem("", "", "", "", 0, "");
  }

  /**
   * Returns the next music item in the music list.
   * 
   * @return a music item
   */
  public MusicItem getNext() {

    if (musicCollectionLength > 0) {

      // increment and wrap the index
      // Note: modulus by 0 is not pretty
      currentItemIndex = ++currentItemIndex % musicCollectionLength;
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "", "", "", 0, "");
  }

  /**
   * Returns the previous music item in the music list.
   * 
   * @return a music item
   */
  public MusicItem getPrevious() {

    if (musicCollectionLength > 0) {
      if (--currentItemIndex < 0) {

        // wrap the index
        currentItemIndex = musicCollectionLength - 1;
      }
      return getItem(currentItemIndex);
    }
    return new MusicItem("", "", "", "", 0, "");
  }

  /**
   * Removes the current music item from the music list.
   * 
   */
  public void removeCurrentItem() {
    musicList.remove(currentItemIndex);

    // Recalculate new List size
    musicCollectionLength = musicList.size();
  }

  /**
   * Updates the current music item.
   * 
   * @param item
   *          the updated music item
   */
  public void updateCurrentItem(MusicItem item) {
    musicList.set(currentItemIndex, item);
  }
}
