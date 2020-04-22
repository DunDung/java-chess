package chess.domain.chesspiece;

import chess.domain.Team;
import chess.domain.position.Position;

public abstract class RouteCheckPiece extends Piece {
	private static final String NOT_SUPPORT_MESSAGE = "RoutecheckPiece에서는 지원하지 않는 기능입니다.";

	public RouteCheckPiece(Position position, Team team) {
		super(position, team);
	}

	@Override
	public void validateCanGo(Piece targetPiece) {
		throw new UnsupportedOperationException(NOT_SUPPORT_MESSAGE);
	}
}