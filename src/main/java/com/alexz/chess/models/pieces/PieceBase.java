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

  public PieceBase(PieceColor pieceColor) {
    this.id = UUID.randomUUID().toString();
    this.pieceColor = pieceColor;
  }

  public String getId() {
    return id;
  }

  @Override
  public PieceColor getPieceColor() {
    return this.pieceColor;
  }

  public void setPieceColor(PieceColor pieceColor) {
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

  protected void addAttackMove(
          final String column, final int row, final Map<Tile, IPiece> board, final List<Tile> moves) {
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    final IPiece piece = board.get(move);
    if (piece != null && piece.getPieceColor() != this.getPieceColor()) {
      moves.add(move);
    }
  }

  protected void addMove(
      final String column, final int row, final Map<Tile, IPiece> board, final List<Tile> moves) {
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    if (board.get(move) == null) {
      moves.add(move);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof PieceBase)) return false;

    PieceBase pieceBase = (PieceBase) o;

    return new EqualsBuilder().append(getPieceColor(), pieceBase.getPieceColor()).append(id, pieceBase.id).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(getPieceColor()).append(id).toHashCode();
  }
}
