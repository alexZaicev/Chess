package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.List;
import java.util.Map;

public class Bishop extends PieceBase {
  public Bishop(PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "B";
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board) {
    return null;
  }
}