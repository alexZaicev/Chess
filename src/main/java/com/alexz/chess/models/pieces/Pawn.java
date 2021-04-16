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
      final char column = currentPos.name().charAt(0);
      final int row;
      if (isBot) {
        row = Integer.parseInt(currentPos.name().substring(1)) + 1;
      } else {
        row = Integer.parseInt(currentPos.name().substring(1)) - 1;
      }

      if (column != 'H') {
        this.addAttackMove((char) (column + 1), row, board, attackMoves);
      }
      if (column != 'A') {
        this.addAttackMove((char) (column - 1), row, board, attackMoves);
      }
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
      final char column = currentPos.name().charAt(0);
      final int row = Integer.parseInt(currentPos.name().substring(1));

      if (isBot) {
        if (this.isFirstMove()) {
          this.addMove(column, row + 2, board, moves);
        }
        this.addMove(column, row + 1, board, moves);
      } else {
        if (this.isFirstMove()) {
          this.addMove(column, row - 2, board, moves);
        }
        this.addMove(column, row - 1, board, moves);
      }
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
