package com.alexz.chess.models.pieces;

import java.awt.*;

public enum PieceColor {
  WHITE(new Color(240, 240, 240)),
  BLACK(new Color(25, 25, 25)),
  ;

  public final Color color;

  PieceColor(final Color color) {
    this.color = color;
  }
}
