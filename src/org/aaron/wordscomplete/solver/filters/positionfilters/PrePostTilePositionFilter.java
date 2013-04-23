package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 5:45 PM
 */
public class PrePostTilePositionFilter extends PositionFilter {
   @Override
   public boolean isValidPosition(Board board, List<Coordinate> coordinates) {
      Coordinate firstCoord = coordinates.get(0);
      Coordinate lastCoord = coordinates.get(coordinates.size() - 1);

      //Check if there is a tile before/after the location we are playing
      if (firstCoord.row < lastCoord.row) {
         if (firstCoord.row - 1 >= 0 && board.hasTile(firstCoord.row - 1, firstCoord.column)) {
            return false;
         } else if (lastCoord.row + 1 < Board.NUM_ROWS && board.hasTile(lastCoord.row + 1, lastCoord.column)) {
            return false;
         }
      } else {
         if (firstCoord.column - 1 >= 0 && board.hasTile(firstCoord.row, firstCoord.column - 1)) {
            return false;
         } else if (lastCoord.column + 1 < Board.NUM_COLUMNS && board.hasTile(lastCoord.row, lastCoord.column + 1)) {
            return false;
         }
      }

      return true;
   }
}
