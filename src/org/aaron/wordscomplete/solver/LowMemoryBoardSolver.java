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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 10:51 AM
 */
public class LowMemoryBoardSolver extends BoardSolver {
   private static final int[] WORD_SIZES = new int[]{ 2, 5, 14, 15, 4, 12, 13, 6, 11, 7, 10, 8, 3, 9 };

   private static final int[] EXPENSIVE_WORD_SIZES = new int[]{ 2, 3, 9, 10, 11, 12, 13, 14, 15 };
   private static final int[] CHEAP_WORD_SIZES = new int[]{ 4, 5, 6, 7, 8 };

   private WordFilter[] mWordFilters;
   private PositionFilter[] mPositionFilters;

   public LowMemoryBoardSolver(Board board, TileRack tileRack, Dictionary dictionary) {
      super(board, tileRack, dictionary);

      ExistingTilePositionFilter existingTilePositionFilter = new ExistingTilePositionFilter();
      FullPositionFilter fullPositionFilter = new FullPositionFilter();
      PrePostTilePositionFilter prePostTilePositionFilter = new PrePostTilePositionFilter();
      SecondaryWordPositionFilter secondaryWordPositionFilter = new SecondaryWordPositionFilter(getBoard(), tileRack, getDictionary());

      mPositionFilters = new PositionFilter[4];
      mPositionFilters[0] = existingTilePositionFilter;
      mPositionFilters[1] = prePostTilePositionFilter;
      mPositionFilters[2] = fullPositionFilter;
      mPositionFilters[3] = secondaryWordPositionFilter;

      ExistingTilesFilter existingTilesFilter = new ExistingTilesFilter();
      TileRackFilter tileRackFilter = new TileRackFilter(tileRack);
      SecondaryWordFilter secondaryWordFilter = new SecondaryWordFilter(getDictionary());

      mWordFilters = new WordFilter[3];
      mWordFilters[0] = existingTilesFilter;
      mWordFilters[1] = tileRackFilter;
      mWordFilters[2] = secondaryWordFilter;
   }

   @Override
   public BoardSolution[] solveBoard() {
      BoardSolutions solutions = new BoardSolutions(MAX_SOLUTIONS);

      for (int i = 0; i < WORD_SIZES.length; i++) {
         PositionIterator positionIterator = new PositionIterator(WORD_SIZES[i]);

         while (positionIterator.hasNext()) {
            List<Coordinate> coordinates = positionIterator.next();

            if (!areAllPositionFiltersValid(coordinates)) {
               continue;
            }

            Iterator<String> wordIterator = getDictionary().getWordsForLengthIterator(WORD_SIZES[i]);

            while (wordIterator.hasNext()) {
               String word = wordIterator.next();

               if (areAllWordFiltersValid(coordinates, word)) {
                  solutions.add(createBoardPlacement(word, coordinates));
               }
            }
         }
      }

      return solutions.getSortedSolutions();
   }

   public boolean areAllPositionFiltersValid(List<Coordinate> coordinates) {
      for (PositionFilter filter : mPositionFilters) {
         if (!filter.isValidPosition(getBoard(), coordinates)) {
            return false;
         }
      }

      return true;
   }

   public boolean areAllWordFiltersValid(List<Coordinate> coordinates, String word) {
      for (WordFilter filter : mWordFilters) {
         if (!filter.isValidWord(getBoard(), word, coordinates)) {
            return false;
         }
      }

      return true;
   }

   private BoardSolution createBoardPlacement(String word, List<Coordinate> coordinates) {
      boolean isLeftToRight = true;
      if (coordinates.get(0).row < coordinates.get(1).row) {
         isLeftToRight = false;
      }

      List<LetterTile> existingTiles = new LinkedList<LetterTile>();
      List<PlacedTile> placedTiles = new LinkedList<PlacedTile>();
      List<PartialBoardPlacement> secondaryPlacements = new LinkedList<PartialBoardPlacement>();
      boolean[] usedTiles = new boolean[7];
      List<LetterTile> letterTiles = getTileRack().getLetterTiles();
      for (int i = 0; i < coordinates.size(); i++) {
         LetterTile letterTile = getBoard().getTile(coordinates.get(i));
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
      int score = getBoard().getScore(placement);

      BoardSolution solution = new BoardSolution(word, score, placedTiles);
      return solution;
   }

   private PartialBoardPlacement getSecondaryBoardPlacement(Coordinate coordinate, boolean isLeftToRight, PlacedTile placedTile) {
      if (isLeftToRight && !(getBoard().hasTile(coordinate.row - 1, coordinate.column) || getBoard().hasTile(coordinate.row + 1, coordinate.column))) {
         return null;
      } else if (!isLeftToRight && !(getBoard().hasTile(coordinate.row, coordinate.column - 1) || getBoard().hasTile(coordinate.row, coordinate.column + 1))) {
         return null;
      }

      int rowIncrementer = isLeftToRight ? 1 : 0;
      int columnIncrementer = isLeftToRight ? 0 : 1;

      int currentRow = coordinate.row;
      int currentColumn = coordinate.column;

      while (getBoard().hasTile(currentRow - rowIncrementer, currentColumn - columnIncrementer)) {
         currentRow -= rowIncrementer;
         currentColumn -= columnIncrementer;
      }

      List<LetterTile> existingTiles = new LinkedList<LetterTile>();
      boolean hasEncounteredPlacedTile = false;

      while (getBoard().hasTile(currentRow, currentColumn) || !hasEncounteredPlacedTile) {
         LetterTile letterTile = getBoard().getTile(currentRow, currentColumn);

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

}
