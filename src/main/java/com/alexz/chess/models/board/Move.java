package com.alexz.chess.models.board;

import com.alexz.chess.models.pieces.IPiece;

import java.io.Serializable;

public class Move implements Serializable {

  private Tile oldPosition;
  private Tile newPosition;
  private IPiece piece;

  public Move(Tile oldPosition, Tile newPosition, IPiece piece) {
    this.oldPosition = oldPosition;
    this.newPosition = newPosition;
    this.piece = piece;
  }

  public Tile getOldPosition() {
    return oldPosition;
  }

  public void setOldPosition(Tile oldPosition) {
    this.oldPosition = oldPosition;
  }

  public Tile getNewPosition() {
    return newPosition;
  }

  public void setNewPosition(Tile newPosition) {
    this.newPosition = newPosition;
  }

  public IPiece getPiece() {
    return piece;
  }

  public void setPiece(IPiece piece) {
    this.piece = piece;
  }
}
