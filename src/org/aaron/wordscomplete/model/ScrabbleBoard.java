package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.model.scoring.BoardPlacement;
import org.aaron.wordscomplete.model.scoring.FullBoardPlacement;
import org.aaron.wordscomplete.model.scoring.PartialBoardPlacement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 9:14 PM
 */
public class ScrabbleBoard extends Board {

   private static Map<Character, Integer> letterScores = new HashMap<Character, Integer>(26);

   static {
      letterScores.put('a', 1);
      letterScores.put('b', 3);
      letterScores.put('c', 3);
      letterScores.put('d', 2);
      letterScores.put('e', 1);
      letterScores.put('f', 4);
      letterScores.put('g', 2);
      letterScores.put('h', 4);
      letterScores.put('i', 1);
      letterScores.put('j', 8);
      letterScores.put('k', 5);
      letterScores.put('l', 1);
      letterScores.put('m', 3);
      letterScores.put('n', 1);
      letterScores.put('o', 1);
      letterScores.put('p', 3);
      letterScores.put('q', 10);
      letterScores.put('r', 1);
      letterScores.put('s', 1);
      letterScores.put('t', 1);
      letterScores.put('u', 1);
      letterScores.put('v', 4);
      letterScores.put('w', 4);
      letterScores.put('x', 8);
      letterScores.put('y', 4);
      letterScores.put('z', 10);
   }

   public ScrabbleBoard () {
      super();

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            board[column][row].setScoreBonus(this.getScoreBonus(row, column));
         }
      }
   }

   private ScoreBonus getScoreBonus (int row, int column) {
      int transRow = row > 7 ? 14 - row : row;
      int transColumn = column > 7 ? 14 - column : column;

      if ((transRow == 0 && transColumn == 0) || areSameOrInverse(transRow, transColumn, 0, 7)) {
         return ScoreBonus.TripleWordScore;
      } else if ((transRow == 1 || transRow == 2 || transRow == 3 || transRow == 4 || transRow == 7) && transRow == transColumn) {
         return ScoreBonus.DoubleWordScore;
      } else if ((transRow == 5 && transColumn == 5) ||
            areSameOrInverse(transRow, transColumn, 1, 5)) {
         return ScoreBonus.TripleLetterScore;
      } else if (areSameOrInverse(transRow, transColumn, 0, 3) ||
            areSameOrInverse(transRow, transColumn, 2, 6) ||
            areSameOrInverse(transRow, transColumn, 3, 7) ||
            (transRow == 6 && transColumn == 6)) {
         return ScoreBonus.DoubleLetterScore;
      }

      return ScoreBonus.None;
   }

   private static boolean areSameOrInverse (int row1, int column1, int row2, int column2) {
      return (row1 == row2 && column1 == column2) || (row1 == column2 && column1 == row2);
   }

   @Override
   public int getScore(FullBoardPlacement placementToScore) {
      int score = scorePlacement(placementToScore);

      List<PartialBoardPlacement> secPlacements = placementToScore.getSecondaryPlacements();
      if (secPlacements != null) {
         for (BoardPlacement secPlacement : secPlacements) {
            score += this.scorePlacement(secPlacement);
         }
      }

      if (placementToScore.getAddedTiles().size() == 7) {
         score += 50;
      }

      return score;
   }

   private int scorePlacement (BoardPlacement placementToScore) {
      int score = 0;

      for (LetterTile tile : placementToScore.getExistingTiles()) {
         if (!tile.isBlank()) {
            score += letterScores.get(tile.getLetter());
         }
      }

      for (PlacedTile tile : placementToScore.getAddedTiles()) {
         if (!tile.getTile().isBlank()) {
            int initialLetterScore = letterScores.get(tile.getTile().getLetter());

            ScoreBonus bonus = this.getBoardTile(tile.getCoordinate()).getScoreBonus();
            if (bonus == ScoreBonus.DoubleLetterScore) {
               initialLetterScore *= 2;
            } else if (bonus == ScoreBonus.TripleLetterScore) {
               initialLetterScore *= 3;
            }

            score += initialLetterScore;
         }
      }

      for (PlacedTile tile : placementToScore.getAddedTiles()) {
         ScoreBonus bonus = this.getBoardTile(tile.getCoordinate()).getScoreBonus();
         if (bonus == ScoreBonus.DoubleWordScore) {
            score *= 2;
         } else if (bonus == ScoreBonus.TripleWordScore) {
            score *= 3;
         }
      }

      return score;
   }

   @Override
   public BoardType getBoardType() {
      return BoardType.Scrabble;
   }

}
