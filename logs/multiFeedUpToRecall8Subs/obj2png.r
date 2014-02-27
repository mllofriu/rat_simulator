require(ggplot2, quietly = TRUE)

plotObj <- function(dir, x) {
  file <- paste(dir, x, sep='')
  p <- readRDS(file)
  bnNoExt <- substr(file, 1, nchar(file) - 4) 
  pngFile <- paste(bnNoExt,'.png',sep='')
  ggsave(plot=p, pngFile, width=10, height=10)
}

args <- commandArgs(trailingOnly = TRUE)
dir <- args[[1]]
filePattern <- args[[2]]

objs <- list.files(path = dir, pattern = filePattern, recursive = TRUE)
lapply(objs, function(x) plotObj(dir, x))
