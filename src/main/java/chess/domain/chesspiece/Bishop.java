package chess.domain.chesspiece;

import static chess.domain.Direction.*;

import java.util.Arrays;
import java.util.List;

import chess.domain.Direction;
import chess.domain.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

public class Bishop extends RouteCheckPiece {
	private static final List<Direction> DIRECTIONS;
	private static final String NAME = "b";

	static {
		DIRECTIONS = Arrays.asList(LEFT_UP, LEFT_DOWN, RIGHT_DOWN, RIGHT_UP);
	}

	public Bishop(Position position, Team team) {
		super(position, team);
	}

	@Override
	public Positions makePathAndValidate(Piece targetPiece) {
		return moveManager.makePath(targetPiece.position, DIRECTIONS);
	}

	@Override
	public boolean isNotNeedCheckPath() {
		return false;
	}

	@Override
	public String getName() {
		return team.parseName(NAME);
	}

}