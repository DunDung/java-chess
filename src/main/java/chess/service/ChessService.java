package chess.service;

import java.util.List;
import java.util.NoSuchElementException;

import chess.dao.BoardDAO;
import chess.dao.TurnDAO;
import chess.domain.Result;
import chess.domain.Status;
import chess.domain.Team;
import chess.domain.Turn;
import chess.domain.chessboard.Board;
import chess.domain.chesspiece.Piece;
import chess.domain.factory.BoardFactory;
import chess.domain.position.Position;
import chess.dto.PieceDTO;

public class ChessService {
	private static final boolean FIRST_TURN = true;

	private final BoardDAO boardDAO;
	private final TurnDAO turnDAO;

	public ChessService() {
		boardDAO = new BoardDAO();
		turnDAO = new TurnDAO();
	}

	public Board move(Position startPosition, Position targetPosition) {
		Board board = find();
		Piece startPiece = board.findByPosition(startPosition);
		board.move(startPosition, targetPosition);
		boardDAO.update(targetPosition, startPiece.getName());
		boardDAO.update(startPosition, ".");
		turnDAO.changeTurn(board.isWhiteTurn());
		return board;
	}

	public Board find() {
		List<PieceDTO> pieceDTOS = boardDAO.findAll();
		Turn turn;
		try {
			turn = turnDAO.find();
		} catch (NoSuchElementException e) {
			turn = new Turn(FIRST_TURN);
			turnDAO.addTurn(FIRST_TURN);
		}
		if (pieceDTOS.isEmpty()) {
			return createBoard(BoardFactory.createBoard());
		}
		return BoardFactory.createBoard(pieceDTOS, turn);
	}

	private Board createBoard(Board board) {
		List<Piece> pieces = board.findAll();
		for (Piece piece : pieces) {
			boardDAO.addPiece(PieceDTO.from(piece));
		}
		return board;
	}

	public Board restart() {
		boardDAO.removeAll();
		turnDAO.removeAll();
		return find();
	}

	public boolean isEnd() {
		Board board = find();
		return !board.isLiveBothKing();
	}

	public boolean isWinWhiteTeam() {
		Board board = find();
		return board.isLiveKing(Team.WHITE);
	}

	public Result status() {
		Board board = find();
		Status status = board.createStatus();
		return status.getResult();
	}
}
