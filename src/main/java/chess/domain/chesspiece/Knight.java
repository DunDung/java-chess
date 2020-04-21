package chess.domain.chesspiece;

import chess.domain.Team;
import chess.domain.position.Position;

public class Knight extends WorthlessPiece {
	private static final String NAME = "n";

	public Knight(Position position, Team team) {
		super(position, team);
	}

	@Override
	public String getName() {
		return team.parseName(NAME);
	}

	@Override
	public boolean isNotNeedCheckPath() {
		return true;
	}

	@Override
	public void validateCanGo(ChessPiece targetPiece) {
		moveManager.validateKnightMove(targetPiece.position);
	}
}