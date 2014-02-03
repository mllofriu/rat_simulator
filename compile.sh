#!/bin/bash

javac -sourcepath src/:deps/classes/ -d bin -classpath ".:/home/m/mllofriualon//java_libs/commons-io-2.4.jar:/home/m/mllofriualon//java_libs/j3dcore.jar:/home/m/mllofriualon//java_libs/j3dutils.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/home/m/mllofriualon//java_libs/vecmath.jar:deps/classes/nslj/src/" `find ./src/ -iname *.java`
