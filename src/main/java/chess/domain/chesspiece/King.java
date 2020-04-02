package chess.domain.chesspiece;

import static chess.domain.Direction.*;

import java.util.Arrays;
import java.util.List;

import chess.domain.Direction;
import chess.domain.MoveManager;
import chess.domain.Team;
import chess.domain.position.Position;
import chess.domain.utils.NameUtils;

public class King extends WorthlessPiece {
	private static final List<Direction> DIRECTIONS;
	private static final String NAME = "k";

	static {
		DIRECTIONS = Arrays.asList(UP, LEFT, RIGHT, DOWN, RIGHT_DOWN,
			RIGHT_UP, LEFT_DOWN, LEFT_UP);
	}

	public King(Position position, Team team) {
		super(position, team, NAME);
	}

	@Override
	public boolean isNotNeedCheckPath() {
		return true;
	}

	@Override
	public void validateCanGo(ChessPiece targetPiece) {
		Direction direction = moveManager.getMatchDirection(targetPiece.position);
		moveManager.validateMove(direction, DIRECTIONS);
	}
}
