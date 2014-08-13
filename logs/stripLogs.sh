#!/bin/bash

if [ $# -ne 1 ]; 
	then echo "Must specify log directory"
fi

log=$1

maze=`find $log -iname maze.xml | head -n 1`
cp $maze $log
find $log -mindepth 2 -iname maze.xml -exec rm {} \;

config=`find $log -iname experiment.xml | head -n 1`
cp $config $log
find $log -mindepth 2 -iname experiment.xml -exec rm {} \;

plot=`find $log -iname plotting.r | head -n 1`
cp $plot $log
find $log -mindepth 2 -iname plotting.r -exec rm {} \;

find $log -mindepth 2 -iname \*.pdf -exec rm {} \;
find $log -mindepth 2 -iname \*.gif -exec rm {} \;
