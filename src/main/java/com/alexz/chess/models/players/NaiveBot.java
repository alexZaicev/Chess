package com.alexz.chess.models.players;

import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBot extends BotBase {

  public NaiveBot(PieceColor color) {
    super(color, Difficulty.EASY);
  }

  @Override
  public Move getNextMove(Map<Tile, IPiece> board) {
    final Map<Tile, IPiece> myPieces = new HashMap<>();
    for (final Map.Entry<Tile, IPiece> e : board.entrySet()) {
      if (e.getValue() != null && e.getValue().getPieceColor() == this.getPieceColor()) {
        myPieces.put(e.getKey(), e.getValue());
      }
    }
    Move move = null;
    while (move == null) {
      final List<Tile> keys = new ArrayList<>(myPieces.keySet());
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
    return move;
  }
}
