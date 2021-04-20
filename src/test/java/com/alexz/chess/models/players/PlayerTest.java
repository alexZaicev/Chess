package com.alexz.chess.models.players;

import com.alexz.chess.models.pieces.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

  @Test
  void testPlayer() {
    final Player p = new Player(PieceColor.WHITE);
    Assertions.assertEquals(PieceColor.WHITE, p.getPieceColor());
  }
}
