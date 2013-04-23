package org.aaron.wordscomplete.dictionary;

import org.aaron.wordscomplete.model.TileRack;

import java.util.Iterator;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 9:40 PM
 */
public interface Dictionary {
   public void insertAll(String[] words);
   public boolean exists(String word);
   public Iterator<String> getWordsForLengthIterator(int wordSize);
   public boolean hasWordForWildCard(String prefix, TileRack tileRack, String suffix);
}
