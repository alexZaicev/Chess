package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends PieceBase {

  private boolean firstMove;

  public Pawn(PieceColor color) {
    super(color);
    this.firstMove = true;
  }

  @Override
  public String toString() {
    return "P";
  }

  @Override
  public List<Tile> getAttackMoves(Map<Tile, IPiece> board) {
    return this.getAttackMoves(board, false);
  }

  @Override
  public List<Tile> getAttackMoves(Map<Tile, IPiece> board, final boolean isBot) {
    final List<Tile> attackMoves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      String column = currentPos.name().substring(0, 1);
      final int row = Integer.parseInt(currentPos.name().substring(1));
      final int i = column.charAt(0);
      if (isBot) {
        // attack moves
        if (i == 'A') {
          column = Character.toString((char) (i + 1));
          this.addAttackMove(column, row + 1, board, attackMoves);
        } else if (i == 'H') {
          column = Character.toString((char) (i - 1));
          this.addAttackMove(column, row + 1, board, attackMoves);
        } else {
          column = Character.toString((char) (i + 1));
          this.addAttackMove(column, row + 1, board, attackMoves);
          column = Character.toString((char) (i - 1));
          this.addAttackMove(column, row + 1, board, attackMoves);
        }
      } else {
        if (i == 'A') {
          column = Character.toString((char) (i + 1));
          this.addAttackMove(column, row - 1, board, attackMoves);
        } else if (i == 'H') {
          column = Character.toString((char) (i - 1));
          this.addAttackMove(column, row - 1, board, attackMoves);
        } else {
          column = Character.toString((char) (i + 1));
          this.addAttackMove(column, row - 1, board, attackMoves);
          column = Character.toString((char) (i - 1));
          this.addAttackMove(column, row - 1, board, attackMoves);
        }
      }
    }
    return attackMoves;
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board) {
    return this.getAvailableMoves(board, false);
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board, boolean isBot) {
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      String column = currentPos.name().substring(0, 1);
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

  public void setFirstMove(boolean firstMove) {
    this.firstMove = firstMove;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (!(o instanceof Pawn)) return false;

    Pawn pawn = (Pawn) o;

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
