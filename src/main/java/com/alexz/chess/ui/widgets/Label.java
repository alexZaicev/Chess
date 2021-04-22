package com.alexz.chess.ui.widgets;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.services.CfgProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class Label extends JLabel {

  private static final long serialVersionUID = -3641364140662611153L;

  public Label(final String text, final int x, final int y) {
    this(text, x, y, LEFT);
  }

  public Label(final String text, final int x, final int y, final ConfigKey font) {
    this(text, x, y, LEFT, font);
  }

  public Label(final String text, final int x, final int y, final int align) {
    this(text, x, y, align, ConfigKey.FONT_P);
  }

  public Label(final String text, final int x, final int y, final int align, final ConfigKey font) {
    super(text, align);
    final Font ff = (Font) CfgProvider.getInstance().get(font);
    this.setFont(ff);

    final AffineTransform affinetransform = new AffineTransform();
    final FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
    final int width = (int) (ff.getStringBounds(text, frc).getWidth() * 1.5);
    final int height = (int) (ff.getStringBounds(text, frc).getHeight());
    this.setSize(width, height);
    this.setLocation(x, y);
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
