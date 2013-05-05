package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;
import org.aaron.wordscomplete.model.LetterTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 10:30 AM
 */
public interface WordFilter {
   boolean isValidWord(Board board, String word, List<Coordinate> coordinates, LetterTile[] letterTilesFromBoard);
}
