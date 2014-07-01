#!/bin/bash

rm -r bin
mkdir bin
javac -sourcepath /home/m/mllofriualon/rat_simulator/src/ -d bin -classpath ".:/home/m/mllofriualon//java_libs/commons-io-2.4.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/j3dcore.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/j3dutils.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/vecmath.jar:/home/m/mllofriualon//java_libs/jts-1.8.jar:/home/m/mllofriualon/java_libs/nslj.jar" `find /home/m/mllofriualon/rat_simulator/src/ -iname *.java`
