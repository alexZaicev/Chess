package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rook extends PieceBase {

  private boolean firstMove;

  public Rook(final PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "R";
  }

  public boolean isFirstMove() {
    return firstMove;
  }

  public void setFirstMove(final boolean firstMove) {
    this.firstMove = firstMove;
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board) {
    return this.getAttackMoves(board, false);
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board, final boolean isBot) {
    final List<Tile> attackMoves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      attackMoves.addAll(PieceUtils.getRookAttackMoves(board, currentPos, this.pieceColor));
    }
    return attackMoves;
  }

  @Override
  public List<Tile> getAvailableMoves(final Map<Tile, IPiece> board) {
    return this.getAvailableMoves(board, false);
  }

  @Override
  public List<Tile> getAvailableMoves(final Map<Tile, IPiece> board, final boolean isBot) {
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      moves.addAll(PieceUtils.getRookMoves(board, currentPos));
    }

    return moves;
  }

  @Override
  public void postMoveUpdate() {
    this.setFirstMove(false);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Rook)) return false;

    final Rook rook = (Rook) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(isFirstMove(), rook.isFirstMove())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(isFirstMove())
        .toHashCode();
  }
}
