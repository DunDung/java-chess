package chess;

import static spark.Spark.*;

import chess.controller.ChessController;
import chess.service.ChessService;

public class WebUIChessApplication {

	public static void main(String[] args) {
		staticFileLocation("/templates");

		ChessService chessService = new ChessService();
		new ChessController(chessService).run();
	}
}
