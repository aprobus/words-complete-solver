package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 4:48 PM
 */
public class SecondaryWordFilter implements WordFilter {

   private Dictionary dictionary;

   public SecondaryWordFilter(Dictionary dictionary) {
      this.dictionary = dictionary;
   }

   @Override
   public boolean isValidWord(Board board, String word, List<Coordinate> coordinates) {
      Direction placementDirection = Direction.Down;

      if (coordinates.get(0).getColumn() < coordinates.get(1).getColumn()) {
         placementDirection = Direction.Right;
      }

      for (int i = 0; i < coordinates.size(); i++) {
         if (isSecondaryWordFormed(board, coordinates.get(i), placementDirection) &&
               !isValidSecondaryWord(board, coordinates.get(i), word.charAt(i), placementDirection)) {
            return false;
         }
      }

      return true;
   }

   private boolean isSecondaryWordFormed(Board board, Coordinate coordinate, Direction direction) {
      if (board.hasTile(coordinate)) {
         return false;
      }

      if (direction == Direction.Right &&
            ((coordinate.getRow() - 1 >= 0 && board.hasTile(coordinate.getRow() - 1, coordinate.getColumn())) ||
            (coordinate.getRow() + 1 < Board.NUM_ROWS && board.hasTile(coordinate.getRow() + 1, coordinate.getColumn())))) {
         return true;
      } if (direction == Direction.Down &&
            ((coordinate.getColumn() - 1 >= 0 && board.hasTile(coordinate.getRow(), coordinate.getColumn() - 1)) ||
                  (coordinate.getColumn() + 1 < Board.NUM_COLUMNS && board.hasTile(coordinate.getRow(), coordinate.getColumn() + 1)))) {
         return true;
      }

      return false;
   }

   private boolean isValidSecondaryWord(Board board, Coordinate coordinate, char letterPlaced, Direction direction) {
      int row = coordinate.getRow();
      int column = coordinate.getColumn();

      if (direction == Direction.Right) {
         while (row - 1 >= 0 && board.hasTile(row - 1, column)) {
            row--;
         }
      } else {
         while (column - 1 >= 0 && board.hasTile(row, column - 1)) {
            column--;
         }
      }

      StringBuilder wordFormed = new StringBuilder();
      while (board.hasTile(row, column) || (row == coordinate.getRow() && column == coordinate.getColumn())) {
         if (row == coordinate.getRow() && column == coordinate.getColumn()) {
            wordFormed.append(letterPlaced);
         } else {
            wordFormed.append(board.getBoardTile(row, column).getTile().getLetter());
         }

         if (direction == Direction.Right) {
            row++;
         } else {
            column++;
         }
      }

      return dictionary.exists(wordFormed.toString());
   }

   private enum Direction {
      Right,
      Down
   }
}
