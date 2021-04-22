package com.alexz.chess.ui.widgets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class TextAreaTest {

  @Test
  void testTextArea() {
    TextArea ta = new TextArea("Test", true, true);
    Assertions.assertEquals("Test", ta.getText());
    Assertions.assertTrue(ta.getLineWrap());
    Assertions.assertTrue(ta.isEditable());
    Assertions.assertEquals(0, ta.getLocation().x);
    Assertions.assertEquals(0, ta.getLocation().y);
    Assertions.assertEquals(0, ta.getRows());
    Assertions.assertEquals(0, ta.getColumns());

    ta = new TextArea("Test1", false, 5, 7, true, 5, 12);
    Assertions.assertEquals("Test1", ta.getText());
    Assertions.assertFalse(ta.getLineWrap());
    Assertions.assertTrue(ta.isEditable());
    Assertions.assertEquals(5, ta.getLocation().x);
    Assertions.assertEquals(7, ta.getLocation().y);
    Assertions.assertEquals(5, ta.getRows());
    Assertions.assertEquals(12, ta.getColumns());

    ta.setSize(200, 350);
    Assertions.assertEquals(200, ta.getSize().width);
    Assertions.assertEquals(350, ta.getSize().height);
    Assertions.assertEquals(200, ta.getPreferredSize().width);
    Assertions.assertEquals(350, ta.getPreferredSize().height);

    ta.setSize(new Dimension(400, 200));
    Assertions.assertEquals(400, ta.getSize().width);
    Assertions.assertEquals(200, ta.getSize().height);
    Assertions.assertEquals(400, ta.getPreferredSize().width);
    Assertions.assertEquals(200, ta.getPreferredSize().height);
  }
}
