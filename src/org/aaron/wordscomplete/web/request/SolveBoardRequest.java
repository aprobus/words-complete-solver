package org.aaron.wordscomplete.web.request;

import com.loopj.android.http.RequestParams;
import org.aaron.wordscomplete.model.Board;
import org.aaron.wordscomplete.model.TileRack;
import org.aaron.wordscomplete.transformer.BoardStringTransformer;
import org.aaron.wordscomplete.transformer.TileRackStringTransformer;

/**
 * User: aprobus
 * Date: 4/24/13
 * Time: 6:10 PM
 */
public class SolveBoardRequest extends RequestParams {

   public static final String PARAM_BOARD = "board";
   public static final String PARAM_TILE_RACK = "tileRack";
   public static final String PARAM_BOARD_TYPE = "boardType";
   public static final String PARAM_DICT_TYPE = "dictType";

   public SolveBoardRequest(Board board, TileRack tileRack) {
      put(PARAM_BOARD, BoardStringTransformer.toString(board));
      put(PARAM_TILE_RACK, TileRackStringTransformer.toString(tileRack));
      put(PARAM_BOARD_TYPE, board.getBoardType().toString());
      put(PARAM_DICT_TYPE, board.getBoardType().toString());
   }

}
