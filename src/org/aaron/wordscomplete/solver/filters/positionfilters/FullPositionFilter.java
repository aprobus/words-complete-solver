package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 5:42 PM
 */
public class FullPositionFilter extends PositionFilter {
   @Override
   public boolean isValidPosition(Board board, List<Coordinate> coordinates) {
      for (Coordinate coordinate : coordinates) {
         if (!board.hasTile(coordinate)) {
            return true;
         }
      }

      return false;
   }
}
