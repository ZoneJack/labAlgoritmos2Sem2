#!/bin/bash

JAR_FILE="CuentaMenoresALaDerecha.jar"

if [ $# -gt 0 ]; then
	echo "$@" | java -jar $JAR_FILE
else
	java -jar $JAR_FILE
fi
