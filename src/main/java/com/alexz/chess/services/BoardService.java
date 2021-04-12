package com.alexz.chess.services;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.*;
import com.alexz.chess.models.pieces.*;
import com.alexz.chess.models.players.BotBase;
import com.alexz.chess.models.players.Player;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardService extends ServiceBase {

  private static final Object lock = new Object();
  private static BoardService instance;
  private final List<IBoardListener> listeners;
  private final Board board;
  private Difficulty difficulty;

  public BoardService() {
    this.listeners = new ArrayList<>();
    this.board = new Board();
    this.difficulty = Difficulty.valueOf(CfgProvider.getInstance().getStr(ConfigKey.DIFFICULTY));
  }

  public static BoardService getInstance() {
    BoardService result = instance;
    if (result == null) {
      synchronized (lock) {
        result = instance;
        if (result == null) instance = result = new BoardService();
      }
    }
    return result;
  }

  public void registerListener(final IBoardListener listener) {
    listeners.add(listener);
  }

  public void notifyListeners() {
    // check if bot has next turn
    if (this.board.getState() == BoardState.IN_GAME
        && this.board.isPlayersTurn(this.board.getPlayerB().getPieceColor())) {
      final Board copy = SerializationUtils.clone(this.board);
      final BotWorker worker = new BotWorker((BotBase) copy.getPlayerB(), copy.getBoard());
      worker.execute();
    }
    for (final IBoardListener l : listeners) {
      l.notify(this.board);
    }
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void onPlayerAction(final Move move) {
    // TODO
  }

  public void startNewGame() {
    this.board.setState(BoardState.IN_GAME);

    this.board.setTurnHolder(PieceColor.WHITE);
    if (RandomUtils.nextInt(0, 10) % 2 == 0) {
      this.board.setPlayerA(new Player(PieceColor.WHITE));
      this.board.setPlayerB(BotFactory.getBot(PieceColor.BLACK, this.difficulty));
    } else {
      this.board.setPlayerA(new Player(PieceColor.BLACK));
      this.board.setPlayerB(BotFactory.getBot(PieceColor.WHITE, this.difficulty));
    }

    this.board.getBoard().clear();
    this.addPieces(this.board.getBoard(), PieceColor.WHITE);
    this.addPieces(this.board.getBoard(), PieceColor.BLACK);
    for (final Tile t : Tile.values()) {
      if (!this.board.getBoard().containsKey(t)) {
        this.board.getBoard().put(t, null);
      }
    }
    this.notifyListeners();
  }

  public void addPieces(final Map<Tile, IPiece> board, final PieceColor color) {
    for (final Tile tile : Tile.getInitialPawnPositions(color)) {
      board.put(tile, new Pawn(color));
    }
    for (final Tile tile : Tile.getInitialRookPositions(color)) {
      board.put(tile, new Rook(color));
    }
    for (final Tile tile : Tile.getInitialKnightPositions(color)) {
      board.put(tile, new Knight(color));
    }
    for (final Tile tile : Tile.getInitialBishopPositions(color)) {
      board.put(tile, new Bishop(color));
    }
    for (final Tile tile : Tile.getInitialQueenPositions(color)) {
      board.put(tile, new Queen(color));
    }
    for (final Tile tile : Tile.getInitialKingPositions(color)) {
      board.put(tile, new King(color));
    }
  }
}
