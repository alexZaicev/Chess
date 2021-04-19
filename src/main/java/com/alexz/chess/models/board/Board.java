package com.alexz.chess.models.board;

import com.alexz.chess.models.EntityBase;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.models.players.IPlayer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Board extends EntityBase {

  private final Map<Tile, IPiece> board;
  private final Set<Tile> availableMoves;
  private final Set<Tile> availableAttackMoves;

  private BoardState state;
  private PieceColor turnHolder;
  private IPlayer playerA;
  private IPlayer playerB;
  private PieceColor check;

  public Board() {
    this.state = BoardState.IN_MENU;
    this.turnHolder = PieceColor.WHITE;
    this.check = PieceColor.NONE;
    this.board = new TreeMap<>();
    this.availableMoves = new HashSet<>();
    this.availableAttackMoves = new HashSet<>();
  }

  public PieceColor getCheck() {
    return check;
  }

  public void setCheck(final PieceColor check) {
    this.check = check;
  }

  public Set<Tile> getAvailableAttackMoves() {
    return availableAttackMoves;
  }

  public Set<Tile> getAvailableMoves() {
    return availableMoves;
  }

  public IPlayer getPlayerA() {
    return playerA;
  }

  public void setPlayerA(final IPlayer playerA) {
    this.playerA = playerA;
  }

  public IPlayer getPlayerB() {
    return playerB;
  }

  public void setPlayerB(final IPlayer playerB) {
    this.playerB = playerB;
  }

  public Map<Tile, IPiece> getBoard() {
    return board;
  }

  public void setTurnHolder(final PieceColor turnHolder) {
    this.turnHolder = turnHolder;
  }

  public boolean isPlayersTurn(final PieceColor color) {
    return color == this.turnHolder;
  }

  public BoardState getState() {
    return state;
  }

  public void setState(final BoardState state) {
    this.state = state;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;

    if (!(o instanceof Board)) return false;

    final Board board1 = (Board) o;

    return new EqualsBuilder()
        .append(getBoard(), board1.getBoard())
        .append(getAvailableMoves(), board1.getAvailableMoves())
        .append(getAvailableAttackMoves(), board1.getAvailableAttackMoves())
        .append(getState(), board1.getState())
        .append(turnHolder, board1.turnHolder)
        .append(getPlayerA(), board1.getPlayerA())
        .append(getPlayerB(), board1.getPlayerB())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getBoard())
        .append(getAvailableMoves())
        .append(getAvailableAttackMoves())
        .append(getState())
        .append(turnHolder)
        .append(getPlayerA())
        .append(getPlayerB())
        .toHashCode();
  }
}
