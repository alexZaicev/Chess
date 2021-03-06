package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import com.alexz.chess.services.PieceUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Queen extends PieceBase {
  public Queen(final PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "Q";
  }

  @Override
  public String getIconPath() {
    final String root = super.getIconPath();
    return String.format("%s/queen_%s.png", root, this.pieceColor.name().toLowerCase());
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board) {
    return this.getAttackMoves(board, false);
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board, final boolean isBot) {
    return this.getAttackMoves(board, isBot, true);
  }

  @Override
  public List<Tile> getAttackMoves(
      final Map<Tile, IPiece> board, final boolean isBot, final boolean filter) {
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      moves.addAll(PieceUtils.getQueenAttackMoves(board, currentPos, this.pieceColor));
      if (filter) {
        return PieceUtils.filterMovesToAvoidCheck(currentPos, this, moves, board);
      }
    }
    return moves;
  }

  @Override
  public List<Tile> getAvailableMoves(final Map<Tile, IPiece> board) {
    return this.getAvailableMoves(board, false);
  }

  @Override
  public List<Tile> getAvailableMoves(final Map<Tile, IPiece> board, final boolean isBot) {
    return this.getAvailableMoves(board, isBot, true);
  }

  @Override
  public List<Tile> getAvailableMoves(
      final Map<Tile, IPiece> board, final boolean isBot, final boolean filter) {
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      moves.addAll(PieceUtils.getQueenMoves(board, currentPos));
      if (filter) {
        return PieceUtils.filterMovesToAvoidCheck(currentPos, this, moves, board);
      }
    }
    return moves;
  }

  @Override
  public void postMoveUpdate() {}

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Queen)) return false;

    return new EqualsBuilder().appendSuper(super.equals(o)).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).toHashCode();
  }
}
