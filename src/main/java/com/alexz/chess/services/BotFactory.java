package com.alexz.chess.services;

import com.alexz.chess.models.Difficulty;
import com.alexz.chess.models.pieces.PieceColor;
import com.alexz.chess.models.players.BotBase;
import com.alexz.chess.models.players.NaiveBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotFactory {

  private static final Logger _logger = LogManager.getLogger(BotFactory.class);

  public static BotBase getBot(final PieceColor pieceColor, final Difficulty difficulty) {
    switch (difficulty) {
      case EASY:
      case MODERATE:
      case HARD:
        return new NaiveBot(pieceColor);
    }
    _logger.warn("Could not construct bot for an unsupported difficulty [" + difficulty + "]");
    return new NaiveBot(pieceColor);
  }
}
