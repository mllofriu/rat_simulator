#$ -cwd
#$ -N rat_sim
#$ -l h_rt=00:50:00,pcpus=16,mpj=40G
#$ -m e
#$ -M mllofriualon@mail.usf.edu

sh compile.sh

Xvfb :1 -screen 2 1600x1200x16 &
xvfb_pid=$!
export DISPLAY=:1.2 
java  -Xmx4000m -Djava.library.path=/work/m/mllofriualon/java_libs/ -cp .:/work/m/mllofriualon//java_libs/commons-io-2.4.jar:/work/m/mllofriualon//java_libs/j3dcore.jar:/work/m/mllofriualon//java_libs/j3dutils.jar:/work/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/work/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/work/m/mllofriualon//java_libs/vecmath.jar:bin/:src/ edu.usf.ratsim.experiment.multiscalemorris.MSMExperiment

kill $xvfb_pid
