#!/bin/bash

mkdir pathPlots
for i in `find . -iname maze.png`; do 
	# Erase / and . to get a single filename
	cp $i pathPlots/`echo $i | sed s/[\.\/]//g`.png; 
done
