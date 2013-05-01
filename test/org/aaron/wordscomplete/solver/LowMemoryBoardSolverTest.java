package org.aaron.wordscomplete.solver;

import org.aaron.wordscomplete.dictionary.Dictionary;
import org.aaron.wordscomplete.dictionary.SegmentedArrayDictionary;
import org.aaron.wordscomplete.model.*;
import org.aaron.wordscomplete.model.scoring.BoardPlacement;
import org.aaron.wordscomplete.transformer.TileRackStringTransformer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * User: aprobus
 * Date: 4/27/13
 * Time: 9:51 PM
 */
public class LowMemoryBoardSolverTest {

   private Dictionary mDictionary;
   private Board mBoard;

   @Before
   public void setupDictionary() {
      mBoard = Board.ofType(BoardType.Scrabble);

      mDictionary = new SegmentedArrayDictionary();

      for (String word = "aa"; word.length() < 16; word += "a") {
         mDictionary.setWords(new String[]{word});
      }
   }

   @Test
   public void testSimpleSolutionVert() {
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(7, 6, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(7, 8, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("te"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(1, solutions.length);
      Assert.assertEquals("the", solutions[0].getWord());
      Assert.assertEquals(2, solutions[0].getPlacedTiles().size());
      Assert.assertEquals(Coordinate.newCoordinate(6, 7), solutions[0].getPlacedTiles().get(0).getCoordinate());
      Assert.assertEquals(Coordinate.newCoordinate(8, 7), solutions[0].getPlacedTiles().get(1).getCoordinate());
   }

   @Test
   public void testSimpleSolutionHori() {
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(6, 7, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(8, 7, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("te"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(1, solutions.length);
      Assert.assertEquals("the", solutions[0].getWord());
      Assert.assertEquals(2, solutions[0].getPlacedTiles().size());
      Assert.assertEquals(Coordinate.newCoordinate(7, 6), solutions[0].getPlacedTiles().get(0).getCoordinate());
      Assert.assertEquals(Coordinate.newCoordinate(7, 8), solutions[0].getPlacedTiles().get(1).getCoordinate());
   }

   @Test
   public void testIndirectSolutionHori() {
      mDictionary.setWords(new String[]{"eb"});
      mDictionary.setWords(new String[]{"boo"});
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(7, 6, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(7, 8, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("boo"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(2, solutions.length);
      Assert.assertEquals("boo", solutions[0].getWord());
      Assert.assertEquals("eb", solutions[1].getWord());

      Assert.assertEquals(3, solutions[0].getPlacedTiles().size());
      Assert.assertEquals(Coordinate.newCoordinate(8, 8), solutions[0].getPlacedTiles().get(0).getCoordinate());
      Assert.assertEquals(Coordinate.newCoordinate(8, 9), solutions[0].getPlacedTiles().get(1).getCoordinate());
      Assert.assertEquals(Coordinate.newCoordinate(8, 10), solutions[0].getPlacedTiles().get(2).getCoordinate());

      Assert.assertEquals(1, solutions[1].getPlacedTiles().size());
      Assert.assertEquals(Coordinate.newCoordinate(8, 8), solutions[0].getPlacedTiles().get(0).getCoordinate());
   }

   @Test
   public void testIndirectSolutionVert() {
      mDictionary.setWords(new String[]{"eb"});
      mDictionary.setWords(new String[]{"boo"});
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(6, 7, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(8, 7, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("boo"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(2, solutions.length);
      Assert.assertEquals("boo", solutions[0].getWord());
      Assert.assertEquals("eb", solutions[1].getWord());
   }

   @Test
   public void testSolutionSandwhichHori() {
      mDictionary.setWords(new String[]{"emo"});
      mDictionary.setWords(new String[]{"hop"});
      mDictionary.setWords(new String[]{"om"});
      mDictionary.setWords(new String[]{"pot"});
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(7, 6, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(7, 8, new LetterTile('e'));

      mBoard.setTile(9, 7, new LetterTile('p'));
      mBoard.setTile(9, 8, new LetterTile('o'));
      mBoard.setTile(9, 9, new LetterTile('t'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("om"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(4, solutions.length);
      Assert.assertEquals("om", solutions[0].getWord());
   }

   @Test
   public void testSolutionSandwhichVert() {
      mDictionary.setWords(new String[]{"emo"});
      mDictionary.setWords(new String[]{"hop"});
      mDictionary.setWords(new String[]{"om"});
      mDictionary.setWords(new String[]{"pot"});
      mDictionary.setWords(new String[]{"the"});

      mBoard.setTile(6, 7, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(8, 7, new LetterTile('e'));

      mBoard.setTile(7, 9, new LetterTile('p'));
      mBoard.setTile(8, 9, new LetterTile('o'));
      mBoard.setTile(9, 9, new LetterTile('t'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("om"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(4, solutions.length);
      Assert.assertEquals("om", solutions[0].getWord());
   }

   @Test
   public void testExtendWordSameDirectionHori() {
      mDictionary.setWords(new String[]{"the", "thesis"});

      mBoard.setTile(7, 6, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(7, 8, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("sis"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(1, solutions.length);
   }

   @Test
   public void testExtendWordSameDirectionVert() {
      mDictionary.setWords(new String[]{"the", "thesis"});

      mBoard.setTile(6, 7, new LetterTile('t'));
      mBoard.setTile(7, 7, new LetterTile('h'));
      mBoard.setTile(8, 7, new LetterTile('e'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("sis"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(1, solutions.length);
   }

   @Test
   public void testExtendWordOppositeDirectionHori() {
      mDictionary.setWords(new String[]{"bra", "bras"});
      mDictionary.setWords(new String[]{"is"});

      mBoard.setTile(6, 7, new LetterTile('b'));
      mBoard.setTile(7, 7, new LetterTile('r'));
      mBoard.setTile(8, 7, new LetterTile('a'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("is"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(2, solutions.length);
      Assert.assertEquals("is", solutions[0].getWord());
   }

   @Test
   public void testExtendWordOppositeDirectionVert() {
      mDictionary.setWords(new String[]{"bra", "bras"});
      mDictionary.setWords(new String[]{"is"});

      mBoard.setTile(7, 6, new LetterTile('b'));
      mBoard.setTile(7, 7, new LetterTile('r'));
      mBoard.setTile(7, 8, new LetterTile('a'));

      LowMemoryBoardSolver lowMemoryBoardSolver = new LowMemoryBoardSolver(mBoard, TileRackStringTransformer.fromString("is"), mDictionary);
      BoardSolution[] solutions = lowMemoryBoardSolver.solveBoard();

      Assert.assertEquals(2, solutions.length);
      Assert.assertEquals("is", solutions[0].getWord());
   }

}
