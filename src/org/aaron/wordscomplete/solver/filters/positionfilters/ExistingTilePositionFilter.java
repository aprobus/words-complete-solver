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

      boolean isLeftToRight = lastCoord.getColumn() > firstCoord.getColumn();

      for (int i = 0; i < coordinates.size(); i++) {
         Coordinate coordinate = coordinates.get(i);

         if (board.hasTile(coordinate)) {
            return true;
         } else if (isLeftToRight) {
            if (coordinate.getRow() - 1 >= 0 && board.hasTile(coordinate.getRow() - 1, coordinate.getColumn())) {
               return true;
            } else if (coordinate.getRow() + 1 < Board.NUM_ROWS && board.hasTile(coordinate.getRow() + 1, coordinate.getColumn())) {
               return true;
            }
         } else {
            if (coordinate.getColumn() - 1 >= 0 && board.hasTile(coordinate.getRow(), coordinate.getColumn() - 1)) {
               return true;
            } else if (coordinate.getColumn() + 1 < Board.NUM_COLUMNS && board.hasTile(coordinate.getRow(), coordinate.getColumn() + 1)) {
               return true;
            }
         }
      }

      boolean isFirstPlayOfGame = firstCoord.getRow() == 7 && lastCoord.getRow() == 7 && firstCoord.getColumn() <= 7 && lastCoord.getColumn() >= 7;
      return isFirstPlayOfGame;
   }
}
