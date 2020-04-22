package chess.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

import chess.domain.chessboard.Board;
import chess.domain.factory.BoardFactory;

public class StatusTest {

	@Test
	void getResultTest() {
		Board board = BoardFactory.createBoard();
		Status status = board.createStatus();

		assertThat(status.getResult().getBlackTeamScore()).isEqualTo(38);
		assertThat(status.getResult().getWhiteTeamScore()).isEqualTo(38);
	}
}
