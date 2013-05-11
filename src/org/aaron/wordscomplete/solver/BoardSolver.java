package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.BoardSolution;
import org.aaron.wordscomplete.model.TileRack;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: aprobus
 * Date: 1/20/13
 * Time: 9:46 AM
 */
public abstract class BoardSolver {

   public static final int MAX_SOLUTIONS = 50;

   private Board board;
   private TileRack tileRack;
   private Dictionary dictionary;

   public BoardSolver(Board board, TileRack tileRack, Dictionary dictionary) {
      this.board = board;
      this.tileRack = tileRack;
      this.dictionary = dictionary;
   }

   public Dictionary getDictionary() {
      return dictionary;
   }

   public void setDictionary(Dictionary dictionary) {
      this.dictionary = dictionary;
   }

   public TileRack getTileRack() {
      return tileRack;
   }

   public void setTileRack(TileRack tileRack) {
      this.tileRack = tileRack;
   }

   public Board getBoard() {
      return board;
   }

   public void setBoard(Board board) {
      this.board = board;
   }

   public abstract BoardSolution[] solveBoard();
}
