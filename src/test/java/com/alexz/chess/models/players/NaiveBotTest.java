package com.alexz.chess.models.players;

import com.alexz.chess.TestHelper;
import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.exceptions.DrawException;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

public class NaiveBotTest {

  static final IPiece p1Mock = Mockito.mock(IPiece.class);
  static final IPiece p2Mock = Mockito.mock(IPiece.class);
  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    Mockito.reset(p1Mock, p2Mock);
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testGetNextMove() {
    final BotBase bot = new NaiveBot(PieceColor.WHITE);
    BOARD.put(Tile.A5, p1Mock);
    BOARD.put(Tile.C6, p2Mock);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.A6);

    when(p1Mock.getPieceColor()).thenReturn(PieceColor.WHITE);
    when(p1Mock.getAvailableMoves(BOARD, true)).thenReturn(moves);
    when(p1Mock.getAttackMoves(BOARD, true)).thenReturn(new ArrayList<>());

    when(p2Mock.getPieceColor()).thenReturn(PieceColor.BLACK);

    boolean thrown = false;
    Move move = null;
    try {
      move = bot.getNextMove(BOARD);
    } catch (final DrawException ex) {
      thrown = true;
    }
    Assertions.assertFalse(thrown);
    Assertions.assertNotNull(move);
    Assertions.assertEquals(PieceColor.WHITE, move.getPieceColor());
    Assertions.assertEquals(Tile.A5, move.getOldPosition());
    Assertions.assertEquals(Tile.A6, move.getNewPosition());
    Assertions.assertEquals(p1Mock, move.getPiece());

    moves.clear();
    moves.add(Tile.B6);
    thrown = false;
    try {
      move = bot.getNextMove(BOARD);
    } catch (final DrawException ex) {
      thrown = true;
    }
    Assertions.assertFalse(thrown);
    Assertions.assertNotNull(move);
    Assertions.assertEquals(PieceColor.WHITE, move.getPieceColor());
    Assertions.assertEquals(Tile.A5, move.getOldPosition());
    Assertions.assertEquals(Tile.B6, move.getNewPosition());
    Assertions.assertEquals(p1Mock, move.getPiece());
  }

  @Test
  void testGetNextMove_DrawExceptions() {
    final BotBase bot = new NaiveBot(PieceColor.WHITE);
    BOARD.put(Tile.A5, p1Mock);
    BOARD.put(Tile.C6, p2Mock);

    when(p1Mock.getPieceColor()).thenReturn(PieceColor.WHITE);
    when(p1Mock.getAvailableMoves(BOARD, true)).thenReturn(new ArrayList<>());
    when(p1Mock.getAttackMoves(BOARD, true)).thenReturn(new ArrayList<>());
    when(p2Mock.getPieceColor()).thenReturn(PieceColor.BLACK);

    Assertions.assertThrows(DrawException.class, () -> bot.getNextMove(BOARD));
  }

  @Test
  void testGetSetDifficulty() {
    final BotBase bot = new NaiveBot(PieceColor.WHITE);
    bot.setDifficulty(Difficulty.EASY);
    Assertions.assertEquals(Difficulty.EASY, bot.getDifficulty());
  }
}
