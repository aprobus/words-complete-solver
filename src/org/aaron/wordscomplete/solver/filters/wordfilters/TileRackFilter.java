package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.TileRack;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 4:25 PM
 */
public class TileRackFilter extends WordFilter<TileRack> {

   public TileRackFilter(TileRack tileRack) {
      super(tileRack);
   }

   @Override
   public boolean isValidWord(Board board, String word, List<Coordinate> coordinates) {
      boolean[] usedTile = new boolean[mFilterValue.getLetterTiles().size()];

      for (int i = 0; i < word.length(); i++) {
         Coordinate coordinate = coordinates.get(i);
         LetterTile tile = board.getTile(coordinate);
         if (tile != null) {
            continue;
         }

         boolean hasValidTile = markFirstUsableTileUsed(usedTile, word.charAt(i));
         if (!hasValidTile) {
            return false;
         }
      }

      return true;
   }

   private boolean markFirstUsableTileUsed(boolean[] usedTiles, char letterToUse) {
      List<LetterTile> letterTiles = mFilterValue.getLetterTiles();
      for (int i = 0; i < usedTiles.length; i++) {
         if (!usedTiles[i] && !letterTiles.get(i).isBlank() && letterTiles.get(i).getLetter() == letterToUse) {
            usedTiles[i] = true;
            return true;
         }
      }

      for (int i = 0; i < usedTiles.length; i++) {
         if (!usedTiles[i] && letterTiles.get(i).isBlank()) {
            usedTiles[i] = true;
            return true;
         }
      }

      return false;
   }
}
