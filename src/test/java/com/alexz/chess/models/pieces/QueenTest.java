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

public class QueenTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final Queen queen = new Queen(PieceColor.WHITE);
    queen.postMoveUpdate();
    Assertions.assertEquals("Q", queen.toString());
    Assertions.assertEquals("img/queen_white.png", queen.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, queen.getPieceColor());
    final Queen queen1 = new Queen(PieceColor.WHITE);
    Assertions.assertNotEquals(queen, queen1);
    Assertions.assertNotEquals(queen.hashCode(), queen1.hashCode());

    BOARD.put(Tile.C8, queen);
    BOARD.put(Tile.C3, queen1);
    Assertions.assertEquals(Tile.C8, queen.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, queen1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(queen.getCurrentPosition(BOARD));
    Assertions.assertNull(queen.getCurrentPosition(null));
    Assertions.assertNull(queen.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final Queen queen = new Queen(PieceColor.WHITE);
    BOARD.put(Tile.B8, queen);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getQueenMoves(BOARD, Tile.B8)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, queen, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, false, false));
    }
    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, queen);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);
    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getQueenMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, queen, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getQueenMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, queen, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, queen.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final Queen queen = new Queen(PieceColor.WHITE);
    BOARD.put(Tile.B8, queen);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getQueenAttackMoves(BOARD, Tile.B8, queen.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, queen, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, queen);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getQueenAttackMoves(BOARD, Tile.B1, queen.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, queen, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getQueenAttackMoves(BOARD, Tile.B1, queen.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, queen, moves, BOARD))
          .thenReturn(moves);

      moves.clear();
      TestHelper.validatePositions(moves, queen.getAttackMoves(BOARD, true, true));
    }
  }
}
