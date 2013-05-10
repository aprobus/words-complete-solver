package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.LetterTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 5/9/13
 * Time: 9:40 PM
 */
public class SimilarWordFilter implements PositionFilter {

   private Board board;
   private Dictionary dictionary;

   public SimilarWordFilter(Board board, Dictionary dictionary) {
      this.board = board;
      this.dictionary = dictionary;
   }

   @Override
   public boolean isValidPosition(List<Coordinate> coordinates) {
      char[] letters = new char[coordinates.size()];
      int existingLetterCount = 0;

      for (int i = 0; i < coordinates.size(); i++) {
         Coordinate coordinate = coordinates.get(i);
         LetterTile tile = board.getTile(coordinate);

         if (tile != null) {
            letters[i] = tile.getLetter();
            existingLetterCount++;
         } else {
            letters[i] = 0;
         }
      }

      if (existingLetterCount >= 2) {
         return dictionary.hasSimilarWord(letters);
      } else {
         return true;
      }
   }
}
