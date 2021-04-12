package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IPiece extends Serializable {

  PieceColor getPieceColor();

  List<Tile> getAvailableMoves(final Map<Tile, IPiece> board);
}
