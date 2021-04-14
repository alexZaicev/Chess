package com.alexz.chess.ui;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.models.board.Board;
import com.alexz.chess.models.board.IBoardListener;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.services.BoardService;
import com.alexz.chess.services.CfgProvider;
import com.alexz.chess.ui.widgets.Button;
import com.alexz.chess.ui.widgets.Label;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel implements IBoardListener {

  private static final Logger _logger = LogManager.getLogger(BoardPanel.class);
  private Board board;

  public BoardPanel() {
    super();
    this.setLayout(null);
    final int width = CfgProvider.getInstance().getInt(ConfigKey.WINDOW_WIDTH) - 25;
    final int height = CfgProvider.getInstance().getInt(ConfigKey.WINDOW_HEIGHT) - 75;
    this.setSize(new Dimension(width, height));
    this.setPreferredSize(new Dimension(width, height));
    this.setBounds(10, 10, width, height);
  }

  @Override
  public void notify(Board board) {
    if (board.equals(this.board)) {
      return;
    }
    this.board = SerializationUtils.clone(board);
    this.compose();
  }

  private void compose() {
    this.removeAll();
    switch (this.board.getState()) {
      case IN_MENU:
        {
          this.composeInMenu();
          break;
        }
      case IN_GAME:
        {
          this.composeInGame();
          break;
        }
      case PLAYER_A_WINS:
      case PLAYER_B_WINS:
      case DRAW:
        {
          this.composeGameOver();
          break;
        }
    }
    this.revalidate();
    this.repaint();
  }

  private void composeInMenu() {
    Label lbl =
        new Label(CfgProvider.getInstance().getStr(ConfigKey.TITLE), 0, 0, SwingConstants.CENTER);
    lbl.setFont(ConfigKey.FONT_H2);
    lbl.setSize(200, lbl.getFont().getSize());
    lbl.setLocation((this.getWidth() - lbl.getWidth()) / 2, 35);
    this.add(lbl);
  }

  private void composeInGame() {
    Label lbl = new Label("Player A", 0, 0);
    lbl.setFont(ConfigKey.FONT_H4);
    this.add(lbl);

    lbl = new Label("Player B", this.getWidth() - lbl.getWidth(), 0, SwingConstants.RIGHT);
    lbl.setFont(ConfigKey.FONT_H4);
    this.add(lbl);

    final String txt =
        this.board.isPlayersTurn(this.board.getPlayerA().getPieceColor())
            ? "Player A turn"
            : "Player B turn";
    lbl = new Label(txt, (this.getWidth() - lbl.getWidth()) / 2, 35, SwingConstants.CENTER);
    lbl.setFont(ConfigKey.FONT_H5);
    this.add(lbl);

    final JPanel grid = new JPanel(new GridLayout(8, 8));
    final String[] cols = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
    for (int i = 1; i < 9; ++i) {
      for (final String s : cols) {
        final Tile tile = Tile.valueOf(String.format("%s%d", s, i));
        final IPiece piece = this.board.getBoard().get(tile);
        final Button btn = new Button(piece == null ? "" : piece.toString());
        btn.setBackground(this.getBtnColor(tile));

        if (piece != null
            || this.board.getAvailableMoves().contains(tile)
            || this.board.getAvailableAttackMoves().contains(tile)) {
          if (piece != null) {
            btn.setForeground(
                (Color)
                    CfgProvider.getInstance()
                        .get(
                            piece.getPieceColor() == PieceColor.WHITE
                                ? ConfigKey.COLOR_PIECE_WHITE
                                : ConfigKey.COLOR_PIECE_BLACK));
          }
          btn.setEnabled(true);
          btn.setOnClick(
              obj -> {
                _logger.debug("Tile pressed [" + tile + "]");
                BoardService.getInstance().onPlayerAction(tile, piece);
              });

        } else {
          btn.setEnabled(false);
        }
        grid.add(btn);
      }
    }

    final int width = this.getWidth() - 100;
    final int height = this.getHeight() - 100;
    grid.setBounds((this.getWidth() - width) / 2, 85, width, height);
    grid.setSize(new Dimension(width, height));
    grid.setPreferredSize(new Dimension(width, height));
    this.add(grid);
  }

  private void composeGameOver() {
    // TODO:
  }

  private Color getBtnColor(final Tile tile) {
    final int row = Integer.parseInt(tile.name().substring(1));
    boolean white = row % 2 == 0;
    final String[] cols = new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
    for (final String col : cols) {
      if (tile.name().startsWith(cols[0])) {
        break;
      }
      if (tile.name().startsWith(col)) {
        break;
      }
      white = !white;
    }
    if (this.board.getAvailableMoves().contains(tile)) {
      return Color.green;
    }
    if (this.board.getAvailableAttackMoves().contains(tile)) {
      return Color.red;
    }
    return (Color)
        CfgProvider.getInstance()
            .get(white ? ConfigKey.COLOR_TILE_WHITE : ConfigKey.COLOR_TILE_BLACK);
  }
}
