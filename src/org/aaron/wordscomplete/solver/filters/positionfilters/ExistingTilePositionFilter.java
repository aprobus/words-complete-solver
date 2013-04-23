package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 5:47 PM
 */
public class ExistingTilePositionFilter extends PositionFilter {
   @Override
   public boolean isValidPosition(Board board, List<Coordinate> coordinates) {
      Coordinate firstCoord = coordinates.get(0);
      Coordinate lastCoord = coordinates.get(coordinates.size() - 1);

      boolean isLeftToRight = lastCoord.column > firstCoord.column;

      for (int i = 0; i < coordinates.size(); i++) {
         Coordinate coordinate = coordinates.get(i);

         if (board.hasTile(coordinate)) {
            return true;
         } else if (isLeftToRight) {
            if (coordinate.row - 1 >= 0 && board.hasTile(coordinate.row - 1, coordinate.column)) {
               return true;
            } else if (coordinate.row + 1 < Board.NUM_ROWS && board.hasTile(coordinate.row + 1, coordinate.column)) {
               return true;
            }
         } else {
            if (coordinate.column - 1 >= 0 && board.hasTile(coordinate.row, coordinate.column - 1)) {
               return true;
            } else if (coordinate.column + 1 < Board.NUM_COLUMNS && board.hasTile(coordinate.row, coordinate.column + 1)) {
               return true;
            }
         }
      }

      boolean isFirstPlayOfGame = firstCoord.row == 7 && lastCoord.row == 7 && firstCoord.column <= 7 && lastCoord.column >= 7;
      return isFirstPlayOfGame;
   }
}
