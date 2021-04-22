package com.alexz.chess;

import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.*;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestHelper {

  public static Map<Tile, IPiece> emptyBoard() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    return BOARD;
  }

  public static Map<Tile, IPiece> noCheckBoard() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.A2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.B1, new Rook(PieceColor.BLACK, false));
    BOARD.put(Tile.B2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.B7, new Knight(PieceColor.WHITE));
    BOARD.put(Tile.D1, new Queen(PieceColor.BLACK));
    BOARD.put(Tile.D2, new Bishop(PieceColor.BLACK));
    BOARD.put(Tile.D5, new Knight(PieceColor.BLACK));
    BOARD.put(Tile.E1, new King(PieceColor.BLACK));
    BOARD.put(Tile.E6, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.F2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.F4, new Queen(PieceColor.WHITE));
    BOARD.put(Tile.F6, new Bishop(PieceColor.WHITE));
    BOARD.put(Tile.F7, new Pawn(PieceColor.WHITE));
    BOARD.put(Tile.F8, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.G2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.G6, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.G8, new King(PieceColor.WHITE, false));
    BOARD.put(Tile.H1, new Rook(PieceColor.BLACK));
    BOARD.put(Tile.H7, new Pawn(PieceColor.WHITE));
    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_1() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.C1, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.H1, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.A2, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.E8, new King(PieceColor.WHITE, false));
    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_2() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.D1, new Rook(PieceColor.BLACK, false));
    BOARD.put(Tile.D2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.E1, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.E2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.E6, new King(PieceColor.WHITE, false));
    BOARD.put(Tile.F2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.H1, new Rook(PieceColor.WHITE, false));

    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_3() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.A2, new Queen(PieceColor.WHITE));
    BOARD.put(Tile.B7, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.C2, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.E1, new King(PieceColor.WHITE));
    BOARD.put(Tile.E4, new Knight(PieceColor.BLACK));
    BOARD.put(Tile.G2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.G4, new Bishop(PieceColor.BLACK));

    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_4() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.A7, new Queen(PieceColor.BLACK));
    BOARD.put(Tile.A8, new King(PieceColor.WHITE, false));
    BOARD.put(Tile.B1, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.F2, new Bishop(PieceColor.BLACK));

    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_5() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.A3, new Queen(PieceColor.WHITE));
    BOARD.put(Tile.B7, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.C2, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.E1, new King(PieceColor.WHITE));
    BOARD.put(Tile.E4, new Knight(PieceColor.BLACK));
    BOARD.put(Tile.F2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.G2, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.G4, new Bishop(PieceColor.BLACK));

    return BOARD;
  }

  public static Map<Tile, IPiece> boardOption_6() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    BOARD.put(Tile.A1, new Knight(PieceColor.WHITE));
    BOARD.put(Tile.C4, new King(PieceColor.WHITE, false));
    BOARD.put(Tile.C7, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.C8, new Knight(PieceColor.BLACK));
    BOARD.put(Tile.D3, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.D7, new Pawn(PieceColor.BLACK));
    BOARD.put(Tile.D8, new King(PieceColor.BLACK, false));
    BOARD.put(Tile.F5, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.G1, new Rook(PieceColor.WHITE, false));
    BOARD.put(Tile.H3, new Pawn(PieceColor.WHITE, false));
    BOARD.put(Tile.H6, new Bishop(PieceColor.BLACK));

    return BOARD;
  }

  public static void validatePositions(final List<Tile> expected, final List<Tile> actual) {
    Assertions.assertEquals(
        expected.size(),
        actual.size(),
        "Mismatch in results: \nActual ["
            + listToString(actual)
            + "]\nExpected ["
            + listToString(expected)
            + "]");
    for (final Tile e : expected) {
      Assertions.assertTrue(actual.contains(e), "Tile [" + e + "] not present in actual moves");
    }
  }

  public static void validatePieces(
      final Map<Tile, IPiece> board,
      final PieceColor pieceColor,
      final Class<?> clazz,
      final Tile... positions) {
    for (final Tile position : positions) {
      Assertions.assertEquals(clazz, board.get(position).getClass());
      Assertions.assertEquals(pieceColor, board.get(position).getPieceColor());
    }
  }

  public static List<Tile> toList(final Tile... tiles) {
    return Arrays.asList(tiles);
  }

  private static String listToString(final List<?> list) {
    final StringBuilder sb = new StringBuilder();
    for (final Object item : list) {
      sb.append(item.toString());
      if (list.indexOf(item) != list.size() - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }
}
