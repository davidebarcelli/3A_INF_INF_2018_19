TARGET=Pannello
CC=gcc
INCLUDE=-I/usr/lib/jvm/java-8-oracle/include/ -I/usr/lib/jvm/java-8-oracle/include/linux/ -I.

${TARGET}: ${TARGET}.java serialLib.c
	javac ${TARGET}.java -h .
	cp ${TARGET}.h serialLib.h
	gcc ${INCLUDE} -c serialLib.c -fPIC -oserialLib.o
	#gcc ${TARGET}.o  -o lib${TARGET}.dylib -shared
	gcc serialLib.o -shared -o libserialLib.so
	make run

serial:serial.c
	gcc -c serial.c -o serial.o
	gcc serial.o -o serial

run:
	java -Djava.library.path=. ${TARGET}

jar:
	jar cmf ${TARGET}.mf ${TARGET}.jar ${TARGET}.class ${TARGET}.java

clean:
	rm -f *.o *.dylib *.h *.class *.jar *.so $(TARGET) $(TARGET).h serial *.log
	
	
	



