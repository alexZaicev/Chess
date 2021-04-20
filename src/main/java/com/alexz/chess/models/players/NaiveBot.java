package com.alexz.chess.models.players;

import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.exceptions.DrawException;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBot extends BotBase {

  private static final Logger _logger = LogManager.getLogger(NaiveBot.class);

  public NaiveBot(final PieceColor color) {
    super(color, Difficulty.EASY);
  }

  @Override
  public Move getNextMove(final Map<Tile, IPiece> board) throws DrawException {
    final Map<Tile, IPiece> myPieces = new HashMap<>();
    for (final Map.Entry<Tile, IPiece> e : board.entrySet()) {
      if (e.getValue() != null && e.getValue().getPieceColor() == this.getPieceColor()) {
        myPieces.put(e.getKey(), e.getValue());
      }
    }
    final List<Tile> keys = new ArrayList<>(myPieces.keySet());
    Move move = null;
    while (move == null) {
      if (keys.isEmpty()) {
        break;
      }
      final Tile position = keys.get(RandomUtils.nextInt(0, keys.size()));
      final IPiece piece = myPieces.get(position);
      final List<Tile> availableMoves = piece.getAvailableMoves(board, true);
      availableMoves.addAll(piece.getAttackMoves(board, true));
      if (availableMoves.isEmpty()) {
        keys.remove(position);
        continue;
      }
      final Tile newPosition = availableMoves.get(RandomUtils.nextInt(0, availableMoves.size()));
      move = new Move(this.getPieceColor(), position, newPosition, piece);
    }
    if (move == null) {
      _logger.debug("Bot has no valid move to make. Calling draw");
      throw new DrawException();
    }
    return move;
  }
}
