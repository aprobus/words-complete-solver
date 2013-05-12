package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.model.*;
import org.aaron.wordscomplete.model.scoring.FullBoardPlacement;
import org.aaron.wordscomplete.model.scoring.PartialBoardPlacement;
import org.aaron.wordscomplete.solver.filters.positionfilters.*;
import org.aaron.wordscomplete.solver.filters.wordfilters.ExistingTilesFilter;
import org.aaron.wordscomplete.solver.filters.wordfilters.SecondaryWordFilter;
import org.aaron.wordscomplete.solver.filters.wordfilters.TileRackFilter;
import org.aaron.wordscomplete.solver.filters.wordfilters.WordFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 10:51 AM
 */
public class LowMemoryBoardSolver implements BoardSolver {

   private static final int THREAD_COUNT = 4;

   private static final int MIN_WORD_SIZE = 2;
   private static final int MAX_WORD_SIZE = 15;

   private ExecutorService threadPool;

   private boolean isCanceled = false;

   private Board board;
   private TileRack tileRack;
   private Dictionary dictionary;

   private WordFilter[] mWordFilters;
   private PositionFilter[] mPositionFilters;

   public LowMemoryBoardSolver(Board board, TileRack tileRack, Dictionary dictionary, ExecutorService threadPool) {
      this.board = board;
      this.tileRack = tileRack.moveBlanksToEnd();
      this.dictionary = dictionary;

      this.threadPool = threadPool;

      ExistingTilePositionFilter existingTilePositionFilter = new ExistingTilePositionFilter(board);
      FullPositionFilter fullPositionFilter = new FullPositionFilter(board);
      PrePostTilePositionFilter prePostTilePositionFilter = new PrePostTilePositionFilter(board);
      SecondaryWordPositionFilter secondaryWordPositionFilter = new SecondaryWordPositionFilter(this.board, this.tileRack, dictionary);
      FillAllPositionsFilter fillAllPositionsFilter = new FillAllPositionsFilter(board, this.tileRack);
      SimilarWordFilter similarWordFilter = new SimilarWordFilter(board, dictionary);

      mPositionFilters = new PositionFilter[] {
         existingTilePositionFilter,
         prePostTilePositionFilter,
         fullPositionFilter,
         secondaryWordPositionFilter,
         fillAllPositionsFilter,
         similarWordFilter
      };

      ExistingTilesFilter existingTilesFilter = new ExistingTilesFilter();
      TileRackFilter tileRackFilter = new TileRackFilter(this.tileRack);
      SecondaryWordFilter secondaryWordFilter = new SecondaryWordFilter(dictionary);

      mWordFilters = new WordFilter[3];
      mWordFilters[0] = existingTilesFilter;
      mWordFilters[1] = tileRackFilter;
      mWordFilters[2] = secondaryWordFilter;
   }

   @Override
   public void cancel() {
      isCanceled = true;
   }

   @Override
   public boolean isCanceled() {
      return isCanceled;
   }

   @Override
   public BoardSolution[] call() {
      BoardSolutions solutions = new BoardSolutions(BoardSolver.MAX_SOLUTIONS);

      List<BoardSolverMinorCallable> solverFutures = new ArrayList<BoardSolverMinorCallable>(THREAD_COUNT);
      for (int i = 0; i < THREAD_COUNT; i++) {
         solverFutures.add(new BoardSolverMinorCallable(solutions, THREAD_COUNT, i));
      }

      try {
         threadPool.invokeAll(solverFutures);
      } catch (InterruptedException e) {
         return null;
      }

      return solutions.getSortedSolutions();
   }

   private boolean areAllPositionFiltersValid(List<Coordinate> coordinates) {
      for (PositionFilter filter : mPositionFilters) {
         if (!filter.isValidPosition(coordinates)) {
            return false;
         }
      }

      return true;
   }

   private boolean areAllWordFiltersValid(List<Coordinate> coordinates, LetterTile[] letterTilesForCoordinates, String word) {
      //Faster than looping
      return mWordFilters[0].isValidWord(board, word, coordinates, letterTilesForCoordinates)
            && mWordFilters[1].isValidWord(board, word, coordinates, letterTilesForCoordinates)
            && mWordFilters[2].isValidWord(board, word, coordinates, letterTilesForCoordinates);
   }

   private BoardSolution createBoardSolution(String word, List<Coordinate> coordinates) {
      boolean isLeftToRight = true;
      if (coordinates.get(0).getRow() < coordinates.get(1).getRow()) {
         isLeftToRight = false;
      }

      List<LetterTile> existingTiles = new LinkedList<LetterTile>();
      List<PlacedTile> placedTiles = new LinkedList<PlacedTile>();
      List<PartialBoardPlacement> secondaryPlacements = new LinkedList<PartialBoardPlacement>();
      boolean[] usedTiles = new boolean[7];
      List<LetterTile> letterTiles = tileRack.getLetterTiles();
      for (int i = 0; i < coordinates.size(); i++) {
         LetterTile letterTile = board.getTile(coordinates.get(i));
         if (letterTile != null) {
            existingTiles.add(letterTile);
         } else {
            LetterTile newLetterTile = getUnusedLetterTile(letterTiles, usedTiles, word.charAt(i));
            PlacedTile placedTile = new PlacedTile(newLetterTile, coordinates.get(i));
            placedTiles.add(placedTile);

            PartialBoardPlacement secondaryPlacement = getSecondaryBoardPlacement(coordinates.get(i), isLeftToRight, placedTile);
            if (secondaryPlacement != null) {
               secondaryPlacements.add(secondaryPlacement);
            }
         }
      }

      FullBoardPlacement placement = new FullBoardPlacement(placedTiles, existingTiles, secondaryPlacements);
      int score = board.getScore(placement);

      BoardSolution solution = new BoardSolution(word, score, placedTiles);
      return solution;
   }

   private PartialBoardPlacement getSecondaryBoardPlacement(Coordinate coordinate, boolean isLeftToRight, PlacedTile placedTile) {
      if (isLeftToRight && !(board.hasTile(coordinate.getRow() - 1, coordinate.getColumn()) || board.hasTile(coordinate.getRow() + 1, coordinate.getColumn()))) {
         return null;
      } else if (!isLeftToRight && !(board.hasTile(coordinate.getRow(), coordinate.getColumn() - 1) || board.hasTile(coordinate.getRow(), coordinate.getColumn() + 1))) {
         return null;
      }

      int rowIncrementer = isLeftToRight ? 1 : 0;
      int columnIncrementer = isLeftToRight ? 0 : 1;

      int currentRow = coordinate.getRow();
      int currentColumn = coordinate.getColumn();

      while (board.hasTile(currentRow - rowIncrementer, currentColumn - columnIncrementer)) {
         currentRow -= rowIncrementer;
         currentColumn -= columnIncrementer;
      }

      List<LetterTile> existingTiles = new LinkedList<LetterTile>();
      boolean hasEncounteredPlacedTile = false;

      while (board.hasTile(currentRow, currentColumn) || !hasEncounteredPlacedTile) {
         LetterTile letterTile = board.getTile(currentRow, currentColumn);

         if (letterTile == null) {
            hasEncounteredPlacedTile = true;
         } else {
            existingTiles.add(letterTile);
         }

         currentRow += rowIncrementer;
         currentColumn += columnIncrementer;
      }

      List<PlacedTile> placedTiles = new LinkedList<PlacedTile>();
      placedTiles.add(placedTile);

      PartialBoardPlacement solution = new PartialBoardPlacement(placedTiles, existingTiles);

      return solution;
   }

   private LetterTile getUnusedLetterTile(List<LetterTile> letterTiles, boolean[] usedTiles, char letterToPlace) {
      for (int i = 0; i < letterTiles.size(); i++) {
         if (!usedTiles[i] && !letterTiles.get(i).isBlank() && letterTiles.get(i).getLetter() == letterToPlace) {
            usedTiles[i] = true;
            return new LetterTile(letterToPlace, false);
         }
      }

      for (int i = 0; i < letterTiles.size(); i++) {
         if (!usedTiles[i] && letterTiles.get(i).isBlank()) {
            usedTiles[i] = true;
            return new LetterTile(letterToPlace, true);
         }
      }

      throw new IllegalStateException("Unable to find unused letter tile");
   }

   private class BoardSolverMinorCallable implements Callable<Void> {

      private BoardSolutions solutions;
      private int incrementBy;
      private int initialOffset;

      private BoardSolverMinorCallable(BoardSolutions solutions, int incrementBy, int initialOffset) {
         this.solutions = solutions;
         this.incrementBy = incrementBy;
         this.initialOffset = initialOffset;
      }

      @Override
      public Void call() throws Exception {
         for (int wordSize = MIN_WORD_SIZE; wordSize < MAX_WORD_SIZE && !isCanceled; wordSize++) {
            PositionIterator positionIterator = new PositionIterator(wordSize);

            while (positionIterator.hasNext() && !isCanceled) {
               List<Coordinate> coordinates = positionIterator.next();

               if (!areAllPositionFiltersValid(coordinates)) {
                  continue;
               }

               LetterTile[] letterTilesForCoordinates = new LetterTile[coordinates.size()];
               for (int i = 0; i < coordinates.size(); i++) {
                  letterTilesForCoordinates[i] = board.getTile(coordinates.get(i));
               }

               Iterator<String> wordIterator = dictionary.getWordsForLengthIterator(wordSize, incrementBy, initialOffset);

               while (wordIterator.hasNext()) {
                  String word = wordIterator.next();

                  if (areAllWordFiltersValid(coordinates, letterTilesForCoordinates, word)) {
                     solutions.add(createBoardSolution(word, coordinates));
                  }
               }
            }
         }

         return null;
      }
   }

}
