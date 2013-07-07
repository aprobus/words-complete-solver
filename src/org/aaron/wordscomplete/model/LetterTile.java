package org.aaron.wordscomplete.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 7:14 PM
 */
public class LetterTile implements Comparable<LetterTile> {

   public static LetterTile newBlankTile() {
      return new LetterTile('a', true);
   }

   public static LetterTile newBlankTileWithLetter(char letter) {
      return new LetterTile(letter, true);
   }

   public static LetterTile newTile(char letter) {
      return new LetterTile(letter, false);
   }

   private char letter;
   private boolean isBlank = true;

   public LetterTile (char tileLetter, boolean isBlank) {
      this.letter = Character.toLowerCase(tileLetter);
      this.isBlank = isBlank;
   }

   public LetterTile (char letter) {
      this(letter, false);
   }

   public LetterTile () {
      this('a', true);
   }

   public char getLetter() {
      return letter;
   }

   public boolean isBlank() {
      return isBlank;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if(this == obj) {
         return true;
      } else if (!(obj instanceof LetterTile)) {
         return false;
      }

      LetterTile otherTile = (LetterTile)obj;

      if (isBlank && otherTile.isBlank) {
         return true;
      } else {
         return letter == otherTile.letter && isBlank == otherTile.isBlank;
      }
   }

   @Override
   public int hashCode() {
      return letter;
   }

   @Override
   public int compareTo(LetterTile letterTile) {
      if (letterTile == null) {
         return -1;
      }

      if (isBlank && letterTile.isBlank) {
         return 0;
      } else if (isBlank && !letterTile.isBlank) {
         return 1;
      } else if (!isBlank && letterTile.isBlank) {
         return -1;
      }

      if (letter > letterTile.letter) {
         return 1;
      } else if (letter < letterTile.letter) {
         return -1;
      } else {
         return 0;
      }
   }

   public static List<LetterTile> values() {
      List<LetterTile> allLetterTiles = new ArrayList<LetterTile>(27);

      for (char currentLetter = 'a'; currentLetter <= 'z'; currentLetter++) {
         allLetterTiles.add(LetterTile.newTile(currentLetter));
      }

      allLetterTiles.add(LetterTile.newBlankTile());

      Collections.sort(allLetterTiles);

      return allLetterTiles;
   }

}
