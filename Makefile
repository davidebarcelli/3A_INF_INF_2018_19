#TARGET=serial
TARGET=serialLib
CC=gcc
#INCLUDE=-I/Applications/Xcode.app/Contents/Developer/Platforms/MacOSX.platform/Developer/SDKs/MacOSX.sdk/System/Library/Frameworks/JavaVM.framework/Versions/A/Headers/
INCLUDE=-I/usr/lib/jvm/java-11-oracle/include/ -I/usr/lib/jvm/java-11-oracle/include/linux/ -I.



${TARGET}: ${TARGET}Test.java ${TARGET}.c
	javac ${TARGET}Test.java -h .
	cp ${TARGET}Test.h ${TARGET}.h
	gcc ${INCLUDE} -c ${TARGET}.c -fPIC -o ${TARGET}.o
	#gcc ${TARGET}.o  -o lib${TARGET}.dylib -shared
	gcc ${TARGET}.o -shared -o lib${TARGET}.so
	make run

serial:serial.c
	gcc -c serial.c -o serial.o
	gcc serial.o -o serial

run:
	java -Djava.library.path=. ${TARGET}Test

jar:
	jar cmf ${TARGET}.mf ${TARGET}.jar ${TARGET}Test.class ${TARGET}Test.java

clean:
	rm -f *.o *.dylib *.h *.class *.jar *.so $(TARGET) $(TARGET).h serial *.log
	
	
	



