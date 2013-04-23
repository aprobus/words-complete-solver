package org.aaron.wordscomplete.dictionary;

/**
 * User: aprobus
 * Date: 4/23/13
 * Time: 5:11 PM
 */
public enum DictionaryType {

   WordsWithFriends,
   Scrabble,
   Unknown;

   public static DictionaryType fromString(String dictionaryType) {
      if (dictionaryType == null) {
         return Unknown;
      }

      if (dictionaryType.toLowerCase().equals("wordswithfriends")) {
         return WordsWithFriends;
      } else if (dictionaryType.toLowerCase().equals("scrabble")) {
         return Scrabble;
      } else {
         return Unknown;
      }
   }

}
