package com.alexz.chess.models.board;

import com.alexz.chess.models.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;

public enum Tile {
  A1,
  A2,
  A3,
  A4,
  A5,
  A6,
  A7,
  A8,
  B1,
  B2,
  B3,
  B4,
  B5,
  B6,
  B7,
  B8,
  C1,
  C2,
  C3,
  C4,
  C5,
  C6,
  C7,
  C8,
  D1,
  D2,
  D3,
  D4,
  D5,
  D6,
  D7,
  D8,
  E1,
  E2,
  E3,
  E4,
  E5,
  E6,
  E7,
  E8,
  F1,
  F2,
  F3,
  F4,
  F5,
  F6,
  F7,
  F8,
  G1,
  G2,
  G3,
  G4,
  G5,
  G6,
  G7,
  G8,
  H1,
  H2,
  H3,
  H4,
  H5,
  H6,
  H7,
  H8,
  ;

  public static List<Tile> getInitialPawnPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "2" : "7";
    for (final Tile t : Tile.values()) {
      if (t.name().endsWith(s)) {
        positions.add(t);
      }
    }
    return positions;
  }

  public static List<Tile> getInitialRookPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "1" : "8";
    positions.add(Tile.valueOf(String.format("%s%s", "A", s)));
    positions.add(Tile.valueOf(String.format("%s%s", "H", s)));
    return positions;
  }

  public static List<Tile> getInitialKnightPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "1" : "8";
    positions.add(Tile.valueOf(String.format("%s%s", "B", s)));
    positions.add(Tile.valueOf(String.format("%s%s", "G", s)));
    return positions;
  }

  public static List<Tile> getInitialBishopPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "1" : "8";
    positions.add(Tile.valueOf(String.format("%s%s", "C", s)));
    positions.add(Tile.valueOf(String.format("%s%s", "F", s)));
    return positions;
  }

  public static List<Tile> getInitialQueenPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "1" : "8";
    positions.add(Tile.valueOf(String.format("%s%s", "D", s)));
    return positions;
  }

  public static List<Tile> getInitialKingPositions(final PieceColor pieceColor) {
    final List<Tile> positions = new ArrayList<>();
    final String s = pieceColor == PieceColor.WHITE ? "1" : "8";
    positions.add(Tile.valueOf(String.format("%s%s", "E", s)));
    return positions;
  }
}
