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
      final char column = currentPos.name().charAt(0);
      final int row = Integer.parseInt(currentPos.name().substring(1));

      attackMoves.addAll(this.getVerticalAttackMoves(board, column, row));
      attackMoves.addAll(this.getHorizontalAttackMoves(board, column, row));
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

      moves.addAll(this.getVerticalMoves(board, column, row));
      moves.addAll(this.getHorizontalMoves(board, column, row));
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

  private List<Tile> getVerticalAttackMoves(
      final Map<Tile, IPiece> board, final char column, final int row) {
    final List<Tile> moves = new ArrayList<>();
    if (row != 8) {
      for (int i = row + 1; i < 9; ++i) {
        if (!this.addAttackMove(column, i, board, moves)) {
          break;
        }
      }
    }
    if (row != 1) {
      for (int i = row - 1; i > 0; --i) {
        if (!this.addAttackMove(column, i, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }

  private List<Tile> getHorizontalAttackMoves(
      final Map<Tile, IPiece> board, final char column, final int row) {
    final List<Tile> moves = new ArrayList<>();
    if (column != 'H') {
      for (int i = column + 1; i <= 'H'; ++i) {
        if (!this.addAttackMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    if (column != 'A') {
      for (int i = column - 1; i >= 'A'; --i) {
        if (!this.addAttackMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }

  private List<Tile> getVerticalMoves(
      final Map<Tile, IPiece> board, final char column, final int row) {
    final List<Tile> moves = new ArrayList<>();
    if (row != 8) {
      for (int i = row + 1; i < 9; ++i) {
        if (!this.addMove(column, i, board, moves)) {
          break;
        }
      }
    }
    if (row != 1) {
      for (int i = row - 1; i > 0; --i) {
        if (!this.addMove(column, i, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }

  private List<Tile> getHorizontalMoves(
      final Map<Tile, IPiece> board, final char column, final int row) {
    final List<Tile> moves = new ArrayList<>();
    if (column != 'H') {
      for (int i = column + 1; i <= 'H'; ++i) {
        if (!this.addMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    if (column != 'A') {
      for (int i = column - 1; i >= 'A'; --i) {
        if (!this.addMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }
}
