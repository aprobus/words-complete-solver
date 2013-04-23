package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.LetterTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 11:08 AM
 */
public class ExistingTilesFilter extends WordFilter<Void> {
   @Override
   public boolean isValidWord(Board board, String word, List<Coordinate> coordinates) {
      for (int i = 0; i < coordinates.size(); i++) {
         LetterTile letterTile = board.getTile(coordinates.get(i));

         if (letterTile != null && letterTile.getLetter() != word.charAt(i)) {
            return false;
         }
      }

      return true;
   }
}
