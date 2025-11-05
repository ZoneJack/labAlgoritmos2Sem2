#!/bin/bash

LIB_DIR=libPlotRuntime

MAIN_CLASS=PruebaOrdenamientoKt

# Construir el CLASSPATH para incluir el directorio actual (.) y todos los .jar en LIB_DIR
CLASSPATH=".:$(printf %s: $LIB_DIR/*.jar)"

# Ejecutar el programa
kotlin -cp "$CLASSPATH" $MAIN_CLASS "$@"
