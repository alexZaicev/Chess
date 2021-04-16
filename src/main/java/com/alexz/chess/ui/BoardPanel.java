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
import org.apache.commons.text.CaseUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        new Label(
            CfgProvider.getInstance().getStr(ConfigKey.TITLE),
            0,
            0,
            SwingConstants.CENTER,
            ConfigKey.FONT_H2);
    lbl.setLocation((this.getWidth() - lbl.getWidth()) / 2, 35);
    this.add(lbl);
  }

  private void composeInGame() {
    String text =
        "Player A ["
            + CaseUtils.toCamelCase(this.board.getPlayerA().getPieceColor().toString(), true)
            + "]";
    Label lbl = new Label(text, 0, 0, ConfigKey.FONT_H6);
    this.add(lbl);

    text =
        "Player B ["
            + CaseUtils.toCamelCase(this.board.getPlayerB().getPieceColor().toString(), true)
            + "]";
    lbl = new Label(text, 0, 0, SwingConstants.RIGHT, ConfigKey.FONT_H6);
    lbl.setLocation(this.getWidth() - lbl.getWidth(), 0);
    this.add(lbl);

    final String txt =
        this.board.isPlayersTurn(this.board.getPlayerA().getPieceColor())
            ? "Player A turn"
            : "Player B turn";
    lbl =
        new Label(
            txt,
            (this.getWidth() - lbl.getWidth()) / 2,
            35,
            SwingConstants.CENTER,
            ConfigKey.FONT_H5);
    this.add(lbl);

    final JPanel grid = new JPanel(new GridBagLayout());
    final int width = this.getWidth() - 60;
    final int height = this.getWidth() - 60;
    grid.setBounds((this.getWidth() - width - 10) / 2, 95, width, height);
    grid.setSize(new Dimension(width, height));
    grid.setPreferredSize(new Dimension(width, height));

    final int btnWidth = 40;
    final int btnHeight = 40;

    final List<String> columns = this.getColNames();
    for (int i = 0; i < 9; ++i) {
      for (final String s : columns) {
        if (i == 0) {
          final Label btn = new Label(s, 0, 0, SwingConstants.CENTER, ConfigKey.FONT_P_BOLD);
          btn.setSize(new Dimension(btnWidth, btnHeight - 30));
          grid.add(btn, this.getGbc(columns.indexOf(s) + 1, 0));
        } else {
          final Tile tile = Tile.valueOf(String.format("%s%d", s, i));
          final IPiece piece = this.board.getBoard().get(tile);
          final Button btn = this.getBoardBtn(tile, piece);
          btn.setSize(new Dimension(btnWidth, btnHeight));
          grid.add(btn, this.getGbc(columns.indexOf(s) + 1, i));
        }
      }
    }

    for (int i = 0; i < 9; ++i) {
      if (i == 0) {
        continue;
      }
      final Label btn =
          new Label(Integer.toString(i), 0, 0, SwingConstants.CENTER, ConfigKey.FONT_P_BOLD);
      btn.setSize(new Dimension(btnWidth - 30, btnHeight));
      grid.add(btn, this.getGbc(0, i));
    }

    this.add(grid);
  }

  private Button getBoardBtn(final Tile tile, final IPiece piece) {
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
    return btn;
  }

  private List<String> getColNames() {
    final Set<String> columns = new HashSet<>();
    for (final Tile tile : Tile.values()) {
      columns.add(tile.name().substring(0, 1));
    }
    return new ArrayList<>(columns);
  }

  private GridBagConstraints getGbc(final int gridX, final int gridY) {
    final GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.PAGE_START;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = gridX;
    gbc.gridy = gridY;
    gbc.gridwidth = 1;
    gbc.gridheight = 1;
    gbc.weightx = 1;
    gbc.weighty = 1;
    return gbc;
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
