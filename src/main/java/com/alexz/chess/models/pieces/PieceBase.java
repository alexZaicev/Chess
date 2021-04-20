package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;
import java.util.UUID;

public abstract class PieceBase implements IPiece {

  protected final String id;
  protected final PieceColor pieceColor;

  public PieceBase(final PieceColor pieceColor) {
    this.id = UUID.randomUUID().toString();
    this.pieceColor = pieceColor;
  }

  @Override
  public String getIconPath() {
    return "img";
  }

  @Override
  public PieceColor getPieceColor() {
    return this.pieceColor;
  }

  protected Tile getCurrentPosition(final Map<Tile, IPiece> board) {
    Tile currentPos = null;
    if (board != null && !board.isEmpty()) {
      for (final Map.Entry<Tile, IPiece> e : board.entrySet()) {
        if (this.equals(e.getValue())) {
          currentPos = e.getKey();
          break;
        }
      }
    }
    return currentPos;
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
