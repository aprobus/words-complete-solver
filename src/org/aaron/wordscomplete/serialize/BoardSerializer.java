package org.aaron.wordscomplete.serialize;

import org.aaron.wordscomplete.model.*;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 10:40 PM
 */
public class BoardSerializer {

   private static final char EMPTY_TILE_CHAR = '_';

   public static String serialize(Board board) {
      StringBuilder builder = new StringBuilder();

      for (int row = 0; row < Board.NUM_ROWS; row++) {
         for (int column = 0; column < Board.NUM_COLUMNS; column++) {
            if (board.hasTile(row, column)) {
               LetterTile tile = board.getTile(row, column);

               if (tile.isBlank()) {
                  builder.append(Character.toUpperCase(tile.getLetter()));
               } else {
                  builder.append(Character.toLowerCase(tile.getLetter()));
               }
            } else {
               builder.append(EMPTY_TILE_CHAR);
            }
         }
      }

      return builder.toString();
   }

   public static Board deserialize(String boardString, BoardType boardType) {
      if (boardString == null || boardString.length() != Board.NUM_COLUMNS * Board.NUM_ROWS) {
         return null;
      }

      Board board = null;
      switch(boardType) {
         case Scrabble:
            board = new ScrabbleBoard();
            break;
         case WordsWithFriends:
            board = new WordsWithFriendsBoard();
            break;
         default:
            return null;
      }

      int stringIndex = 0;
      for (int row = 0; row < Board.NUM_ROWS; row++) {
         for (int column = 0; column < Board.NUM_COLUMNS; column++) {
            char tileChar = boardString.charAt(stringIndex);

            if (Character.isLetter(tileChar)) {
               board.setTile(row, column, new LetterTile(Character.toLowerCase(tileChar), Character.isUpperCase(tileChar)));
            }

            stringIndex++;
         }
      }

      return board;
   }

}
