package org.aaron.wordscomplete.solver.filters.positionfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/27/13
 * Time: 10:21 AM
 */
public abstract class PositionFilter {
   public abstract boolean isValidPosition(Board board, List<Coordinate> coordinates);
}
