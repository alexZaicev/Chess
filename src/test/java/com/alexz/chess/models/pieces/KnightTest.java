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

public class KnightTest {

  static final Map<Tile, IPiece> BOARD = TestHelper.emptyBoard();

  @BeforeEach
  void setup() {
    BOARD.replaceAll((k, v) -> null);
  }

  @Test
  void testSimpleMethods() {
    final Knight knight = new Knight(PieceColor.WHITE);
    knight.postMoveUpdate();
    Assertions.assertEquals("KN", knight.toString());
    Assertions.assertEquals("img/knight_white.png", knight.getIconPath());
    Assertions.assertEquals(PieceColor.WHITE, knight.getPieceColor());
    final Knight knight1 = new Knight(PieceColor.WHITE);
    Assertions.assertNotEquals(knight, knight1);
    Assertions.assertNotEquals(knight.hashCode(), knight1.hashCode());

    BOARD.put(Tile.C8, knight);
    BOARD.put(Tile.C3, knight1);
    Assertions.assertEquals(Tile.C8, knight.getCurrentPosition(BOARD));
    Assertions.assertEquals(Tile.C3, knight1.getCurrentPosition(BOARD));

    BOARD.put(Tile.C8, null);
    Assertions.assertNull(knight.getCurrentPosition(BOARD));
    Assertions.assertNull(knight.getCurrentPosition(null));
    Assertions.assertNull(knight.getCurrentPosition(new HashMap<>()));
  }

  @Test
  void testAvailableMoves() {
    final Knight knight = new Knight(PieceColor.WHITE);
    BOARD.put(Tile.B8, knight);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getKnightMoves(BOARD, Tile.B8)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, knight, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD));
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, false));
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, false, false));
    }
    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, knight);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);
    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getKnightMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, knight, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, true));
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock.when(() -> PieceUtils.getKnightMoves(BOARD, Tile.B1)).thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, knight, moves, BOARD))
          .thenReturn(moves);
      moves.clear();
      TestHelper.validatePositions(moves, knight.getAvailableMoves(BOARD, true, true));
    }
  }

  @Test
  void testAvailableAttackMoves() {
    final Knight knight = new Knight(PieceColor.WHITE);
    BOARD.put(Tile.B8, knight);

    final List<Tile> moves = new ArrayList<>();
    moves.add(Tile.B7);
    moves.add(Tile.B6);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKnightAttackMoves(BOARD, Tile.B8, knight.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B8, knight, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD));
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, false));
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, false, true));
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, false, false));
    }

    BOARD.put(Tile.B8, null);
    BOARD.put(Tile.B1, knight);
    moves.clear();
    moves.add(Tile.B2);
    moves.add(Tile.B3);

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKnightAttackMoves(BOARD, Tile.B1, knight.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, knight, moves, BOARD))
          .thenReturn(moves);

      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, true));
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, true, true));
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, true, false));
    }

    try (final MockedStatic<PieceUtils> pieceUtilsMock = Mockito.mockStatic(PieceUtils.class)) {
      pieceUtilsMock
          .when(() -> PieceUtils.getKnightAttackMoves(BOARD, Tile.B1, knight.pieceColor))
          .thenReturn(moves);
      pieceUtilsMock
          .when(() -> PieceUtils.filterMovesToAvoidCheck(Tile.B1, knight, moves, BOARD))
          .thenReturn(moves);

      moves.clear();
      TestHelper.validatePositions(moves, knight.getAttackMoves(BOARD, true, true));
    }
  }
}
