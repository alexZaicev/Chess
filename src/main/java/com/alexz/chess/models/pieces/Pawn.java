package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends PieceBase {

  private boolean firstMove;

  public Pawn(final PieceColor color) {
    super(color);
    this.firstMove = true;
  }

  @Override
  public String toString() {
    return "P";
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
      attackMoves.addAll(PieceUtils.getPawnAttackMoves(board, currentPos, isBot, this.pieceColor));
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
      moves.addAll(PieceUtils.getPawnMoves(board, currentPos, isBot, this.firstMove));
    }
    return moves;
  }

  @Override
  public void postMoveUpdate() {
    this.setFirstMove(false);
  }

  public boolean isFirstMove() {
    return firstMove;
  }

  public void setFirstMove(final boolean firstMove) {
    this.firstMove = firstMove;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Pawn)) return false;

    final Pawn pawn = (Pawn) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(isFirstMove(), pawn.isFirstMove())
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
