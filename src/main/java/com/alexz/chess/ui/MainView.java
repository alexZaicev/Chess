package com.alexz.chess.ui;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.services.BoardService;
import com.alexz.chess.services.CfgProvider;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

  public MainView() {
    super(
        String.format(
            "%s (v.%s)",
            CfgProvider.getInstance().getStr(ConfigKey.TITLE),
            CfgProvider.getInstance().getStr(ConfigKey.VERSION)));
    this.compose();
    BoardService.getInstance().notifyListeners();
    this.pack();
  }

  private void compose() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(null);
    this.setResizable(false);
    this.setVisible(true);

    final int width = CfgProvider.getInstance().getInt(ConfigKey.WINDOW_WIDTH);
    final int height = CfgProvider.getInstance().getInt(ConfigKey.WINDOW_HEIGHT);
    this.setSize(new Dimension(width, height));
    this.setPreferredSize(new Dimension(width, height));
    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

    this.setJMenuBar(new MenuBar(this));

    final BoardPanel boardPanel = new BoardPanel();
    BoardService.getInstance().registerListener(boardPanel);
    this.add(boardPanel);
  }
}
