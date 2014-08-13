#!/bin/bash

logdir=$1

cp src/edu/usf/ratsim/experiment/plot/multifeeders/plotRuntimes.r logs/$logdir/

cd logs/$logdir/
Rscript plotRuntimes.r > plot.out
