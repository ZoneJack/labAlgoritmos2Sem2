KOTLIN_FILES:=$(wildcard *.kt)
KOTLIN_COMPILER:=kotlinc
CLASSPATH:=.

KOTLIN_CLASSES:=$(patsubst %.kt,%Kt.class,$(KOTLIN_FILES))

LIST:=

classes: $(KOTLIN_CLASSES)
	if [ ! -z "$(LIST)" ] ; then \
		$(KOTLIN_COMPILER) -cp $(CLASSPATH) $(LIST) ; \
	fi

$(KOTLIN_CLASSES) : %Kt.class : %.kt
	$(eval LIST+=$$<)

.PHONY: clean

clean :
	rm -rf $(KOTLIN_CLASSES) *~ META-INF
