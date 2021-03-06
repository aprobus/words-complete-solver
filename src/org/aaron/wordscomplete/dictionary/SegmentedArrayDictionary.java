package org.aaron.wordscomplete.dictionary;

import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.TileRack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 9:41 PM
 */
public class SegmentedArrayDictionary implements Dictionary {

   private List<String>[] mWordsByLength;

   public SegmentedArrayDictionary () {
      mWordsByLength = new List[14];

      for (int i = 0; i < mWordsByLength.length; i++) {
         mWordsByLength[i] = new ArrayList<String>();
      }
   }

   @Override
   public void setWords(String[] words) {
      for (int i = 0; i < words.length; i++) {
         String word = words[i];

         if (word.length() - 2 < mWordsByLength.length) {
            mWordsByLength[word.length() - 2].add(word);
         }
      }
   }

   @Override
   public boolean exists(String word) {
      int wordsIndex = word.length() - 2;
      if (wordsIndex < 0 || wordsIndex >= mWordsByLength.length) {
         return false;
      }

      List<String> wordsOfSameLength = mWordsByLength[wordsIndex];
      return exists(wordsOfSameLength, word);
   }

   private boolean exists (List<String> potentialWords, String word) {
      int lowerIndex = 0;
      int upperIndex = potentialWords.size() - 1;

      while (lowerIndex <= upperIndex) {
         int middle = (lowerIndex + upperIndex) / 2;
         int cmp = word.compareTo(potentialWords.get(middle));

         if (cmp == 0) {
            return true;
         } else if (cmp < 0) {
            upperIndex = middle - 1;
         } else {
            lowerIndex = middle + 1;
         }
      }

      return false;
   }

   @Override
   public Iterator<String> getWordsForLengthIterator(int wordSize) {
      return getWordsForLengthIterator(wordSize, 1 ,0);
   }

   @Override
   public Iterator<String> getWordsForLengthIterator(int wordSize, int incrementBy, int initialOffset) {
      return new WordIterator(wordSize, incrementBy, initialOffset);
   }

   @Override
   public boolean hasWordForWildCard(String prefix, TileRack tileRack, String suffix) {
      int wordLength = 1;
      if (prefix != null) {
         wordLength += prefix.length();
      }
      if (suffix != null) {
         wordLength += suffix.length();
      }

      if (wordLength == 1) {
         throw new IllegalArgumentException("Shouldn't get here. Suffix: " + suffix + ", prefix: " + prefix);
      }

      List<String> words = mWordsByLength[wordLength - 2];

      int lowerIndex = 0;
      int upperIndex = words.size() - 1;

      if (prefix != null) {
         for (int i = words.size() / 2; i > 1; i /= 2) {
            if (prefix.compareTo(words.get(lowerIndex + i)) > 0) {
               lowerIndex += i;
            }

            if (prefix.compareTo(words.get(upperIndex - i)) < 0 && !words.get(upperIndex - i).startsWith(prefix)) {
               upperIndex -= i;
            }
         }
      }

      int wildCardIndex = 0;
      if (prefix != null) {
         wildCardIndex = prefix.length();
      }

      for (int i = lowerIndex; i <= upperIndex; i++) {
         String word = words.get(i);
         if (suffix != null && !word.endsWith(suffix)) {
            continue;
         }

         char wildCardChar = word.charAt(wildCardIndex);

         for (LetterTile letterTile : tileRack.getLetterTiles()) {
            if (letterTile.isBlank()) {
               return true;
            } else if (wildCardChar == letterTile.getLetter()) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean hasSimilarWord(char[] letters) {
      List<String> words = mWordsByLength[letters.length - 2];

      int startIndex = 0;
      int endIndex = words.size() - 1;

      for (int i = 0; letters[i] != 0 && i < letters.length; i++) {
         char searchForLetter = letters[i];
         int delta = (endIndex - startIndex) / 2;

         while (delta > 0) {
            int midIndex = startIndex + delta;
            char midLetter = words.get(midIndex).charAt(i);

            if (searchForLetter > midLetter) {
               startIndex = midIndex;
            } else if (searchForLetter < midLetter) {
               endIndex = midIndex;
            }

            delta /= 2;
         }
      }

      for (int i = startIndex; i <= endIndex; i++) {
         if (areSimilar(letters, words.get(i))) {
            return true;
         }
      }

      return false;
   }

   private static boolean areSimilar(char[] letters, String word) {
      for (int i = 0; i < letters.length; i++) {
         if (letters[i] != 0 && letters[i] != word.charAt(i)) {
            return false;
         }
      }

      return true;
   }

   private class WordIterator implements Iterator<String> {

      private List<String> mWords;
      private int mCurrentIndex;
      private int incrementBy;

      public WordIterator(int wordSize, int incrementBy, int initialOffset) {
         mWords = mWordsByLength[wordSize - 2];
         mCurrentIndex = initialOffset;

         this.incrementBy = incrementBy;
      }

      @Override
      public boolean hasNext() {
         return mCurrentIndex < mWords.size();
      }

      @Override
      public String next() {
         String word = mWords.get(mCurrentIndex);
         mCurrentIndex += incrementBy;
         return word;
      }

      @Override
      public void remove() {
      }
   }

}
