JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        TempObs.java \
        HumObs.java \
	TempsInterval.java \
	HumsInterval.java \
	Sensor.java \
	Room.java \
	House.java \
        Tema2.java 

default:  classes

classes: $(CLASSES:.java=.class)

run: classes
	java Tema2
clean:
	$(RM) *.class
