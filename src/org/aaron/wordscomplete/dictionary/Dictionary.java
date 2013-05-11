package org.aaron.wordscomplete.dictionary;

import org.aaron.wordscomplete.model.TileRack;

import java.util.Iterator;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 9:40 PM
 */
public interface Dictionary {
   void setWords(String[] words);
   boolean exists(String word);
   Iterator<String> getWordsForLengthIterator(int wordSize);
   Iterator<String> getWordsForLengthIterator(int wordSize, int incrementBy, int initialOffset);
   boolean hasWordForWildCard(String prefix, TileRack tileRack, String suffix);
   boolean hasSimilarWord(char[] letters);
}
