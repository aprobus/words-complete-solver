package org.aaron.wordscomplete.transformer;

import org.junit.Assert;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.BoardType;
import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.ScrabbleBoard;
import org.junit.Test;

/**
 * User: aprobus
 * Date: 4/27/13
 * Time: 8:28 PM
 */
public class BoardStringTransformerTest {
   @Test
   public void testBlank() throws Exception {
      Board board = Board.ofType(BoardType.Scrabble);
      board.setTile(4, 5, new LetterTile('b', true));
      board.setTile(6, 9, new LetterTile('d'));

      String boardString = BoardStringTransformer.toString(board);

      Board boardFromString = BoardStringTransformer.fromString(boardString, BoardType.Scrabble);

      Assert.assertTrue(boardFromString.hasTile(4, 5));
      Assert.assertEquals(board.getTile(4, 5), boardFromString.getTile(4, 5));
      Assert.assertEquals(board.getTile(6, 9), boardFromString.getTile(6, 9));
   }

}
