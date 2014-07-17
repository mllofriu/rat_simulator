#!/bin/bash

RATSIM=/work/rat_simulator/
JAVA_LIBS=/work/java_libs/
rm -r bin
mkdir bin
javac -sourcepath $RATSIM/src/ -d bin -classpath ".:$JAVA_LIBS/commons-io-2.4.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/j3dcore.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/j3dutils.jar:$JAVA_LIBS/tcljava1.4.1/jacl.jar:$JAVA_LIBS/tcljava1.4.1/tcljava.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/vecmath.jar:$JAVA_LIBS/jts-1.8.jar:$JAVA_LIBS/nslj.jar" `find $RATSIM/src/ -iname *.java`
