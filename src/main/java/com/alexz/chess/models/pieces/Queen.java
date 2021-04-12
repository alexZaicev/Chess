package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.List;
import java.util.Map;

public class Queen extends PieceBase {
  public Queen(PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "Q";
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board) {
    return null;
  }
}
