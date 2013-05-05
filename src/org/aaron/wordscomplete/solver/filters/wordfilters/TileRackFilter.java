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
public class TileRackFilter implements WordFilter {

   private TileRack tileRack;

   public TileRackFilter(TileRack tileRack) {
      this.tileRack = tileRack;
   }

   @Override
   public boolean isValidWord(Board board, String word, List<Coordinate> coordinates, LetterTile[] letterTilesFromBoard) {
      boolean[] usedTile = new boolean[tileRack.getLetterTiles().size()];

      for (int i = 0; i < word.length(); i++) {
         LetterTile tile = letterTilesFromBoard[i];
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
      List<LetterTile> letterTiles = tileRack.getLetterTiles();
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
