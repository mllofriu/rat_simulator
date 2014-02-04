#!/bin/bash

for i in `find . -iname position.txt -exec dirname {} \;`; do
	Rscript plotting.r $i &
done

wait

# copy everything to one folder
mkdir pathPlots
for i in `find . -iname maze.png`; do
	# Erase / and . to get a single filename
	cp $i pathPlots/`echo $i | sed s/[\.\/]//g`.png;
done

mkdir policyPlots
for i in `find . -iname policy.png`; do
	# Erase / and . to get a single filename
	cp $i policyPlots/`echo $i | sed s/[\.\/]//g`.png;
done
