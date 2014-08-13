#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Illegal number of parameters"
fi

log=$1

./scripts/compile.sh

mkdir logs/$log/

#sbatch scripts/execslurm.sh /edu/usf/ratsim/experiment/xml/multiFeeders.xml $log
sbatch scripts/execslurm.sh /edu/usf/ratsim/experiment/xml/multiFeedersTrAndNoObs.xml $log


