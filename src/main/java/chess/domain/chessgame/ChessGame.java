package chess.domain.chessgame;

import chess.domain.chessboard.Board;
import chess.domain.factory.BoardFactory;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGame {
	private final Board board;
	private Command command;

	public ChessGame() {
		this.board = BoardFactory.createBoard();
	}

	public void play() {
		OutputView.printRule();
		initMenu();
		command.validateStart();
		OutputView.printBoard(board);

		while (command.isNotEnd() && board.isLiveBothKing()) {
			initMenu();
			playRound();
		}
		OutputView.printEndGame();
	}

	private void initMenu() {
		while (isNotAllowedMenu());
	}

	private boolean isNotAllowedMenu() {
		try {
			command = new Command(InputView.inputMenu());
			return false;
		} catch (IllegalArgumentException | UnsupportedOperationException e) {
			OutputView.printErrorMessage(e);
			return true;
		}
	}

	private void playRound() {
		try {
			command.execute(board);
			OutputView.printBoard(board);
		} catch (Exception e) {
			OutputView.printErrorMessage(e);
		}
	}
}
