package com.alexz.chess.ui.widgets;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.services.CfgProvider;

import javax.swing.*;
import java.awt.*;

public class TextArea extends JTextArea {

  public TextArea(final String text, final boolean wrap, final boolean editable) {
    this(text, wrap, 0, 0, editable, 0, 0);
  }

  public TextArea(
      final String text,
      final boolean wrap,
      final int x,
      final int y,
      final boolean editable,
      final int row,
      final int column) {
    super(text, row, column);
    this.setWrapStyleWord(wrap);
    this.setLineWrap(wrap);
    this.setLocation(x, y);
    this.setFont((Font) CfgProvider.getInstance().get(ConfigKey.FONT_P));
    this.setEditable(editable);
  }

  @Override
  public void setSize(final int width, final int height) {
    super.setSize(width, height);
    this.setPreferredSize(new Dimension(width, height));
  }

  @Override
  public void setSize(final Dimension d) {
    super.setSize(d);
    this.setPreferredSize(d);
  }
}
