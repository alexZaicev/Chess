package com.alexz.chess.ui.widgets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class ButtonTest {

  @Test
  void testButton() {
    Button btn = new Button();
    Assertions.assertEquals("", btn.getText());
    Assertions.assertEquals(85, btn.getSize().width);
    Assertions.assertEquals(30, btn.getSize().height);
    Assertions.assertEquals(85, btn.getPreferredSize().width);
    Assertions.assertEquals(30, btn.getPreferredSize().height);

    btn = new Button("Test1");
    Assertions.assertEquals("TEST1", btn.getText());
    Assertions.assertEquals(85, btn.getSize().width);
    Assertions.assertEquals(30, btn.getSize().height);
    Assertions.assertEquals(85, btn.getPreferredSize().width);
    Assertions.assertEquals(30, btn.getPreferredSize().height);

    final boolean[] called = new boolean[] {false};
    btn = new Button("Test2", obj -> called[0] = true);
    Assertions.assertEquals("TEST2", btn.getText());
    Assertions.assertEquals(85, btn.getSize().width);
    Assertions.assertEquals(30, btn.getSize().height);
    Assertions.assertEquals(85, btn.getPreferredSize().width);
    Assertions.assertEquals(30, btn.getPreferredSize().height);
    btn.doClick();
    Assertions.assertTrue(called[0]);

    btn.setSize(200, 350);
    Assertions.assertEquals(200, btn.getSize().width);
    Assertions.assertEquals(350, btn.getSize().height);
    Assertions.assertEquals(200, btn.getPreferredSize().width);
    Assertions.assertEquals(350, btn.getPreferredSize().height);

    btn.setSize(new Dimension(400, 200));
    Assertions.assertEquals(400, btn.getSize().width);
    Assertions.assertEquals(200, btn.getSize().height);
    Assertions.assertEquals(400, btn.getPreferredSize().width);
    Assertions.assertEquals(200, btn.getPreferredSize().height);

    final boolean[] called1 = new boolean[] {false};
    btn.setOnClick(obj -> called1[0] = true);
    btn.doClick();
    Assertions.assertTrue(called1[0]);
  }
}
