package org.aaron.wordscomplete.model;

import org.aaron.wordscomplete.model.scoring.FullBoardPlacement;
import org.aaron.wordscomplete.util.Counter;
import org.aaron.wordscomplete.util.HashCounter;

import java.util.Collection;
import java.util.Map;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:18 PM
 */
public abstract class Board {

   protected BoardTile[] board;
   protected boolean isEmpty = true;

   public static final int NUM_ROWS = 15;
   public static final int NUM_COLUMNS = 15;

   protected Board () {
      board = new BoardTile[NUM_COLUMNS * NUM_ROWS];

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            board[toIndex(row, column)] = new BoardTile(getScoreBonusForCoordinate(row, column));
         }
      }
   }

   public static Board ofType(BoardType boardType) {
      switch(boardType) {
         case Scrabble:
            return new ScrabbleBoard();
         case WordsWithFriends:
            return new WordsWithFriendsBoard();
         default:
            throw new IllegalArgumentException("Illegal board type: " + boardType.toString());
      }
   }

   public abstract int getScore(FullBoardPlacement placementToScore);

   public abstract BoardType getBoardType();

   protected abstract ScoreBonus getScoreBonusForCoordinate(int row, int column);

   public boolean hasTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return false;
      } else {
         return board[toIndex(row, column)].getTile() != null;
      }
   }

   public LetterTile getTile(Coordinate coordinate) {
      return getTile(coordinate.getRow(), coordinate.getColumn());
   }

   public LetterTile getTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return null;
      } else {
         return board[toIndex(row, column)].getTile();
      }
   }

   public boolean hasTile(Coordinate coordinate) {
      return hasTile(coordinate.getRow(), coordinate.getColumn());
   }

   public void clearTile (int row, int column) {
      if (row >= 0 && column >= 0 && row < NUM_ROWS && column < NUM_COLUMNS) {
         board[toIndex(row, column)].setTile(null);
      }
   }

   public BoardTile getBoardTile (int row, int column) {
      if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS) {
         return null;
      } else {
         return board[toIndex(row, column)];
      }
   }

   public BoardTile getBoardTile(Coordinate coordinate) {
      return getBoardTile(coordinate.getRow(), coordinate.getColumn());
   }

   public void setTile (int row, int column, LetterTile tile) {
      this.isEmpty = false;
      board[toIndex(row, column)].setTile(tile);
   }

   public void setTile(Coordinate coordinate, LetterTile tile) {
      setTile(coordinate.getRow(), coordinate.getColumn(), tile);
   }

   public boolean isEmpty() {
      return isEmpty;
   }

   @Override
   public String toString () {
      StringBuilder boardString = new StringBuilder();

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            if (this.hasTile(row, column)) {
               boardString.append(this.getTile(row, column).getLetter());
            } else {
               boardString.append(' ');
            }
         }

         boardString.append('\n');
      }

      return boardString.toString();
   }

   public Counter<LetterTile> getRemainingTileCounts(TileRack... tileRacks) {
      Counter<LetterTile> tileCounter = getLetterCountsForBoardType();

      for (int row = 0; row < NUM_ROWS; row++) {
         for (int column = 0; column < NUM_COLUMNS; column++) {
            if (hasTile(row, column)) {
               tileCounter.deincrement(getTile(row, column));
            }
         }
      }

      if (tileRacks == null) {
         return tileCounter;
      }

      for (TileRack tileRack : tileRacks) {
         for (LetterTile tile : tileRack.getLetterTiles()) {
            tileCounter.deincrement(tile);
         }
      }

      return tileCounter;
   }

   protected abstract Counter<LetterTile> getLetterCountsForBoardType();

   private static int toIndex(int row, int column) {
      return row * Board.NUM_COLUMNS + column;
   }

   public enum ScoreBonus {
      DoubleLetterScore,
      TripleLetterScore,
      DoubleWordScore,
      TripleWordScore,
      None
   }

}
