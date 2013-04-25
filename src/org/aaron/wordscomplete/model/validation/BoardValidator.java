package org.aaron.wordscomplete.model.validation;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/23/13
 * Time: 10:30 PM
 */
public class BoardValidator {

   private enum TileStatus {
      Invalid,
      Valid,
      Empty
   }

   public static List<Coordinate> getInvalidLocation(Board board) {
      TileStatus[][] tileStatuses = new TileStatus[Board.NUM_ROWS][Board.NUM_COLUMNS];
      for (int row = 0; row < Board.NUM_ROWS; row++) {
         for (int column = 0; column < Board.NUM_COLUMNS; column++) {
            if (board.hasTile(row, column)) {
               tileStatuses[row][column] = TileStatus.Invalid;
            } else {
               tileStatuses[row][column] = TileStatus.Empty;
            }
         }
      }

      setValid(board, 7, 7, tileStatuses);

      return collectInvalidLocations(tileStatuses);
   }

   private static void setValid(Board board, int row, int column, TileStatus[][] tileStatuses) {
      if (!board.hasTile(row, column)) {
         return;
      } else if (tileStatuses[row][column] == TileStatus.Valid) {
         return;
      }

      tileStatuses[row][column] = TileStatus.Valid;

      setValid(board, row + 1, column, tileStatuses);
      setValid(board, row, column + 1, tileStatuses);
      setValid(board, row - 1, column, tileStatuses);
      setValid(board, row, column - 1, tileStatuses);

   }

   private static List<Coordinate> collectInvalidLocations(TileStatus[][] tileStatuses) {
      LinkedList<Coordinate> invalidCoordinates = new LinkedList<Coordinate>();

      for (int row = 0; row < Board.NUM_ROWS; row++) {
         for (int column = 0; column < Board.NUM_COLUMNS; column++) {
            if (tileStatuses[row][column] == TileStatus.Invalid) {
               invalidCoordinates.add(new Coordinate(row, column));
            }
         }
      }

      return invalidCoordinates;
   }

}
