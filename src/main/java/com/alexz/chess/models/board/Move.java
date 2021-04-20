package com.alexz.chess.models.board;

import com.alexz.chess.models.IEntity;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Move implements IEntity {

  private final PieceColor pieceColor;
  private final Tile oldPosition;
  private final Tile newPosition;
  private final IPiece piece;

  public Move(
      final PieceColor pieceColor,
      final Tile oldPosition,
      final Tile newPosition,
      final IPiece piece) {
    this.pieceColor = pieceColor;
    this.oldPosition = oldPosition;
    this.newPosition = newPosition;
    this.piece = piece;
  }

  public PieceColor getPieceColor() {
    return pieceColor;
  }

  public Tile getOldPosition() {
    return oldPosition;
  }

  public Tile getNewPosition() {
    return newPosition;
  }

  public IPiece getPiece() {
    return piece;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Move)) return false;

    final Move move = (Move) o;

    return new EqualsBuilder()
        .append(getPieceColor(), move.getPieceColor())
        .append(getOldPosition(), move.getOldPosition())
        .append(getNewPosition(), move.getNewPosition())
        .append(getPiece(), move.getPiece())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getPieceColor())
        .append(getOldPosition())
        .append(getNewPosition())
        .append(getPiece())
        .toHashCode();
  }
}
