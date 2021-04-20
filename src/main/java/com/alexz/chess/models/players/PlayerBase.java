package com.alexz.chess.models.players;

import com.alexz.chess.models.pieces.PieceColor;

public abstract class PlayerBase implements IPlayer {

  private final PieceColor color;

  public PlayerBase(final PieceColor color) {
    this.color = color;
  }

  @Override
  public PieceColor getPieceColor() {
    return this.color;
  }
}
