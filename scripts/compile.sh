#!/bin/bash

#SBATCH -J ratsim

#RATSIM=/work/rat_simulator/
#JAVA_LIBS=/work/java_libs/

#RATSIM=/home/m/mllofriualon/work/rat_simulator
#JAVA_LIBS=/home/m/mllofriualon/java_libs/

RATSIM=/home/m/mllofriualon/rat_simulator
JAVA_LIBS=/home/m/mllofriualon/java_libs/
JAVAC=/etc/alternatives/java_sdk_1.7.0/bin/javac

rm -r bin
mkdir bin
$JAVAC -sourcepath $RATSIM/src/ -d bin -classpath ".:$JAVA_LIBS/commons-io-2.4.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/j3dcore.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/j3dutils.jar:$JAVA_LIBS/tcljava1.4.1/jacl.jar:$JAVA_LIBS/tcljava1.4.1/tcljava.jar:$JAVA_LIBS/j3d-1_5_2-linux-amd64/vecmath.jar:$JAVA_LIBS/jts-1.8.jar:$JAVA_LIBS/nslj.jar" `find $RATSIM/src/ -iname *.java`
