package com.alexz.chess.models.exceptions;

public class DrawException extends ChessException {
  public DrawException() {
    super("Player decided to call a draw");
  }
}
