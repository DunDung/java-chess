package chess.domain.chesspiece;

import chess.domain.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

public abstract class RouteUncheckPiece extends Piece {
	private static final String NOT_SUPPORT_MESSAGE = "RouteUncheckPiece에서는 지원하지 않는 기능입니다.";

	public RouteUncheckPiece(Position position, Team team) {
		super(position, team);
	}

	@Override
	public Positions makePathAndValidate(Piece targetPiece) {
		throw new UnsupportedOperationException(NOT_SUPPORT_MESSAGE);
	}
}