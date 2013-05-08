package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 5:45 PM
 */
public class PrePostTilePositionFilter implements PositionFilter {

   private Board board;

   public PrePostTilePositionFilter(Board board) {
      this.board = board;
   }

   @Override
   public boolean isValidPosition(List<Coordinate> coordinates) {
      Coordinate firstCoord = coordinates.get(0);
      Coordinate lastCoord = coordinates.get(coordinates.size() - 1);

      //Check if there is a tile before/after the location we are playing
      if (firstCoord.getRow() < lastCoord.getRow()) {
         if (firstCoord.getRow() - 1 >= 0 && board.hasTile(firstCoord.getRow() - 1, firstCoord.getColumn())) {
            return false;
         } else if (lastCoord.getRow() + 1 < Board.NUM_ROWS && board.hasTile(lastCoord.getRow() + 1, lastCoord.getColumn())) {
            return false;
         }
      } else {
         if (firstCoord.getColumn() - 1 >= 0 && board.hasTile(firstCoord.getRow(), firstCoord.getColumn() - 1)) {
            return false;
         } else if (lastCoord.getColumn() + 1 < Board.NUM_COLUMNS && board.hasTile(lastCoord.getRow(), lastCoord.getColumn() + 1)) {
            return false;
         }
      }

      return true;
   }
}
