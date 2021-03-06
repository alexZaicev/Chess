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

public class RookTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final Rook rook = new Rook(PieceColor.WHITE);
    Assertions.assertTrue(rook.isFirstMove());
    rook.setFirstMove(false);
    Assertions.assertFalse(rook.isFirstMove());
    rook.setFirstMove(true);
    rook.postMoveUpdate();
    Assertions.assertFalse(rook.isFirstMove());
    Assertions.assertEquals("R", rook.toString());
    Assertions.assertEquals("img/rook_white.png", rook.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, rook.getPieceColor());
    rook.setFirstMove(true);
    final Rook rook1 = new Rook(PieceColor.WHITE);
    Assertions.assertNotEquals(rook, rook1);
    Assertions.assertNotEquals(rook.hashCode(), rook1.hashCode());

    BOARD.put(Tile.C8, rook);
    BOARD.put(Tile.C3, rook1);
    Assertions.assertEquals(Tile.C8, rook.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, rook1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(rook.getCurrentPosition(BOARD));
    Assertions.assertNull(rook.getCurrentPosition(null));
    Assertions.assertNull(rook.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final Rook rook = new Rook(PieceColor.WHITE);
    BOARD.put(Tile.B8, rook);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getRookMoves(BOARD, Tile.B8)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, rook, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, false, false));
    }
    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, rook);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);
    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getRookMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, rook, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getRookMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, rook, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, rook.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final Rook rook = new Rook(PieceColor.WHITE);
    BOARD.put(Tile.B8, rook);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getRookAttackMoves(BOARD, Tile.B8, rook.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, rook, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, rook);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getRookAttackMoves(BOARD, Tile.B1, rook.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, rook, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getRookAttackMoves(BOARD, Tile.B1, rook.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, rook, moves, BOARD))
          .thenReturn(moves);

      moves.clear();
      TestHelper.validatePositions(moves, rook.getAttackMoves(BOARD, true, true));
    }
  }
}
