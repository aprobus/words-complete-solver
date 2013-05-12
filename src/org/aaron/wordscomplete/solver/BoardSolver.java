package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.BoardSolution;
import org.aaron.wordscomplete.model.TileRack;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * User: aprobus
 * Date: 1/20/13
 * Time: 9:46 AM
 */
public interface BoardSolver extends Callable<BoardSolution[]> {

   public static final int MAX_SOLUTIONS = 50;

   void cancel();
   boolean isCanceled();
}
