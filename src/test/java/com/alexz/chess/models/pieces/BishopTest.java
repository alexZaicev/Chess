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

public class BishopTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final Bishop bishop = new Bishop(PieceColor.WHITE);
    bishop.postMoveUpdate();
    Assertions.assertEquals("B", bishop.toString());
    Assertions.assertEquals("img/bishop_white.png", bishop.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, bishop.getPieceColor());
    final Bishop bishop1 = new Bishop(PieceColor.WHITE);
    Assertions.assertNotEquals(bishop, bishop1);
    Assertions.assertNotEquals(bishop.hashCode(), bishop1.hashCode());

    BOARD.put(Tile.C8, bishop);
    BOARD.put(Tile.C3, bishop1);
    Assertions.assertEquals(Tile.C8, bishop.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, bishop1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(bishop.getCurrentPosition(BOARD));
    Assertions.assertNull(bishop.getCurrentPosition(null));
    Assertions.assertNull(bishop.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final Bishop bishop = new Bishop(PieceColor.WHITE);
    BOARD.put(Tile.B8, bishop);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getBishopMoves(BOARD, Tile.B8)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, bishop, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, false, false));
    }
    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, bishop);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);
    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getBishopMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, bishop, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getBishopMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, bishop, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, bishop.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final Bishop bishop = new Bishop(PieceColor.WHITE);
    BOARD.put(Tile.B8, bishop);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getBishopAttackMoves(BOARD, Tile.B8, bishop.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, bishop, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, bishop);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getBishopAttackMoves(BOARD, Tile.B1, bishop.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, bishop, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getBishopAttackMoves(BOARD, Tile.B1, bishop.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, bishop, moves, BOARD))
          .thenReturn(moves);

      moves.clear();
      TestHelper.validatePositions(moves, bishop.getAttackMoves(BOARD, true, true));
    }
  }
}
