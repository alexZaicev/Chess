package com.alexz.chess.models.board;

import com.alexz.chess.models.pieces.PieceColor;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoveTest {

  @Test
  void testEquals() {
    final Move a = new Move(PieceColor.WHITE, Tile.A1, Tile.A2, null);
    final Move b = new Move(PieceColor.WHITE, Tile.A1, Tile.A2, null);
    Assertions.assertEquals(a, b);
    Assertions.assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void testSerialization() {
    final Move a = new Move(PieceColor.WHITE, Tile.A1, Tile.A2, null);
    final byte[] bytes = SerializationUtils.serialize(a);
    Assertions.assertTrue(bytes.length > 0);
  }
}
