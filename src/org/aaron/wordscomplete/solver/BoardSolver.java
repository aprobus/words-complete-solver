package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.BoardSolution;

/**
 * User: aprobus
 * Date: 1/20/13
 * Time: 9:46 AM
 */
public abstract class BoardSolver {

   public static final int MAX_SOLUTIONS = 50;

   protected Board mBoard;
   protected Dictionary mDictionary;

   public BoardSolver(Board board, Dictionary dictionary) {
      mBoard = board;
      mDictionary = dictionary;
   }

   public abstract BoardSolution[] solveBoard();
}
