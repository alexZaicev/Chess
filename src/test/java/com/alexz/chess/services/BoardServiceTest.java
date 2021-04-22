package com.alexz.chess.services;

import com.alexz.chess.TestHelper;
import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.BoardState;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.*;
import com.alexz.chess.models.players.IPlayer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.Mockito.when;

public class BoardServiceTest {

  final IPlayer paMock = Mockito.mock(IPlayer.class);
  final IPlayer pbMock = Mockito.mock(IPlayer.class);

  @AfterEach
  void cleanUp() {
    BoardService.getInstance().clearNotifiers();
    BoardService.getInstance().getBoard().setPlayerA(null);
    BoardService.getInstance().getBoard().setPlayerB(null);
  }

  @Test
  void testDifficulty() {
    Assertions.assertEquals(Difficulty.EASY, BoardService.getInstance().getDifficulty());
    BoardService.getInstance().setDifficulty(Difficulty.HARD);
    Assertions.assertEquals(Difficulty.HARD, BoardService.getInstance().getDifficulty());
  }

  @Test
  void testRegisterNotifiers() {
    final boolean[] notified = new boolean[] {false};
    BoardService.getInstance().registerListener(board -> notified[0] = true);
    BoardService.getInstance().notifyListeners();
    Assertions.assertTrue(notified[0]);

    // cover all random scenarios
    for (int i = 0; i < 100; ++i) {
      notified[0] = false;
      BoardService.getInstance().startNewGame();
      BoardService.getInstance().notifyListeners();
      Assertions.assertTrue(notified[0]);
      Assertions.assertNotNull(BoardService.getInstance().getBoard());
    }
  }

  @Test
  void testStartNewGame() {
    final boolean[] notified = new boolean[] {false};
    BoardService.getInstance()
        .registerListener(
            board -> {
              notified[0] = true;
              Assertions.assertNotNull(board);
              Assertions.assertEquals(BoardState.IN_GAME, board.getState());
              Assertions.assertTrue(board.isPlayersTurn(PieceColor.WHITE));
              Assertions.assertFalse(board.isPlayersTurn(PieceColor.BLACK));
              Assertions.assertNotNull(board.getPlayerA());
              Assertions.assertNotNull(board.getPlayerB());
              Assertions.assertEquals(64, board.getBoard().size());

              final PieceColor pcA = board.getPlayerA().getPieceColor();
              final PieceColor pcB = board.getPlayerB().getPieceColor();

              // check player B positions
              TestHelper.validatePieces(board.getBoard(), pcB, Rook.class, Tile.A1);
              TestHelper.validatePieces(board.getBoard(), pcB, Rook.class, Tile.H1);
              TestHelper.validatePieces(board.getBoard(), pcB, Knight.class, Tile.B1);
              TestHelper.validatePieces(board.getBoard(), pcB, Knight.class, Tile.G1);
              TestHelper.validatePieces(board.getBoard(), pcB, Bishop.class, Tile.C1);
              TestHelper.validatePieces(board.getBoard(), pcB, Bishop.class, Tile.F1);
              if (pcB == PieceColor.WHITE) {
                TestHelper.validatePieces(board.getBoard(), pcB, King.class, Tile.D1);
                TestHelper.validatePieces(board.getBoard(), pcB, Queen.class, Tile.E1);
              } else {
                TestHelper.validatePieces(board.getBoard(), pcB, King.class, Tile.E1);
                TestHelper.validatePieces(board.getBoard(), pcB, Queen.class, Tile.D1);
              }
              TestHelper.validatePieces(
                  board.getBoard(),
                  pcB,
                  Pawn.class,
                  Tile.A2,
                  Tile.B2,
                  Tile.C2,
                  Tile.D2,
                  Tile.E2,
                  Tile.F2,
                  Tile.G2,
                  Tile.H2);

              // check player A positions
              TestHelper.validatePieces(board.getBoard(), pcA, Rook.class, Tile.A8);
              TestHelper.validatePieces(board.getBoard(), pcA, Rook.class, Tile.H8);
              TestHelper.validatePieces(board.getBoard(), pcA, Knight.class, Tile.B8);
              TestHelper.validatePieces(board.getBoard(), pcA, Knight.class, Tile.G8);
              TestHelper.validatePieces(board.getBoard(), pcA, Bishop.class, Tile.C8);
              TestHelper.validatePieces(board.getBoard(), pcA, Bishop.class, Tile.F8);
              if (pcA == PieceColor.WHITE) {
                TestHelper.validatePieces(board.getBoard(), pcA, King.class, Tile.E8);
                TestHelper.validatePieces(board.getBoard(), pcA, Queen.class, Tile.D8);
              } else {
                TestHelper.validatePieces(board.getBoard(), pcA, King.class, Tile.D8);
                TestHelper.validatePieces(board.getBoard(), pcA, Queen.class, Tile.E8);
              }
              TestHelper.validatePieces(
                  board.getBoard(),
                  pcA,
                  Pawn.class,
                  Tile.A7,
                  Tile.B7,
                  Tile.C7,
                  Tile.D7,
                  Tile.E7,
                  Tile.F7,
                  Tile.G7,
                  Tile.H7);

              int numOfNulls = 0;
              for (final IPiece piece : board.getBoard().values()) {
                if (piece == null) {
                  numOfNulls += 1;
                }
              }
              Assertions.assertEquals(32, numOfNulls);
            });
    BoardService.getInstance().startNewGame();
    Assertions.assertTrue(notified[0], "Notifier was not called");
  }

  @Test
  void testCallDrawGame() {
    final boolean[] notified = new boolean[] {false};
    BoardService.getInstance()
        .registerListener(
            board -> {
              notified[0] = true;
              Assertions.assertEquals(BoardState.DRAW, board.getState());
            });
    BoardService.getInstance().callDrawGame();
    Assertions.assertTrue(notified[0]);
  }

  @Test
  void testGetWinPieceColor_noWin() {
    Mockito.reset(paMock, pbMock);
    BoardService.getInstance().getBoard().setPlayerA(paMock);
    BoardService.getInstance().getBoard().setPlayerB(pbMock);
    when(paMock.getPieceColor()).thenReturn(PieceColor.WHITE);
    when(pbMock.getPieceColor()).thenReturn(PieceColor.BLACK);

    Assertions.assertEquals(
        PieceColor.NONE,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.noCheckBoard(), PieceColor.NONE, PieceColor.WHITE));
    Assertions.assertEquals(
        PieceColor.NONE,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.boardOption_3(), PieceColor.BLACK, PieceColor.WHITE));
  }

  @Test
  void testGetWinPieceColor_WhiteWin() {
    Mockito.reset(paMock, pbMock);
    BoardService.getInstance().getBoard().setPlayerA(paMock);
    BoardService.getInstance().getBoard().setPlayerB(pbMock);
    when(paMock.getPieceColor()).thenReturn(PieceColor.WHITE);
    when(pbMock.getPieceColor()).thenReturn(PieceColor.BLACK);

    final Map<Tile, IPiece> board = TestHelper.noCheckBoard();
    board.put(Tile.E1, null);
    Assertions.assertEquals(
        PieceColor.WHITE,
        BoardService.getInstance().getWinPieceColor(board, PieceColor.NONE, PieceColor.WHITE));
    Assertions.assertEquals(
        PieceColor.WHITE,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.boardOption_1(), PieceColor.BLACK, PieceColor.WHITE));

    Assertions.assertEquals(
        PieceColor.WHITE,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.boardOption_2(), PieceColor.BLACK, PieceColor.WHITE));
  }

  @Test
  void testGetWinPieceColor_BlackWin() {
    Mockito.reset(paMock, pbMock);
    BoardService.getInstance().getBoard().setPlayerA(paMock);
    BoardService.getInstance().getBoard().setPlayerB(pbMock);
    when(paMock.getPieceColor()).thenReturn(PieceColor.WHITE);
    when(pbMock.getPieceColor()).thenReturn(PieceColor.BLACK);

    final Map<Tile, IPiece> board = TestHelper.noCheckBoard();
    board.put(Tile.G8, null);
    Assertions.assertEquals(
        PieceColor.BLACK,
        BoardService.getInstance().getWinPieceColor(board, PieceColor.NONE, PieceColor.WHITE));
    Assertions.assertEquals(
        PieceColor.BLACK,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.boardOption_4(), PieceColor.WHITE, PieceColor.WHITE));

    when(paMock.getPieceColor()).thenReturn(PieceColor.BLACK);
    when(pbMock.getPieceColor()).thenReturn(PieceColor.WHITE);
    Assertions.assertEquals(
        PieceColor.BLACK,
        BoardService.getInstance()
            .getWinPieceColor(TestHelper.boardOption_5(), PieceColor.WHITE, PieceColor.WHITE));
  }

  @Test
  void testCheckedPlayers() {
    Mockito.reset(paMock, pbMock);
    BoardService.getInstance().getBoard().setPlayerA(paMock);
    BoardService.getInstance().getBoard().setPlayerB(pbMock);
    when(paMock.getPieceColor()).thenReturn(PieceColor.BLACK);
    when(pbMock.getPieceColor()).thenReturn(PieceColor.WHITE);

    final Map<PieceColor, Boolean> checks =
        BoardService.getInstance().getCheckedPlayers(TestHelper.boardOption_6(), true);
    Assertions.assertFalse(checks.get(PieceColor.WHITE));
    Assertions.assertTrue(checks.get(PieceColor.BLACK));
  }
}
