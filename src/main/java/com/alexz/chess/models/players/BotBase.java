package com.alexz.chess.models.players;

import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.exceptions.DrawException;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;

import java.util.Map;

public abstract class BotBase extends PlayerBase {

  private Difficulty difficulty;

  public BotBase(final PieceColor color, final Difficulty difficulty) {
    super(color);
    this.difficulty = difficulty;
  }

  public abstract Move getNextMove(final Map<Tile, IPiece> board) throws DrawException;

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(final Difficulty difficulty) {
    this.difficulty = difficulty;
  }
}
