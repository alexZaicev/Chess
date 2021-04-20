package com.alexz.chess.services;

import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.models.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PieceUtils {

  public static void filterMovesToAvoidCheck(final Tile pos, final IPiece piece, final List<Tile> moves, final Map<Tile, IPiece> board) {
    final List<Tile> filteredMoves = new ArrayList<>();
    final PieceColor checked = BoardService.getInstance().getCheckedPieceColor(board, false);
//    if (checked == PieceColor.NONE) {
//      return;
//    }

    for (final Tile move : moves) {
      final Map<Tile, IPiece> newBoard = new TreeMap<>(board);
      newBoard.put(pos, null);
      newBoard.put(move, piece);
      final PieceColor pieceColor = BoardService.getInstance().getCheckedPieceColor(newBoard, false);
      if (pieceColor == PieceColor.NONE || (checked == PieceColor.NONE && pieceColor != piece.getPieceColor())) {
        filteredMoves.add(move);
      }
    }
    moves.clear();
    moves.addAll(filteredMoves);
  }

  /**
   * @param board
   * @param pos
   * @return
   */
  public static List<Tile> getRookMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalMoves(board, pos));
    moves.addAll(getHorizontalMoves(board, pos));
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param pieceColor
   * @return
   */
  public static List<Tile> getRookAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalAttackMoves(board, pos, pieceColor));
    moves.addAll(getHorizontalAttackMoves(board, pos, pieceColor));
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @return
   */
  public static List<Tile> getBishopMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    return getDiagonalMoves(board, pos);
  }

  /**
   * @param board
   * @param pos
   * @param pieceColor
   * @return
   */
  public static List<Tile> getBishopAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    return getDiagonalAttackMoves(board, pos, pieceColor);
  }

  public static List<Tile> getQueenAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalAttackMoves(board, pos, pieceColor));
    moves.addAll(getHorizontalAttackMoves(board, pos, pieceColor));
    moves.addAll(getDiagonalAttackMoves(board, pos, pieceColor));
    return moves;
  }

  public static List<Tile> getQueenMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    moves.addAll(getVerticalMoves(board, pos));
    moves.addAll(getHorizontalMoves(board, pos));
    moves.addAll(getDiagonalMoves(board, pos));
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @return
   */
  public static List<Tile> getKnightMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();

    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    // vertical moves
    addMove((char) (column - 1), row + 2, board, moves);
    addMove((char) (column + 1), row + 2, board, moves);
    addMove((char) (column - 1), row - 2, board, moves);
    addMove((char) (column + 1), row - 2, board, moves);

    // horizontal moves
    addMove((char) (column - 2), row + 1, board, moves);
    addMove((char) (column + 2), row + 1, board, moves);
    addMove((char) (column - 2), row - 1, board, moves);
    addMove((char) (column + 2), row - 1, board, moves);

    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param pieceColor
   * @return
   */
  public static List<Tile> getKnightAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();

    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    // vertical moves
    addAttackMove((char) (column - 1), row + 2, board, moves, pieceColor);
    addAttackMove((char) (column + 1), row + 2, board, moves, pieceColor);
    addAttackMove((char) (column - 1), row - 2, board, moves, pieceColor);
    addAttackMove((char) (column + 1), row - 2, board, moves, pieceColor);

    // horizontal moves
    addAttackMove((char) (column - 2), row + 1, board, moves, pieceColor);
    addAttackMove((char) (column + 2), row + 1, board, moves, pieceColor);
    addAttackMove((char) (column - 2), row - 1, board, moves, pieceColor);
    addAttackMove((char) (column + 2), row - 1, board, moves, pieceColor);

    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param isBot
   * @param pieceColor
   * @return
   */
  public static List<Tile> getPawnAttackMoves(
      final Map<Tile, IPiece> board,
      final Tile pos,
      final boolean isBot,
      final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();

    final char column = pos.name().charAt(0);
    final int row;
    if (isBot) {
      row = Integer.parseInt(pos.name().substring(1)) + 1;
    } else {
      row = Integer.parseInt(pos.name().substring(1)) - 1;
    }

    if (column != 'H') {
      addAttackMove((char) (column + 1), row, board, moves, pieceColor);
    }
    if (column != 'A') {
      addAttackMove((char) (column - 1), row, board, moves, pieceColor);
    }
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param isBot
   * @param isFirstMove
   * @return
   */
  public static List<Tile> getPawnMoves(
      final Map<Tile, IPiece> board,
      final Tile pos,
      final boolean isBot,
      final boolean isFirstMove) {
    final List<Tile> moves = new ArrayList<>();

    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    if (isBot) {
      if (isFirstMove) {
        final Tile tile = Tile.valueOf(String.format("%s%d", column, row + 1));
        if (board.get(tile) == null) {
          PieceUtils.addMove(column, row + 2, board, moves);
        }
      }
      PieceUtils.addMove(column, row + 1, board, moves);
    } else {
      if (isFirstMove) {
        final Tile tile = Tile.valueOf(String.format("%s%d", column, row - 1));
        if (board.get(tile) == null) {
          PieceUtils.addMove(column, row - 2, board, moves);
        }
      }
      PieceUtils.addMove(column, row - 1, board, moves);
    }
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param isFirstMove
   * @return
   */
  public static List<Tile> getKingMoves(
      final Map<Tile, IPiece> board,
      final Tile pos,
      final boolean isFirstMove,
      final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    if (isFirstMove) {
      moves.addAll(getCastlingMoves(board, pos, pieceColor));
    }

    addMove(column, row + 1, board, moves);
    addMove(column, row - 1, board, moves);
    addMove((char) (column + 1), row, board, moves);
    addMove((char) (column - 1), row, board, moves);
    addMove((char) (column + 1), row + 1, board, moves);
    addMove((char) (column + 1), row - 1, board, moves);
    addMove((char) (column - 1), row + 1, board, moves);
    addMove((char) (column - 1), row - 1, board, moves);
    return moves;
  }

  /**
   * @param board
   * @param pos
   * @param pieceColor
   * @return
   */
  public static List<Tile> getKingAttackMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();
    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    addAttackMove(column, row + 1, board, moves, pieceColor);
    addAttackMove(column, row - 1, board, moves, pieceColor);
    addAttackMove((char) (column + 1), row, board, moves, pieceColor);
    addAttackMove((char) (column - 1), row, board, moves, pieceColor);
    addAttackMove((char) (column + 1), row + 1, board, moves, pieceColor);
    addAttackMove((char) (column + 1), row - 1, board, moves, pieceColor);
    addAttackMove((char) (column - 1), row + 1, board, moves, pieceColor);
    addAttackMove((char) (column - 1), row - 1, board, moves, pieceColor);
    return moves;
  }

  public static List<Tile> getCastlingMoves(
      final Map<Tile, IPiece> board, final Tile pos, final PieceColor pieceColor) {
    final List<Tile> moves = new ArrayList<>();

    final char column = pos.name().charAt(0);
    final int row = Integer.parseInt(pos.name().substring(1));

    final Tile leftRookPos = Tile.valueOf(String.format("A%d", row));
    final Tile rightRookPos = Tile.valueOf(String.format("H%d", row));

    final IPiece leftRook = board.get(leftRookPos);
    final IPiece rightRook = board.get(rightRookPos);

    if (leftRook instanceof Rook
        && ((Rook) leftRook).isFirstMove()
        && leftRook.getPieceColor() == pieceColor) {
      boolean empty = true;
      char tmpCol = column;
      while (tmpCol > 'B') {
        tmpCol -= 1;
        if (board.get(Tile.valueOf(String.format("%s%d", tmpCol, row))) != null) {
          empty = false;
          break;
        }
      }
      if (empty) {
        addMove((char) (column - 2), row, board, moves);
      }
    }

    if (rightRook instanceof Rook
        && ((Rook) rightRook).isFirstMove()
        && rightRook.getPieceColor() == pieceColor) {
      boolean empty = true;
      char tmpCol = column;
      while (tmpCol < 'G') {
        tmpCol += 1;
        if (board.get(Tile.valueOf(String.format("%s%d", tmpCol, row))) != null) {
          empty = false;
          break;
        }
      }
      if (empty) {
        addMove((char) (column + 2), row, board, moves);
      }
    }
    return moves;
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

  private static boolean addMove(
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

  private static boolean addAttackMove(
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
