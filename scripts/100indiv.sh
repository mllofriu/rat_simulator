export DISPLAY=:0.0

for i in `seq 0 99`; do
  ./scripts/execPinky.sh "/edu/usf/ratsim/experiment/xml/taxi.xml" Experiment 0 $i
done
