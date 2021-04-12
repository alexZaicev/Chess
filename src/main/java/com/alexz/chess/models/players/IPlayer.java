package com.alexz.chess.models.players;

import com.alexz.chess.models.pieces.PieceColor;

import java.io.Serializable;

public interface IPlayer extends Serializable {

  PieceColor getPieceColor();
}
