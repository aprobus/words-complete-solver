package org.aaron.wordscomplete.model;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:20 PM
 */
public enum BoardType {
   Scrabble,
   WordsWithFriends,
   Unknown;

   public static BoardType fromString(String boardType) {
      if (boardType == null) {
         return Unknown;
      }

      boardType = boardType.toLowerCase();

      if (boardType.equals("scrabble")) {
            return Scrabble;
      } else if (boardType.equals("wordswithfriends")) {
         return WordsWithFriends;
      } else {
         return Unknown;
      }
   }
}
