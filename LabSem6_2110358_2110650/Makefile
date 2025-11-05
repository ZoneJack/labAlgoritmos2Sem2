KOTLIN_COMPILER=kotlinc

# Archivos fuente y Libreria
KOTLIN_FILES=Ordenamiento.kt PruebaOrdenamiento.kt

LIB_DIR=libPlotRuntime
CLASSPATH=.:$(shell printf %s: $(LIB_DIR)/*.jar)

all: classes

classes: $(KOTLIN_FILES)
	@echo "Intentando Compilar."
	$(KOTLIN_COMPILER) -cp "$(CLASSPATH)" $(KOTLIN_FILES)
	@echo "Se logro compilar. SE LOGRO COMPILAR!"

# Target para limpiar los archivos generados
clean:
	@echo "Limpiando el desastre"
	rm -rf *.class META-INF *.png
