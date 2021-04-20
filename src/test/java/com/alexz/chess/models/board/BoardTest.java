package com.alexz.chess.models.board;

import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.models.players.Player;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

  @Test
  void testEquals() {
    final Board a = new Board();
    Assertions.assertEquals(BoardState.IN_MENU, a.getState());
    Assertions.assertTrue(a.isPlayersTurn(PieceColor.WHITE));
    Assertions.assertEquals(PieceColor.NONE, a.getCheck());
    Assertions.assertNotNull(a.getAvailableAttackMoves());
    Assertions.assertNotNull(a.getAvailableMoves());
    Assertions.assertNotNull(a.getBoard());

    final Board b = new Board();
    Assertions.assertEquals(BoardState.IN_MENU, b.getState());
    Assertions.assertTrue(b.isPlayersTurn(PieceColor.WHITE));
    Assertions.assertEquals(PieceColor.NONE, b.getCheck());
    Assertions.assertNotNull(b.getAvailableAttackMoves());
    Assertions.assertNotNull(b.getAvailableMoves());
    Assertions.assertNotNull(b.getBoard());

    Assertions.assertEquals(a, b);
    Assertions.assertEquals(a.hashCode(), b.hashCode());

    b.setPlayerA(new Player(PieceColor.WHITE));
    b.setPlayerB(new Player(PieceColor.BLACK));
    Assertions.assertNotNull(b.getPlayerA());
    Assertions.assertNotNull(b.getPlayerB());
    b.setTurnHolder(PieceColor.BLACK);
    Assertions.assertTrue(b.isPlayersTurn(PieceColor.BLACK));
    Assertions.assertFalse(b.isPlayersTurn(PieceColor.WHITE));
    b.setState(BoardState.IN_GAME);
    Assertions.assertEquals(BoardState.IN_GAME, b.getState());
    b.setCheck(PieceColor.BLACK);
    Assertions.assertEquals(PieceColor.BLACK, b.getCheck());
  }

  @Test
  void testSerialization() {
    final Board a = new Board();
    final byte[] bytes = SerializationUtils.serialize(a);
    Assertions.assertTrue(bytes.length > 0);
  }
}
