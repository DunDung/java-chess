package chess.controller;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import chess.domain.Result;
import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.ChessPiece;
import chess.domain.position.Position;
import chess.service.ChessService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class ChessController {
	private static final HandlebarsTemplateEngine HANDLEBARS_TEMPLATE_ENGINE = new HandlebarsTemplateEngine();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	private final ChessService chessService;

	public ChessController(ChessService chessService) {
		this.chessService = chessService;
	}

	private static String getBoardJson(ChessBoard chessBoard) {
		Map<String, Object> model = new HashMap<>();
		for (ChessPiece chessPiece : chessBoard.findAll()) {
			model.put(chessPiece.getPositionName(), chessPiece.getName());
		}
		return GSON.toJson(model);
	}

	private static String render(Map<String, Object> model, String templatePath) {
		return HANDLEBARS_TEMPLATE_ENGINE.render(new ModelAndView(model, templatePath));
	}

	public void run() {
		get("/", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			return render(model, "index.html");
		});

		get("/init", (req, res) -> getBoardJson(chessService.find()));

		post("/move", (req, res) -> {
			Position startPosition = Position.of(req.queryParams("startPosition"));
			Position targetPosition = Position.of(req.queryParams("targetPosition"));
			ChessBoard chessBoard = chessService.move(startPosition, targetPosition);
			return getBoardJson(chessBoard);
		});

		get("/isEnd", (req, res) -> {
			Map<String, Object> model = new HashMap<>();
			if (!chessService.isEnd()) {
				model.put("isEnd", false);
				return GSON.toJson(model);
			}
			model.put("isEnd", true);
			if (chessService.isWinWhiteTeam()) {
				model.put("message", "WHITE팀 승리!");
				return GSON.toJson(model);
			}
			model.put("message", "BLACK팀 승리!");
			return GSON.toJson(model);
		});

		get("/restart", (req, res) -> chessService.restart());

		get("/status", (req, res) -> {
			ChessService chessService = new ChessService();
			Result result = chessService.status();
			Map<String, Object> model = new HashMap<>();
			model.put("blackTeamScore", result.getBlackTeamScore());
			model.put("whiteTeamScore", result.getWhiteTeamScore());
			return GSON.toJson(model);
		});
		exception(IllegalArgumentException.class,
			((exception, request, response) -> response.body(exception.getMessage())));
		exception(UnsupportedOperationException.class,
			((exception, request, response) -> response.body(exception.getMessage())));
	}

}
