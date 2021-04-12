package com.alexz.chess.models.pieces;

public abstract class PieceBase implements IPiece {

  protected PieceColor pieceColor;

  public PieceBase(PieceColor pieceColor) {
    this.pieceColor = pieceColor;
  }

  @Override
  public PieceColor getPieceColor() {
    return this.pieceColor;
  }

  public void setPieceColor(PieceColor pieceColor) {
    this.pieceColor = pieceColor;
  }
}
