package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.model.BoardSolution;
import org.aaron.wordscomplete.model.scoring.BoardPlacement;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * User: aprobus
 * Date: 1/20/13
 * Time: 11:24 AM
 */
public class BoardSolutions {
   private PriorityQueue<BoardSolution> mSolutions;
   private int mMaxSolutions;

   public BoardSolutions(int maxSolutions) {
      mMaxSolutions = maxSolutions;

      mSolutions = new PriorityQueue<BoardSolution>(maxSolutions, new LowestToHighestScoreComparator());
   }

   public BoardSolutions() {
      this(30);
   }

   public void add(BoardSolution solution) {
      if(mSolutions.size() < mMaxSolutions ) {
         mSolutions.add(solution);
      } else if (mSolutions.peek().getScore() < solution.getScore()) {
         mSolutions.poll();
         mSolutions.add(solution);
      }
   }

   public void addAll(BoardSolution[] solutions) {
      for (BoardSolution solution : solutions) {
         this.add(solution);
      }
   }

   public BoardSolution[] getSortedSolutions() {
      BoardSolution[] solutions = new BoardSolution[mSolutions.size()];

      mSolutions.toArray(solutions);
      Arrays.sort(solutions, new HighestToLowestScoreComparator());

      return solutions;
   }

   private class LowestToHighestScoreComparator implements Comparator<BoardSolution> {
      @Override
      public int compare(BoardSolution lhs, BoardSolution rhs) {
         return lhs.getScore() - rhs.getScore();
      }
   }

   private class HighestToLowestScoreComparator implements Comparator<BoardSolution> {
      @Override
      public int compare(BoardSolution lhs, BoardSolution rhs) {
         return rhs.getScore() - lhs.getScore();
      }
   }

   private static void reverse(BoardPlacement[] solutions) {
      for (int i = 0; i < solutions.length / 2; i++) {
         BoardPlacement temp = solutions[i];

         int endIndex = solutions.length - 1 - i;
         solutions[i] = solutions[endIndex];
         solutions[endIndex] = temp;
      }
   }
}
