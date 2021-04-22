package com.alexz.chess.models.pieces;

import com.alexz.chess.TestHelper;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.services.PieceUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KingTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final King king = new King(PieceColor.WHITE);
    Assertions.assertTrue(king.isFirstMove());
    king.setFirstMove(false);
    Assertions.assertFalse(king.isFirstMove());
    king.setFirstMove(true);
    king.postMoveUpdate();
    Assertions.assertFalse(king.isFirstMove());
    Assertions.assertEquals("K", king.toString());
    Assertions.assertEquals("img/king_white.png", king.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, king.getPieceColor());
    king.setFirstMove(true);
    final King king1 = new King(PieceColor.WHITE);
    Assertions.assertNotEquals(king, king1);
    Assertions.assertNotEquals(king.hashCode(), king1.hashCode());

    BOARD.put(Tile.C8, king);
    BOARD.put(Tile.C3, king1);
    Assertions.assertEquals(Tile.C8, king.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, king1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(king.getCurrentPosition(BOARD));
    Assertions.assertNull(king.getCurrentPosition(null));
    Assertions.assertNull(king.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final King king = new King(PieceColor.WHITE);
    BOARD.put(Tile.B8, king);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingMoves(BOARD, Tile.B8, true, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, king, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, false, false));
    }
    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, king);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);
    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingMoves(BOARD, Tile.B1, true, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, king, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingMoves(BOARD, Tile.B1, true, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, king, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, king.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final King king = new King(PieceColor.WHITE);
    BOARD.put(Tile.B8, king);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingAttackMoves(BOARD, Tile.B8, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, king, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, king);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingAttackMoves(BOARD, Tile.B1, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, king, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKingAttackMoves(BOARD, Tile.B1, king.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, king, moves, BOARD))
          .thenReturn(moves);

      moves.clear();
      TestHelper.validatePositions(moves, king.getAttackMoves(BOARD, true, true));
    }
  }

  @Test
  void testGetCastlingMoves() {
    final King king = new King(PieceColor.WHITE);
    BOARD.put(Tile.B8, king);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.A8);
    moves.add(Tile.D8);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getCastlingMoves(BOARD, Tile.B8, king.pieceColor))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, king.getCastlingMoves(BOARD));

      king.setFirstMove(false);
      TestHelper.validatePositions(new ArrayList<>(), king.getCastlingMoves(BOARD));
    }
  }
}
