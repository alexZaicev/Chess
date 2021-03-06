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

public class PawnTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final Pawn pawn = new Pawn(PieceColor.WHITE);
    Assertions.assertTrue(pawn.isFirstMove());
    pawn.setFirstMove(false);
    Assertions.assertFalse(pawn.isFirstMove());
    pawn.setFirstMove(true);
    pawn.postMoveUpdate();
    Assertions.assertFalse(pawn.isFirstMove());
    Assertions.assertEquals("P", pawn.toString());
    Assertions.assertEquals("img/pawn_white.png", pawn.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, pawn.getPieceColor());
    pawn.setFirstMove(true);
    final Pawn pawn1 = new Pawn(PieceColor.WHITE);
    Assertions.assertNotEquals(pawn, pawn1);
    Assertions.assertNotEquals(pawn.hashCode(), pawn1.hashCode());

    BOARD.put(Tile.C8, pawn);
    BOARD.put(Tile.C3, pawn1);
    Assertions.assertEquals(Tile.C8, pawn.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, pawn1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(pawn.getCurrentPosition(BOARD));
    Assertions.assertNull(pawn.getCurrentPosition(null));
    Assertions.assertNull(pawn.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final Pawn pawn = new Pawn(PieceColor.WHITE);
    BOARD.put(Tile.B8, pawn);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnMoves(BOARD, Tile.B8, false, true))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, pawn, moves, BOARD))
          .thenReturn(moves);
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, pawn);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnMoves(BOARD, Tile.B1, true, true))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, pawn, moves, BOARD))
          .thenReturn(moves);
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnMoves(BOARD, Tile.B1, true, true))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, pawn, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, pawn.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final Pawn pawn = new Pawn(PieceColor.WHITE);
    BOARD.put(Tile.B8, pawn);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnAttackMoves(BOARD, Tile.B8, false, pawn.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, pawn, moves, BOARD))
          .thenReturn(moves);
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, pawn);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnAttackMoves(BOARD, Tile.B1, true, pawn.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, pawn, moves, BOARD))
          .thenReturn(moves);
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getPawnAttackMoves(BOARD, Tile.B1, true, pawn.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, pawn, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, pawn.getAttackMoves(BOARD, true, true));
    }
  }
}
