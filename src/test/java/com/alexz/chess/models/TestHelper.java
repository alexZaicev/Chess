package com.alexz.chess.models;

import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.services.PieceUtils;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestHelper {

  public static final MockedStatic<PieceUtils> pieceUtilsMock =
      Mockito.mockStatic(PieceUtils.class);

  public static Map<Tile, IPiece> board() {
    final Map<Tile, IPiece> BOARD = new TreeMap<>();
    for (final Tile tile : Tile.values()) {
      BOARD.put(tile, null);
    }
    return BOARD;
  }

  public static void validate(final List<Tile> expected, final List<Tile> actual) {
    Assertions.assertEquals(expected.size(), actual.size());
    for (final Tile e : expected) {
      Assertions.assertTrue(actual.contains(e));
    }
  }
}
