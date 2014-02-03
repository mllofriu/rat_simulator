#$ -cwd
#$ -N rat_sim
#$ -l h_rt=08:00:00

export PATH="/usr/lib64/qt-3.3/bin:/opt/sge/bin:/opt/sge/bin/lx-amd64:/bin:/usr/bin:/apps/bin:/usr/lib64/alliance/bin:/home/m/mllofriualon/bin"
export LD_LIBRARY_PATH="/usr/lib64/"
Xvfb :1 -screen 2 1600x1200x16 &
xvfb_pid=$!
DISPLAY=:1.2 java  -Xmx4000m -Djava.library.path=. -cp .:deps/commons-io-2.4.jar:deps/j3dcore.jar:deps/j3dutils.jar:deps/tcljava1.4.1/jacl.jar:deps/tcljava1.4.1/tcljava.jar:deps/vecmath.jar:src/ edu.usf.ratsim.experiment.multiscalemorris.MSMExperiment

kill $xvfb_pid
