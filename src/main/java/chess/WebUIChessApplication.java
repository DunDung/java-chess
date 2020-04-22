package chess;

import static spark.Spark.*;

import chess.controller.Controller;
import chess.service.ChessService;

public class WebUIChessApplication {

	public static void main(String[] args) {
		staticFileLocation("/templates");

		ChessService chessService = new ChessService();
		Controller controller = new Controller(chessService);
		controller.run();
	}
}
