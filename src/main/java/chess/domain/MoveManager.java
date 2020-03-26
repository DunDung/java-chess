package chess.domain;

import static chess.domain.Direction.*;

import java.util.List;
import java.util.stream.Collectors;

import chess.domain.chesspiece.ChessPiece;

public class MoveManager {
	public static final String CANNOT_MOVE_POSITION = "이동할 수 없는 좌표 입니다.";

	private Position nowPosition;

	public MoveManager(Position nowPosition) {
		this.nowPosition = nowPosition;
	}

	public List<Position> makePath(Position targetPosition, List<Direction> directions) {
		Direction direction = calculateDirection(targetPosition, directions);
		return nowPosition.calculatePath(targetPosition, direction);
	}

	public Direction calculateDirection(Position targetPosition, List<Direction> directions) {
		PositionGap positionGap = nowPosition.calculateDiff(targetPosition);
		return positionGap.calculateDirection(directions);
	}

	public void validateMove(Position targetPosition, List<Direction> directions) {
		List<Position> positions = directions.stream()
			.map(direction -> nowPosition.plus(direction))
			.collect(Collectors.toList());

		if (positions.contains(targetPosition) == false) {
			throw new IllegalArgumentException(CANNOT_MOVE_POSITION);
		}
	}

	public void validateKnightMove(Position targetPosition) {
		PositionGap positionGap = nowPosition.calculateDiff(targetPosition);
		if (positionGap.cannotMoveKnight()) {
			throw new IllegalArgumentException(CANNOT_MOVE_POSITION);
		}
	}
}
