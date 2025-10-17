#!/bin/bash

# Script para ejecutar CuentaMenoresALaDerecha

# Verificar si el archivo JAR existe
if [ ! -f "CuentaMenoresALaDerecha.jar" ]; then
  echo "Error: El archivo CuentaMenoresALaDerecha.jar no existe."
  echo "Ejecute 'make' primero"
  exit 1
fi
java -jar CuentaMenoresALaDerecha.jar "$@"