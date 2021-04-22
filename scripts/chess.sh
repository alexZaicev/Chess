#!/bin/bash

CLASSPATH=.;./lib/*
MAINCLASS=com.alexz.chess.Chess

java -cp "$CLASSPATH" "$MAINCLASS" "$@"
