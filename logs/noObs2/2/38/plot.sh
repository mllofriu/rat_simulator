#!/bin/bash

mkdir -p plots/path/
mkdir -p plots/policy/
mkdir -p plots/runtime/

#Rscript -e "pos <- read.csv('position.txt', sep='\t'); save(pos, file='pos.RData')"
#Rscript -e "walls <- read.csv('walls.txt', sep='\t'); save(wals, file='walls.RData')"
#Rscript -e "feeders <- read.csv('wantedFeeder.txt', sep='\t'); save(feeders, file='wantedFeeders.RData')"

time Rscript plotting.r

#Rscript -e "summary <- read.csv('summary.csv'); save(summary, file='summary.RData')"

rm plotting.r