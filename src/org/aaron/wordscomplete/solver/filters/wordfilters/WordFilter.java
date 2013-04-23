package org.aaron.wordscomplete.solver.filters.wordfilters;

import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.Coordinate;

import java.util.List;

/**
 * User: aprobus
 * Date: 1/26/13
 * Time: 10:30 AM
 */
public abstract class WordFilter<T> {

   protected T mFilterValue;

   public WordFilter() {
      this(null);
   }

   public WordFilter(T filterValue) {
      mFilterValue = filterValue;
   }

   public void setFilter(T filterValue) {
      mFilterValue = filterValue;
   }

   public T getFilterValue() {
      return mFilterValue;
   }

   public abstract boolean isValidWord(Board board, String word, List<Coordinate> coordinates);
}
