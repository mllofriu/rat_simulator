#$ -cwd
#$ -N rat_sim
#$ -l h_rt=08:00:00

export PATH="/usr/lib64/qt-3.3/bin:/opt/sge/bin:/opt/sge/bin/lx-amd64:/bin:/usr/bin:/apps/bin:/usr/lib64/alliance/bin:/home/m/mllofriualon/bin"
export LD_LIBRARY_PATH="/usr/lib64/"
Xvfb :1 -screen 2 1600x1200x16 &
xvfb_pid=$!
DISPLAY=:1.2 java  -Xmx4000m -Djava.library.path=/home/m/mllofriualon/java_libs/ -cp .:/home/m/mllofriualon//java_libs/commons-io-2.4.jar:/home/m/mllofriualon//java_libs/j3dcore.jar:/home/m/mllofriualon//java_libs/j3dutils.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/home/m/mllofriualon//java_libs/vecmath.jar:bin/:src/ edu.usf.ratsim.experiment.multiscalemorris.MSMExperiment
#DISPLAY=:1.2 java  -Xmx4000m -Djava.library.path=/home/m/mllofriualon/java_libs/ -cp .:/home/m/mllofriualon//java_libs/commons-io-2.4.jar:/home/m/mllofriualon//java_libs/j3dcore.jar:/home/m/mllofriualon//java_libs/j3dutils.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/jacl.jar:/home/m/mllofriualon//java_libs/tcljava1.4.1/tcljava.jar:/home/m/mllofriualon//java_libs/vecmath.jar:bin/:src/ edu.usf.ratsim.robot.virtual.VirtualExpUniverse

kill $xvfb_pid
