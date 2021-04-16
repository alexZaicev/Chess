package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieceUtils {

  public static List<Tile> getRookMoves(final Map<Tile, IPiece> board, final Tile pos) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalMoves(board, pos));
    moves.addAll(getHorizontalMoves(board, pos));
    return moves;
  }

  public static List<Tile> getRookAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalAttackMoves(board, pos, pieceColor));
    moves.addAll(getHorizontalAttackMoves(board, pos, pieceColor));
    return moves;
  }

  public static List<Tile> getBishopMoves(final Map<Tile, IPiece> board, final Tile pos) {
    return getDiagonalMoves(board, pos);
  }

  public static List<Tile> getBishopAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    return getDiagonalAttackMoves(board, pos, pieceColor);
  }

  private static List<Tile> getDiagonalMoves(final Map<Tile, IPiece> board, final Tile pos) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (row != 1) {
      if (column != 'A') {
        char tmp = column;
        for (int i = row - 1; i > 0; --i) {
          tmp -= 1;
          if (!addMove(tmp, i, board, moves)) {
            break;
          }
        }
      }
      if (column != 'H') {
        char tmp = column;
        for (int i = row - 1; i > 0; --i) {
          tmp += 1;
          if (!addMove(tmp, i, board, moves)) {
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
          if (!addMove(tmp, i, board, moves)) {
            break;
          }
        }
      }
      if (column != 'H') {
        char tmp = column;
        for (int i = row + 1; i < 9; ++i) {
          tmp += 1;
          if (!addMove(tmp, i, board, moves)) {
            break;
          }
        }
      }
    }
    return moves;
  }

  private static List<Tile> getDiagonalAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (row != 1) {
      if (column != 'A') {
        char tmp = column;
        for (int i = row - 1; i > 0; --i) {
          tmp -= 1;
          if (!addAttackMove(tmp, i, board, moves, pieceColor)) {
            break;
          }
        }
      }
      if (column != 'H') {
        char tmp = column;
        for (int i = row - 1; i > 0; --i) {
          tmp += 1;
          if (!addAttackMove(tmp, i, board, moves, pieceColor)) {
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
          if (!addAttackMove(tmp, i, board, moves, pieceColor)) {
            break;
          }
        }
      }
      if (column != 'H') {
        char tmp = column;
        for (int i = row + 1; i < 9; ++i) {
          tmp += 1;
          if (!addAttackMove(tmp, i, board, moves, pieceColor)) {
            break;
          }
        }
      }
    }
    return moves;
  }

  private static List<Tile> getVerticalMoves(final Map<Tile, IPiece> board, final Tile pos) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (row != 8) {
      for (int i = row + 1; i < 9; ++i) {
        if (!addMove(column, i, board, moves)) {
          break;
        }
      }
    }
    if (row != 1) {
      for (int i = row - 1; i > 0; --i) {
        if (!addMove(column, i, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }

  private static List<Tile> getHorizontalMoves(final Map<Tile, IPiece> board, final Tile pos) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (column != 'H') {
      for (int i = column + 1; i <= 'H'; ++i) {
        if (!addMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    if (column != 'A') {
      for (int i = column - 1; i >= 'A'; --i) {
        if (!addMove((char) i, row, board, moves)) {
          break;
        }
      }
    }
    return moves;
  }

  private static List<Tile> getVerticalAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (row != 8) {
      for (int i = row + 1; i < 9; ++i) {
        if (!addAttackMove(column, i, board, moves, pieceColor)) {
          break;
        }
      }
    }
    if (row != 1) {
      for (int i = row - 1; i > 0; --i) {
        if (!addAttackMove(column, i, board, moves, pieceColor)) {
          break;
        }
      }
    }
    return moves;
  }

  private static List<Tile> getHorizontalAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final List<Tile> moves = new ArrayList<>();
    if (column != 'H') {
      for (int i = column + 1; i <= 'H'; ++i) {
        if (!addAttackMove((char) i, row, board, moves, pieceColor)) {
          break;
        }
      }
    }
    if (column != 'A') {
      for (int i = column - 1; i >= 'A'; --i) {
        if (!addAttackMove((char) i, row, board, moves, pieceColor)) {
          break;
        }
      }
    }
    return moves;
  }

  public static boolean addMove(
      final char column, final int row, final Map<Tile, IPiece> board, final List<Tile> moves) {
    if (isColRowOutOfBounds(column, row)) {
      return false;
    }
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    if (board.get(move) != null) {
      return false;
    }
    moves.add(move);
    return true;
  }

  public static boolean addAttackMove(
      final char column,
      final int row,
      final Map<Tile, IPiece> board,
      final List<Tile> moves,
      final PieceColor pieceColor) {
    if (isColRowOutOfBounds(column, row)) {
      return false;
    }
    final Tile move = Tile.valueOf(String.format("%s%d", column, row));
    final IPiece piece = board.get(move);
    if (piece == null) {
      return true;
    }
    if (piece.getPieceColor() != pieceColor) {
      moves.add(move);
    }
    return false;
  }

  private static boolean isColRowOutOfBounds(final char col, final int row) {
    return col < 'A' || col > 'H' || row < 1 || row > 8;
  }
}
