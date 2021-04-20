package com.alexz.chess.models;

import com.alexz.chess.models.exceptions.ChessException;
import com.alexz.chess.models.exceptions.DrawException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionsTest {

  @Test
  void testExceptions() {
    final String txt = "This is exception";
    try {
      throw new ChessException(txt);
    } catch (final Exception ex) {
      Assertions.assertEquals(txt, ex.getMessage());
    }

    try {
      throw new DrawException();
    } catch (final Exception ex) {
      Assertions.assertEquals("Player decided to call a draw", ex.getMessage());
    }
  }
}
