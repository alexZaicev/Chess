package com.alexz.chess.models.pieces;

import java.awt.*;

public enum PieceColor {
  NONE,
  WHITE(new Color(240, 240, 240)),
  BLACK(new Color(25, 25, 25)),
  ;

  public final Color color;

  PieceColor() {
    this.color = null;
  }

  PieceColor(final Color color) {
    this.color = color;
  }
}
