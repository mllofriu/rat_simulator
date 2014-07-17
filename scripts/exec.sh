#$ -cwd
#$ -N rat_sim
#$ -l h_rt=00:20:00,pcpus=1,mpj=200M
#$ -m b
#$ -m e 
#$ -M mllofriualon@mail.usf.edu
# #$ -t 1-576

experiment=$1
logDir=$2
numIndividuals=$3

#sh scripts/compile.sh

Xvfb :1 -screen 2 1600x1200x16 +extension GLX &
xvfb_pid=$!
export DISPLAY=:1.2 
/etc/alternatives/java_sdk_1.7.0/bin/java  -Xmx16000m -Djava.library.path=/home/m/mllofriualon/java_libs/j3d-1_5_2-linux-amd64/ -cp .:/home/m/mllofriualon/java_libs/nslj.jar:/home/m/mllofriualon//java_libs/commons-io-2.4.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/j3dcore.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/j3dutils.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/home/m/mllofriualon//java_libs/jts-1.8.jar:/home/m/mllofriualon//java_libs/j3d-1_5_2-linux-amd64/vecmath.jar:bin/:src/ edu.usf.ratsim.experiment.Experiment $experiment $logDir $numIndividuals # $SGE_TASK_ID

kill $xvfb_pid
