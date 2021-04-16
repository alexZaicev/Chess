package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bishop extends PieceBase {
  public Bishop(final PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "B";
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board) {
    return this.getAttackMoves(board, false);
  }

  @Override
  public List<Tile> getAttackMoves(final Map<Tile, IPiece> board, final boolean isBot) {
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      final char column = currentPos.name().charAt(0);
      final int row = Integer.parseInt(currentPos.name().substring(1));

      if (row != 1) {
        if (column != 'A') {
          char tmp = column;
          for (int i = row - 1; i > 0; --i) {
            tmp -= 1;
            if (!this.addAttackMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
        if (column != 'H') {
          char tmp = column;
          for (int i = row - 1; i > 0; --i) {
            tmp += 1;
            if (!this.addAttackMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
      }

      if (row != 8) {
        if (column != 'A') {
          char tmp = column;
          for (int i = row + 1; i < 9; ++i) {
            tmp -= 1;
            if (!this.addAttackMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
        if (column != 'H') {
          char tmp = column;
          for (int i = row + 1; i < 9; ++i) {
            tmp += 1;
            if (!this.addAttackMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
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
    final List<Tile> moves = new ArrayList<>();
    final Tile currentPos = this.getCurrentPosition(board);

    if (currentPos != null) {
      final char column = currentPos.name().charAt(0);
      final int row = Integer.parseInt(currentPos.name().substring(1));

      if (row != 1) {
        if (column != 'A') {
          char tmp = column;
          for (int i = row - 1; i > 0; --i) {
            tmp -= 1;
            if (!this.addMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
        if (column != 'H') {
          char tmp = column;
          for (int i = row - 1; i > 0; --i) {
            tmp += 1;
            if (!this.addMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
      }

      if (row != 8) {
        if (column != 'A') {
          char tmp = column;
          for (int i = row + 1; i < 9; ++i) {
            tmp -= 1;
            if (!this.addMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
        if (column != 'H') {
          char tmp = column;
          for (int i = row + 1; i < 9; ++i) {
            tmp += 1;
            if (!this.addMove(tmp, i, board, moves)) {
              break;
            }
          }
        }
      }
    }
    return moves;
  }

  @Override
  public void postMoveUpdate() {}
}
