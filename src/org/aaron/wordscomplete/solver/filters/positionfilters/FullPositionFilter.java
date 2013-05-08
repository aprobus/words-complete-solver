package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 5:42 PM
 */
public class FullPositionFilter implements PositionFilter {

   private Board board;

   public FullPositionFilter(Board board) {
      this.board = board;
   }

   @Override
   public boolean isValidPosition(List<Coordinate> coordinates) {
      for (Coordinate coordinate : coordinates) {
         if (!board.hasTile(coordinate)) {
            return true;
         }
      }

      return false;
   }
}
