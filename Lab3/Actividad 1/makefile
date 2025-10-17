# Makefile para compilar AlgoOrdenamiento.kt

.PHONY: all clean

all: AlgoOrdenamiento

AlgoOrdenamiento: AlgoOrdenamiento.kt
	kotlinc AlgoOrdenamiento.kt -include-runtime -d AlgoOrdenamiento.jar

clean:
	rm -f AlgoOrdenamiento.jar
