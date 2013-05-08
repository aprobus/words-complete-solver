package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.TileRack;

import java.util.List;

/**
 * User: aprobus
 * Date: 5/7/13
 * Time: 9:59 PM
 */
public class FillAllPositionsFilter implements PositionFilter {

   private Board board;
   private int numTiles;

   public FillAllPositionsFilter (Board board, TileRack tileRack) {
      this.board = board;
      this.numTiles = tileRack.getLetterTiles().size();
   }

   @Override
   public boolean isValidPosition(List<Coordinate> coordinates) {
      int openPositions = 0;

      for (Coordinate coordinate : coordinates) {
         if (board.getTile(coordinate) == null) {
            openPositions++;

            if (openPositions > numTiles) {
               return false;
            }
         }
      }

      return true;
   }
}
