#!/bin/bash

#$ -cwd
#$ -N taxi
#$ -m b
#$ -m e
#$ -t 1-100

experiment=$1
logDir=$2
group=$3
individual=$4
#individual=$SGE_TASK_ID

RATSIM=/work/rat_simulator/
JAVA_LIBS=/work/java_libs/


#RATSIM=/home/m/mllofriualon/work/rat_simulator
#JAVA_LIBS=/home/m/mllofriualon/java_libs/

JAVA=java
#JAVA=/etc/alternatives/java_sdk_1.7.0/bin/java
#sh scripts/compile.sh

export PATH=/work/R-3.1.1/bin:$PATH

$JAVA  -Xmx16000m -cp /Users/ludo/Documents/workspace/rat_simulator/target/classes:/Users/ludo/Documents/workspace/rat_simulator/deps/commons-io-2.4.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/jts-1.8.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/protobuf-java-2.5.0.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/jts-1.8.0.zip:/Users/ludo/Documents/workspace/nslj/bin:/Users/ludo/Documents/workspace/nslj/deps/jacl.jar:/Users/ludo/Documents/workspace/nslj/deps/tcljava.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/vecmath.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/jogl-all.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/joal.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/gluegen-rt.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/gluegen.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/j3dcore.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/j3dutils.jar:/Users/ludo/Documents/workspace/rat_simulator/deps/j3dport/jocl.jar:/Users/ludo/Documents/workspace/rat_simulator/src edu.usf.ratsim.experiment.Experiment $experiment $logDir $group $individual

