package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.model.scoring.BoardPlacement;
import org.aaron.wordscomplete.model.scoring.FullBoardPlacement;
import org.aaron.wordscomplete.model.scoring.PartialBoardPlacement;
import org.aaron.wordscomplete.util.Counter;
import org.aaron.wordscomplete.util.HashCounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:31 PM
 */
public class WordsWithFriendsBoard extends Board {

   private static Map<Character, Integer> letterScores = new HashMap<Character, Integer>(26);

   static {
      letterScores.put('a', 1);
      letterScores.put('b', 4);
      letterScores.put('c', 4);
      letterScores.put('d', 2);
      letterScores.put('e', 1);
      letterScores.put('f', 4);
      letterScores.put('g', 3);
      letterScores.put('h', 3);
      letterScores.put('i', 1);
      letterScores.put('j', 10);
      letterScores.put('k', 5);
      letterScores.put('l', 2);
      letterScores.put('m', 4);
      letterScores.put('n', 2);
      letterScores.put('o', 1);
      letterScores.put('p', 4);
      letterScores.put('q', 10);
      letterScores.put('r', 1);
      letterScores.put('s', 1);
      letterScores.put('t', 1);
      letterScores.put('u', 2);
      letterScores.put('v', 5);
      letterScores.put('w', 4);
      letterScores.put('x', 8);
      letterScores.put('y', 3);
      letterScores.put('z', 10);
   }

   protected WordsWithFriendsBoard() {
      super();
   }

   @Override
   protected ScoreBonus getScoreBonusForCoordinate (int row, int column) {
      int transRow = row > 7 ? 14 - row : row;
      int transColumn = column > 7 ? 14 - column : column;

      if (areSameOrInverse(transRow, transColumn, 0, 3)) {
         return ScoreBonus.TripleWordScore;
      } else if (areSameOrInverse(transRow, transColumn, 5, 1) || areSameOrInverse(transRow, transColumn, 3, 7)) {
         return ScoreBonus.DoubleWordScore;
      } else if (areSameOrInverse(transRow, transColumn, 0, 6) || (transRow == transColumn && (transRow == 3 || transRow == 5))) {
         return ScoreBonus.TripleLetterScore;
      } else if (areSameOrInverse(transRow, transColumn, 2, 1) || areSameOrInverse(transRow, transColumn, 2, 4) || areSameOrInverse(transRow, transColumn, 6, 4)) {
         return ScoreBonus.DoubleLetterScore;
      }

      return ScoreBonus.None;
   }

   @Override
   protected Counter<LetterTile> getLetterCountsForBoardType() {
      Counter<LetterTile> tileCounts = new HashCounter<LetterTile>();

      tileCounts.setCount(LetterTile.newTile('a'), 9);
      tileCounts.setCount(LetterTile.newTile('b'), 2);
      tileCounts.setCount(LetterTile.newTile('c'), 2);
      tileCounts.setCount(LetterTile.newTile('d'), 5);
      tileCounts.setCount(LetterTile.newTile('e'), 13);
      tileCounts.setCount(LetterTile.newTile('f'), 2);
      tileCounts.setCount(LetterTile.newTile('g'), 3);
      tileCounts.setCount(LetterTile.newTile('h'), 4);
      tileCounts.setCount(LetterTile.newTile('i'), 8);
      tileCounts.setCount(LetterTile.newTile('j'), 1);
      tileCounts.setCount(LetterTile.newTile('k'), 1);
      tileCounts.setCount(LetterTile.newTile('l'), 4);
      tileCounts.setCount(LetterTile.newTile('m'), 2);
      tileCounts.setCount(LetterTile.newTile('n'), 5);
      tileCounts.setCount(LetterTile.newTile('o'), 8);
      tileCounts.setCount(LetterTile.newTile('p'), 2);
      tileCounts.setCount(LetterTile.newTile('q'), 1);
      tileCounts.setCount(LetterTile.newTile('r'), 6);
      tileCounts.setCount(LetterTile.newTile('s'), 5);
      tileCounts.setCount(LetterTile.newTile('t'), 7);
      tileCounts.setCount(LetterTile.newTile('u'), 4);
      tileCounts.setCount(LetterTile.newTile('v'), 2);
      tileCounts.setCount(LetterTile.newTile('w'), 2);
      tileCounts.setCount(LetterTile.newTile('x'), 1);
      tileCounts.setCount(LetterTile.newTile('y'), 2);
      tileCounts.setCount(LetterTile.newTile('z'), 1);
      tileCounts.setCount(LetterTile.newBlankTile(), 2);

      return tileCounts;
   }

   private static boolean areSameOrInverse (int row1, int column1, int row2, int column2) {
      return (row1 == row2 && column1 == column2) || (row1 == column2 && column1 == row2);
   }

   @Override
   public int getScore(FullBoardPlacement placementToScore) {
      int score = scorePlacement(placementToScore);

      List<PartialBoardPlacement> secPlacements = placementToScore.getSecondaryPlacements();
      if (secPlacements != null) {
         for (PartialBoardPlacement secPlacement : secPlacements) {
            score += this.scorePlacement(secPlacement);
         }
      }

      if (placementToScore.getAddedTiles().size() == 7) {
         score += 35;
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

            Board.ScoreBonus bonus = this.getBoardTile(tile.getCoordinate()).getScoreBonus();
            if (bonus == Board.ScoreBonus.DoubleLetterScore) {
               initialLetterScore *= 2;
            } else if (bonus == Board.ScoreBonus.TripleLetterScore) {
               initialLetterScore *= 3;
            }

            score += initialLetterScore;
         }
      }

      for (PlacedTile tile : placementToScore.getAddedTiles()) {
         Board.ScoreBonus bonus = this.getBoardTile(tile.getCoordinate()).getScoreBonus();
         if (bonus == Board.ScoreBonus.DoubleWordScore) {
            score *= 2;
         } else if (bonus == Board.ScoreBonus.TripleWordScore) {
            score *= 3;
         }
      }

      return score;
   }

   @Override
   public BoardType getBoardType() {
      return BoardType.WordsWithFriends;
   }

}
