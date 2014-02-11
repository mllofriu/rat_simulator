#!/bin/bash

mkdir -p plots/path/
mkdir -p plots/policy/
mkdir -p plots/runtime/

time Rscript plotting.r
