package com.alexz.chess.ui.widgets;

import com.alexz.chess.models.ConfigKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class LabelTest {

  @Test
  void testLabel() {
    Label lbl = new Label("Test", 5, 5);
    Assertions.assertTrue(lbl.getSize().width > 0);
    Assertions.assertTrue(lbl.getSize().height > 0);
    Assertions.assertEquals(SwingConstants.LEFT, lbl.getHorizontalAlignment());
    Assertions.assertEquals(5, lbl.getLocation().x);
    Assertions.assertEquals(5, lbl.getLocation().y);
    Assertions.assertEquals("Test", lbl.getText());
    Assertions.assertNotNull(lbl.getFont());

    lbl = new Label("Test1", 10, 15, ConfigKey.FONT_H4);
    Assertions.assertTrue(lbl.getSize().width > 0);
    Assertions.assertTrue(lbl.getSize().height > 0);
    Assertions.assertEquals(SwingConstants.LEFT, lbl.getHorizontalAlignment());
    Assertions.assertEquals(10, lbl.getLocation().x);
    Assertions.assertEquals(15, lbl.getLocation().y);
    Assertions.assertEquals("Test1", lbl.getText());
    Assertions.assertNotNull(lbl.getFont());

    lbl = new Label("Test2", 10, 15, SwingConstants.RIGHT);
    Assertions.assertTrue(lbl.getSize().width > 0);
    Assertions.assertTrue(lbl.getSize().height > 0);
    Assertions.assertEquals(SwingConstants.RIGHT, lbl.getHorizontalAlignment());
    Assertions.assertEquals(10, lbl.getLocation().x);
    Assertions.assertEquals(15, lbl.getLocation().y);
    Assertions.assertEquals("Test2", lbl.getText());
    Assertions.assertNotNull(lbl.getFont());

    lbl.setSize(200, 350);
    Assertions.assertEquals(200, lbl.getSize().width);
    Assertions.assertEquals(350, lbl.getSize().height);
    Assertions.assertEquals(200, lbl.getPreferredSize().width);
    Assertions.assertEquals(350, lbl.getPreferredSize().height);

    lbl.setSize(new Dimension(400, 200));
    Assertions.assertEquals(400, lbl.getSize().width);
    Assertions.assertEquals(200, lbl.getSize().height);
    Assertions.assertEquals(400, lbl.getPreferredSize().width);
    Assertions.assertEquals(200, lbl.getPreferredSize().height);
  }
}
