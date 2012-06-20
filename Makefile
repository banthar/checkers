
SOURCES:=$(shell find src/ -iname *.java)
CLASSES:=$(SOURCES:.java=.class)

checkers.jar: $(SOURCES) src/META-INF/MANIFEST.MF
	mkdir -p bin
	javac -d bin $(SOURCES)
	jar cfm checkers.jar src/META-INF/MANIFEST.MF -C bin/ .

