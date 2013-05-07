package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.TileRack;

import java.util.List;

/**
 * Tests if a word can be formed with the given tiles.
 * Assumption: Blank tiles must be at the end of the tile rack
 *
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

      List<LetterTile> rackTiles = tileRack.getLetterTiles();

      for (int i = 0; i < word.length(); i++) {
         LetterTile tile = letterTilesFromBoard[i];
         if (tile != null) {
            continue;
         }

         boolean hasValidTile = markFirstUsableTileUsed(rackTiles, usedTile, word.charAt(i));
         if (!hasValidTile) {
            return false;
         }
      }

      return true;
   }

   private static boolean markFirstUsableTileUsed(List<LetterTile> rackTiles, boolean[] usedTiles, char letterToUse) {
      for (int i = 0; i < usedTiles.length; i++) {
         LetterTile tile = rackTiles.get(i);
         if (!usedTiles[i] && (tile.isBlank() || tile.getLetter() == letterToUse)) {
            usedTiles[i] = true;
            return true;
         }
      }

      return false;
   }
}
