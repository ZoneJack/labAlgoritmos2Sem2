KOTLINC = kotlinc
JAR_NAME = RegistrosBellezas.jar
SOURCE_FILE = RegistrosBellezas.kt

all: $(JAR_NAME)

$(JAR_NAME) : $(SOURCE_FILE)
	@echo "Intentando compilar: $(SOURCE_FILE), por favor espere. /\ . /\ . /\ "
	$(KOTLINC) $(SOURCE_FILE) -include-runtime -d $(JAR_NAME)
	@echo "! Compilacion Exitosa ! $(JAR_NAME) generado."
	
clean: 
	@echo "Limpiando el desastre."
	rm -f $(JAR_NAME)
	rm -f *Kt.class
