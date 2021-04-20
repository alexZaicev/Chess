package com.alexz.chess.services;

import com.alexz.chess.models.ConfigKey;
import com.alexz.chess.models.board.Move;
import com.alexz.chess.models.board.Tile;
import com.alexz.chess.models.exceptions.DrawException;
import com.alexz.chess.models.pieces.IPiece;
import com.alexz.chess.models.players.BotBase;
import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Map;

public class BotWorker extends SwingWorker<Void, Void> {

  private static final Logger _logger = LogManager.getLogger(BotWorker.class);
  private final long minPause;
  private final long maxPause;
  private final BotBase bot;
  private final Map<Tile, IPiece> boardState;
  private Move nextMove;
  private boolean draw;

  public BotWorker(final BotBase bot, final Map<Tile, IPiece> boardState) {
    this.bot = bot;
    this.boardState = boardState;
    this.minPause = CfgProvider.getInstance().getLong(ConfigKey.BOT_PAUSE_MIN);
    this.maxPause = CfgProvider.getInstance().getLong(ConfigKey.BOT_PAUSE_MAX);
    this.draw = false;
  }

  @Override
  protected Void doInBackground() {
    try {
      if (bot == null) {
        throw new IllegalArgumentException("Bot player cannot be NULL");
      }
      if (boardState == null || boardState.isEmpty()) {
        throw new IllegalArgumentException("Invalid board state provided");
      }
      final long start = System.currentTimeMillis();
      this.nextMove = bot.getNextMove(boardState);

      // imitate bot thinking time
      final long diff = System.currentTimeMillis() - start;
      if (diff < minPause) {
        try {
          Thread.sleep(RandomUtils.nextLong(this.minPause, this.maxPause));
        } catch (final InterruptedException ex) {
          //
        }
      }
    } catch (final DrawException ex) {
      this.draw = true;
      _logger.debug(ex.getMessage());
    } catch (final Exception ex) {
      _logger.error(ex);
    }
    return null;
  }

  @Override
  protected void done() {
    if (draw) {
      BoardService.getInstance().callDrawGame();
      return;
    }
    if (nextMove == null) {
      _logger.error("Failed to generate next move");
      return;
    }
    BoardService.getInstance().onMoveAction(this.nextMove, true);
  }
}
