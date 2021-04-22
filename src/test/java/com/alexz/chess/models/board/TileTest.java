package com.alexz.chess.models.board;

import com.alexz.chess.TestHelper;
import com.alexz.chess.models.pieces.PieceColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TileTest {

  @Test
  void testGetInitialPawnPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.A2);
    expected.add(Tile.B2);
    expected.add(Tile.C2);
    expected.add(Tile.D2);
    expected.add(Tile.E2);
    expected.add(Tile.F2);
    expected.add(Tile.G2);
    expected.add(Tile.H2);
    TestHelper.validatePositions(expected, Tile.getInitialPawnPositions(true));

    expected.clear();
    expected.add(Tile.A7);
    expected.add(Tile.B7);
    expected.add(Tile.C7);
    expected.add(Tile.D7);
    expected.add(Tile.E7);
    expected.add(Tile.F7);
    expected.add(Tile.G7);
    expected.add(Tile.H7);
    TestHelper.validatePositions(expected, Tile.getInitialPawnPositions(false));
  }

  @Test
  void testGetInitialRookPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.A1);
    expected.add(Tile.H1);
    TestHelper.validatePositions(expected, Tile.getInitialRookPositions(true));

    expected.clear();
    expected.add(Tile.A8);
    expected.add(Tile.H8);
    TestHelper.validatePositions(expected, Tile.getInitialRookPositions(false));
  }

  @Test
  void testGetInitialKnightPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.B1);
    expected.add(Tile.G1);
    TestHelper.validatePositions(expected, Tile.getInitialKnightPositions(true));

    expected.clear();
    expected.add(Tile.B8);
    expected.add(Tile.G8);
    TestHelper.validatePositions(expected, Tile.getInitialKnightPositions(false));
  }

  @Test
  void testGetInitialBishopPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.C1);
    expected.add(Tile.F1);
    TestHelper.validatePositions(expected, Tile.getInitialBishopPositions(true));

    expected.clear();
    expected.add(Tile.C8);
    expected.add(Tile.F8);
    TestHelper.validatePositions(expected, Tile.getInitialBishopPositions(false));
  }

  @Test
  void testGetInitialQueenPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.E1);
    TestHelper.validatePositions(expected, Tile.getInitialQueenPositions(true, PieceColor.WHITE));

    expected.clear();
    expected.add(Tile.D1);
    TestHelper.validatePositions(expected, Tile.getInitialQueenPositions(true, PieceColor.BLACK));

    expected.clear();
    expected.add(Tile.D8);
    TestHelper.validatePositions(expected, Tile.getInitialQueenPositions(false, PieceColor.WHITE));

    expected.clear();
    expected.add(Tile.E8);
    TestHelper.validatePositions(expected, Tile.getInitialQueenPositions(false, PieceColor.BLACK));
  }

  @Test
  void testGetInitialKingPositions() {
    final List<Tile> expected = new ArrayList<>();
    expected.add(Tile.D1);
    TestHelper.validatePositions(expected, Tile.getInitialKingPositions(true, PieceColor.WHITE));

    expected.clear();
    expected.add(Tile.E1);
    TestHelper.validatePositions(expected, Tile.getInitialKingPositions(true, PieceColor.BLACK));

    expected.clear();
    expected.add(Tile.E8);
    TestHelper.validatePositions(expected, Tile.getInitialKingPositions(false, PieceColor.WHITE));

    expected.clear();
    expected.add(Tile.D8);
    TestHelper.validatePositions(expected, Tile.getInitialKingPositions(false, PieceColor.BLACK));
  }
}
