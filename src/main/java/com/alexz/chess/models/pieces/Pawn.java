package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.List;
import java.util.Map;

public class Pawn extends PieceBase {
  public Pawn(PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "P";
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board) {
    return null;
  }
}
