package org.aaron.wordscomplete.web.response;

import org.aaron.wordscomplete.model.BoardSolution;

/**
 * User: aprobus
 * Date: 4/24/13
 * Time: 6:09 PM
 */
public class BoardSolutionResponse {

   public static final String PARAM_RESPONSE_CODE = "responseCode";
   public static final String PARAM_SOLUTIONS = "solutions";

   public String responseCode;
   public BoardSolution[] solutions;

   public BoardSolutionResponse(String responseCode, BoardSolution[] solutions) {
      this.responseCode = responseCode;
      this.solutions = solutions;
   }

}
