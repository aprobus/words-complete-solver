package org.aaron.wordscomplete.model.scoring;

import org.aaron.wordscomplete.model.LetterTile;
import org.aaron.wordscomplete.model.PlacedTile;

import java.util.List;

/**
 * User: aprobus
 * Date: 4/22/13
 * Time: 8:02 PM
 */
public interface BoardPlacement  {

   public List<PlacedTile> getAddedTiles();
   public List<LetterTile> getExistingTiles();

}
