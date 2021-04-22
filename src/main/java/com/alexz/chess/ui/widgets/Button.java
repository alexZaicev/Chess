package com.alexz.chess.ui.widgets;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.models.ICallable;
import com.alexz.chess.services.CfgProvider;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

  private static final long serialVersionUID = 6175488897663485105L;

  public Button() {
    this(null, null);
  }

  public Button(final String text) {
    this(text, null);
  }

  public Button(final String text, final ICallable callable) {
    super(StringUtils.isBlank(text) ? text : text.toUpperCase());
    this.setFont((Font) CfgProvider.getInstance().get(ConfigKey.FONT_H6));
    this.setSize(85, 30);
    if (callable != null) {
      this.addActionListener(e -> callable.call(null));
    }
  }

  @Override
  public void setSize(final Dimension d) {
    super.setSize(d);
    this.setPreferredSize(d);
  }

  @Override
  public void setSize(final int width, final int height) {
    super.setSize(width, height);
    this.setPreferredSize(new Dimension(width, height));
  }

  public void setOnClick(final ICallable callable) {
    final ActionListener[] listeners = this.getActionListeners();
    if (listeners != null && listeners.length > 0) {
      for (final ActionListener l : this.getActionListeners()) {
        this.removeActionListener(l);
      }
    }
    this.addActionListener(e -> callable.call(null));
  }
}
