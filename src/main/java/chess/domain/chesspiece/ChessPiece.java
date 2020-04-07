package chess.domain.chesspiece;

import java.util.Objects;
import java.util.function.Function;

import chess.domain.MoveManager;
import chess.domain.Team;
import chess.domain.position.Position;
import chess.domain.position.Positions;

public abstract class ChessPiece {
	protected final Team team;
	protected Position position;
	protected final MoveManager moveManager;

	public ChessPiece(Position position, Team team) {
		this.position = position;
		this.team = team;
		this.moveManager = new MoveManager(this.position);
	}

	public boolean isMatchTeam(Team team) {
		return this.team == team;
	}

	public boolean isNotMatchTeam(Team team) {
		return (this.team == team) == false;
	}

	public boolean isBlankPiece() {
		return this.team == null;
	}

	public boolean isNotBlankPiece() {
		return this.team != null;
	}

	public boolean equalsPosition(Position position) {
		return this.position.equals(position);
	}

	public boolean isSameTeam(ChessPiece chessPiece) {
		return chessPiece.isMatchTeam(this.team);
	}

	public void changePosition(Position position) {
		this.position = position;
		this.moveManager.changePosition(position);
	}

	public void canMove(ChessPiece chessPiece, Function<Position, ChessPiece> findByPosition) {
		if (isNotNeedCheckPath()) {
			validateCanGo(chessPiece);
			return;
		}
		Positions positions = makePathAndValidate(chessPiece);
		positions.validateCanMovePath(findByPosition);
	}

	public Position getPosition() {
		return position;
	}

	public abstract boolean isNotNeedCheckPath();

	public abstract void validateCanGo(ChessPiece targetPiece);

	public abstract Positions makePathAndValidate(ChessPiece targetPiece);

	public abstract String getName();

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ChessPiece that = (ChessPiece)o;
		return team == that.team &&
			position.equals(that.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(team, position);
	}
}