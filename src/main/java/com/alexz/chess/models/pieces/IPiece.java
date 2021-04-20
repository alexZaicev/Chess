package com.alexz.chess.models.pieces;

import com.alexz.chess.models.board.Tile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IPiece extends Serializable {

  String getIconPath();

  PieceColor getPieceColor();

  List<Tile> getAttackMoves(final Map<Tile, IPiece> board);

  List<Tile> getAttackMoves(final Map<Tile, IPiece> board, final boolean isBot);

  List<Tile> getAttackMoves(final Map<Tile, IPiece> board, final boolean isBot, final boolean filter);

  List<Tile> getAvailableMoves(final Map<Tile, IPiece> board);

  List<Tile> getAvailableMoves(final Map<Tile, IPiece> board, final boolean isBot);

  List<Tile> getAvailableMoves(final Map<Tile, IPiece> board, final boolean isBot, final boolean filter);

  void postMoveUpdate();
}
