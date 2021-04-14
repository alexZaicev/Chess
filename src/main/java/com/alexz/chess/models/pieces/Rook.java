package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Rook extends PieceBase {
  public Rook(PieceColor color) {
    super(color);
  }

  @Override
  public String toString() {
    return "R";
  }

  @Override
  public List<Tile> getAttackMoves(Map<Tile, IPiece> board) {
    return new ArrayList<>();
  }

  @Override
  public List<Tile> getAttackMoves(Map<Tile, IPiece> board, final boolean isBot) {
    return new ArrayList<>();
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board) {
    return new ArrayList<>();
  }

  @Override
  public List<Tile> getAvailableMoves(Map<Tile, IPiece> board, boolean isBot) {
    return new ArrayList<>();
  }

  @Override
  public void postMoveUpdate() {

  }
}
