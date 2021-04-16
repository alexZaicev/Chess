package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class PieceBase implements IPiece {

  protected final String id;
  protected PieceColor pieceColor;

  public PieceBase(final PieceColor pieceColor) {
    this.id = UUID.randomUUID().toString();
    this.pieceColor = pieceColor;
  }

  @Override
  public PieceColor getPieceColor() {
    return this.pieceColor;
  }

  public void setPieceColor(final PieceColor pieceColor) {
    this.pieceColor = pieceColor;
  }

  protected Tile getCurrentPosition(final Map<Tile, IPiece> board) {
    Tile currentPos = null;
    for (final Map.Entry<Tile, IPiece> e : board.entrySet()) {
      if (this.equals(e.getValue())) {
        currentPos = e.getKey();
        break;
      }
    }
    return currentPos;
  }

  protected boolean addAttackMove(
      final char column, final int row, final Map<Tile, IPiece> board, final List<Tile> moves) {
    if (this.isColRowOutOfBounds(column, row)) {
      return false;
    }
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    final IPiece piece = board.get(move);
    if (piece != null && piece.getPieceColor() != this.getPieceColor()) {
      moves.add(move);
      return true;
    }
    return false;
  }

  protected boolean addMove(
      final char column, final int row, final Map<Tile, IPiece> board, final List<Tile> moves) {
    if (this.isColRowOutOfBounds(column, row)) {
      return false;
    }
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    if (board.get(move) == null) {
      moves.add(move);
      return true;
    }
    return false;
  }

  protected boolean isColRowOutOfBounds(final char col, final int row) {
    return col < 'A' || col > 'H' || row < 1 || row > 8;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof PieceBase)) return false;

    final PieceBase pieceBase = (PieceBase) o;

    return new EqualsBuilder()
        .append(getPieceColor(), pieceBase.getPieceColor())
        .append(id, pieceBase.id)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getPieceColor()).append(id).toHashCode();
  }
}
