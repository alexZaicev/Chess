package com.alexz.chess.services;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.*;
import com.alexz.chess.models.pieces.*;
import com.alexz.chess.models.players.BotBase;
import com.alexz.chess.models.players.Player;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BoardService extends ServiceBase {

  private static final Logger _logger = LogManager.getLogger(BoardService.class);
  private static final Object lock = new Object();
  private static BoardService instance;
  private final List<IBoardListener> listeners;
  private final Board board;
  private Difficulty difficulty;
  private ImmutablePair<Tile, IPiece> selectedPiece;

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

  public void setDifficulty(final Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void onPlayerAction(final Tile tile, final IPiece piece) {
    if (this.selectedPiece == null || piece != null) {
      if (piece.getPieceColor() == this.board.getPlayerA().getPieceColor()) {
        this.selectedPiece = ImmutablePair.of(tile, piece);

        this.board.getAvailableMoves().clear();
        this.board.getAvailableMoves().addAll(piece.getAvailableMoves(this.board.getBoard()));

        this.board.getAvailableAttackMoves().clear();
        this.board.getAvailableAttackMoves().addAll(piece.getAttackMoves(this.board.getBoard()));
        this.checkWin();
        this.notifyListeners();
      } else {
        // attack move
        final Move move =
            new Move(
                this.board.getPlayerA().getPieceColor(),
                this.selectedPiece.getLeft(),
                tile,
                this.selectedPiece.getRight());
        this.onMoveAction(move);
      }
    } else {
      final Move move =
          new Move(
              this.board.getPlayerA().getPieceColor(),
              this.selectedPiece.getLeft(),
              tile,
              this.selectedPiece.getRight());
      this.onMoveAction(move);
    }
  }

  public void onMoveAction(final Move move) {
    this.onMoveAction(move, false);
  }

  public void onMoveAction(final Move move, final boolean isBot) {
    if (!isBot
        && !this.board.getAvailableMoves().contains(move.getNewPosition())
        && !this.board.getAvailableAttackMoves().contains(move.getNewPosition())) {
      this.selectedPiece = null;
      this.board.getAvailableMoves().clear();
      this.board.getAvailableAttackMoves().clear();
      this.notifyListeners();
      return;
    }

    // TODO: check if king is currently under check
    // TODO: check if castling position ends up under check
    if (move.getPiece() instanceof King) {
      final King king = (King) move.getPiece();
      if (king.isFirstMove()
          && king.getCastlingMoves(this.board.getBoard()).contains(move.getNewPosition())) {
        this.performCastlingMove(move);
      }
    }

    this.board.getBoard().put(move.getOldPosition(), null);
    this.board.getBoard().put(move.getNewPosition(), move.getPiece());
    move.getPiece().postMoveUpdate();
    _logger.debug(
        "Piece ["
            + move.getPiece().toString()
            + "] moves ["
            + move.getOldPosition()
            + "] to ["
            + move.getNewPosition()
            + "]");

    this.selectedPiece = null;
    this.board.getAvailableMoves().clear();
    this.board.getAvailableAttackMoves().clear();

    if (move.getPieceColor() == PieceColor.WHITE) {
      this.board.setTurnHolder(PieceColor.BLACK);
    } else {
      this.board.setTurnHolder(PieceColor.WHITE);
    }
    this.checkWin();

    this.notifyListeners();
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

    this.board.getAvailableMoves().clear();
    this.board.getBoard().clear();
    this.addPieces(this.board.getBoard(), this.board.getPlayerA().getPieceColor(), false);
    this.addPieces(this.board.getBoard(), this.board.getPlayerB().getPieceColor(), true);

    for (final Tile t : Tile.values()) {
      if (!this.board.getBoard().containsKey(t)) {
        this.board.getBoard().put(t, null);
      }
    }
    this.notifyListeners();
  }

  public void addPieces(
      final Map<Tile, IPiece> board, final PieceColor color, final boolean isBot) {
    for (final Tile tile : Tile.getInitialPawnPositions(isBot)) {
      board.put(tile, new Pawn(color));
    }
    for (final Tile tile : Tile.getInitialRookPositions(isBot)) {
      board.put(tile, new Rook(color));
    }
    for (final Tile tile : Tile.getInitialKnightPositions(isBot)) {
      board.put(tile, new Knight(color));
    }
    for (final Tile tile : Tile.getInitialBishopPositions(isBot)) {
      board.put(tile, new Bishop(color));
    }
    for (final Tile tile : Tile.getInitialQueenPositions(isBot, color)) {
      board.put(tile, new Queen(color));
    }
    for (final Tile tile : Tile.getInitialKingPositions(isBot, color)) {
      board.put(tile, new King(color));
    }
  }

  public PieceColor getCheckedPieceColor(final Map<Tile, IPiece> board) {
    return this.getCheckedPieceColor(board, true);
  }

  public PieceColor getCheckedPieceColor(final Map<Tile, IPiece> board, final boolean filter) {
    final List<Tile> attackMoves = new ArrayList<>();
    for (final IPiece piece : board.values()) {
      if (piece != null) {
        attackMoves.addAll(piece.getAttackMoves(board, false, filter));
      }
    }
    for (final Tile pos : attackMoves) {
      final IPiece piece = board.get(pos);
      if (piece instanceof King) {
        return piece.getPieceColor();
      }
    }
    return PieceColor.NONE;
  }

  public PieceColor getWinPieceColor(final Map<Tile, IPiece> board, final PieceColor checked) {
    final List<King> kings = new ArrayList<>();
    for (final IPiece piece : this.board.getBoard().values()) {
      if (piece instanceof King) {
        kings.add((King) piece);
      }
    }
    if (kings.size() == 1) {
      return kings.get(0).getPieceColor();
    }

    if (checked == PieceColor.NONE) {
      return PieceColor.NONE;
    }

    final Map<ImmutablePair<Tile, IPiece>, List<Tile>> possibleMoves = new HashMap<>();
    for (final Map.Entry<Tile, IPiece> e : this.board.getBoard().entrySet()) {
      final Tile pos = e.getKey();
      final IPiece piece = e.getValue();
      if (piece != null && piece.getPieceColor() == checked) {
        final List<Tile> moves = new ArrayList<>();
        moves.addAll(piece.getAvailableMoves(this.board.getBoard()));
        moves.addAll(piece.getAttackMoves(this.board.getBoard()));
        if (!moves.isEmpty()) {
          possibleMoves.put(ImmutablePair.of(pos, piece), moves);
        }
      }
    }

    for (final Map.Entry<ImmutablePair<Tile, IPiece>, List<Tile>> e : possibleMoves.entrySet()) {
      for (final Tile move : e.getValue()) {
        final Map<Tile, IPiece> newBoard = new TreeMap<>(board);
        newBoard.put(e.getKey().getLeft(), null);
        newBoard.put(move, e.getKey().getRight());
        final PieceColor pieceColor = this.getCheckedPieceColor(newBoard);
        if (pieceColor != checked) {
          return PieceColor.NONE;
        }
      }
    }
    return checked == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
  }

  public void callDrawGame() {
    this.board.setState(BoardState.DRAW);
    this.notifyListeners();
  }

  private void checkWin() {
    this.board.setCheck(this.getCheckedPieceColor(this.board.getBoard()));
    _logger.debug("Player ["+this.board.getCheck()+"] is checked");
    final PieceColor winPieceColor =
            this.getWinPieceColor(this.board.getBoard(), this.board.getCheck());
    if (winPieceColor == this.board.getPlayerA().getPieceColor()) {
      this.board.setState(BoardState.PLAYER_A_WINS);
      _logger.debug("Player A won");
    } else if (winPieceColor == this.board.getPlayerB().getPieceColor()) {
      this.board.setState(BoardState.PLAYER_B_WINS);
      _logger.debug("Player B won");
    }
  }

  private void performCastlingMove(final Move move) {
    final char col = move.getNewPosition().name().charAt(0);
    final int row = Integer.parseInt(move.getNewPosition().name().substring(1));
    final char oldCol = move.getOldPosition().name().charAt(0);
    final boolean leftSide = oldCol - col > 0;

    final Tile rookOldPos;
    final Tile rookNewPos;
    final IPiece rook;
    if (leftSide) {
      rookOldPos = Tile.valueOf(String.format("A%d", row));
      rook = this.board.getBoard().get(rookOldPos);
      rookNewPos = Tile.valueOf(String.format("%s%d", (char) (col + 1), row));
    } else {
      rookOldPos = Tile.valueOf(String.format("H%d", row));
      rook = this.board.getBoard().get(rookOldPos);
      rookNewPos = Tile.valueOf(String.format("%s%d", (char) (col - 1), row));
    }
    ((Rook) rook).setFirstMove(false);
    this.board.getBoard().put(rookOldPos, null);
    this.board.getBoard().put(rookNewPos, rook);
  }
}
