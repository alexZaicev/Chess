package com.alexz.chess.models.players;

import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;

import java.util.Map;

public class NaiveBot extends BotBase {

  public NaiveBot(PieceColor color) {
    super(color, Difficulty.EASY);
  }

  @Override
  public Move getNextMove(Map<Tile, IPiece> board) {
    return null;
  }
}
