package com.alexz.chess.models.board;

import com.alexz.chess.models.EntityBase;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.models.players.IPlayer;

import java.util.Map;
import java.util.TreeMap;

public class Board extends EntityBase {

  private final Map<Tile, IPiece> board;
  private BoardState state;
  private PieceColor turnHolder;
  private IPlayer playerA;
  private IPlayer playerB;

  public Board() {
    this.state = BoardState.IN_MENU;
    this.turnHolder = PieceColor.WHITE;
    this.board = new TreeMap<>();
  }

  public IPlayer getPlayerA() {
    return playerA;
  }

  public void setPlayerA(IPlayer playerA) {
    this.playerA = playerA;
  }

  public IPlayer getPlayerB() {
    return playerB;
  }

  public void setPlayerB(IPlayer playerB) {
    this.playerB = playerB;
  }

  public Map<Tile, IPiece> getBoard() {
    return board;
  }

  public void setTurnHolder(PieceColor turnHolder) {
    this.turnHolder = turnHolder;
  }

  public boolean isPlayersTurn(final PieceColor color) {
    return color == this.turnHolder;
  }

  public BoardState getState() {
    return state;
  }

  public void setState(BoardState state) {
    this.state = state;
  }
}
