#!/bin/bash

JAR_FILE="RegistrosBellezas.jar"
INPUT_FILE=$1

if [ ! -f "$JAR_FILE" ]; then
	echo "Error, $JAR_FILE no encontrado."
	exit 1
fi

if [ -z "$INPUT_FILE" ]; then
	echo "Error, debe especificar un archivo de entrada."
	exit 1
fi

java -jar $JAR_FILE "$INPUT_FILE"

