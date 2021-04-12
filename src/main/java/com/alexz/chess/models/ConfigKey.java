package com.alexz.chess.models;

public enum ConfigKey {
  TITLE("title"),
  VERSION("version"),
  WINDOW_WIDTH("window.size.width"),
  WINDOW_HEIGHT("window.size.height"),
  FONT_H1("font.h1"),
  FONT_H2("font.h2"),
  FONT_H3("font.h3"),
  FONT_H4("font.h4"),
  FONT_H5("font.h5"),
  FONT_H6("font.h6"),
  FONT_P("font.p"),
  COLOR_TILE_WHITE("color.tile.white"),
  COLOR_TILE_BLACK("color.tile.black"),
  COLOR_PIECE_WHITE("color.piece.white"),
  COLOR_PIECE_BLACK("color.piece.black"),
  DIFFICULTY("difficulty"),
  BOT_PAUSE_MIN("bot.pause.min"),
  BOT_PAUSE_MAX("bot.pause.max"),
  ;

  public final String name;

  ConfigKey(final String name) {
    this.name = name;
  }
}
