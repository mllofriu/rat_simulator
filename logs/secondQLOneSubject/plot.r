require(XML)
require(shape)
require(ggplot2)

plotMaze <- function(mazeFile){
  # Same as xmlParse()
  doc <- xmlParseDoc(mazeFile)
  root <- xmlRoot(doc)
  ns <- getNodeSet(doc, "/world//pool")
  r <- as.numeric(xmlGetAttr(ns[[1]], "r"))
  x <- as.numeric(xmlGetAttr(ns[[1]], "xp"))
  # y coordinate is z
  y <- as.numeric(xmlGetAttr(ns[[1]], "zp"))

  #plot(x=NULL, y=NULL, xlim=c(-1, doc <- xmlParseDoc("../mazes/morrisCircular.xml")

  circleFun <- function(center = c(0,0),diameter = 1, npoints = 100){
    r = diameter / 2
    tt <- seq(0,2*pi,length.out = npoints)
    xx <- center[1] + r * cos(tt)
    yy <- center[2] + r * sin(tt)
    return(data.frame(x = xx, y = yy))
  }
  
  dat <- circleFun(c(x,y),2*r,npoints = 100)
  geom_path(data=dat,aes(x,y))
}

plotPath <- function(logFile, mazeFile){
  maze <- plotMaze(mazeFile)
  logFile
  pathData = read.csv(logFile, sep='\t')
  path <- geom_path(data=pathData, aes(x,y))
  end <- geom_point(data=tail(pathData, n=1),aes(x,y), col="blue", bg="blue", cex=4)
  start <- geom_point(data=head(pathData, n=1), aes(x,y), col="green", bg="green",cex=4)
  p <- ggplot() + path + maze + start + end
  p + theme(axis.line=element_blank(),axis.text.x=element_blank(),
            axis.text.y=element_blank(),axis.ticks=element_blank(),
            axis.title.x=element_blank(),
            axis.title.y=element_blank(),legend.position="none",
            panel.background=element_blank(),panel.border=element_blank(),panel.grid.major=element_blank(),
            panel.grid.minor=element_blank(),plot.background=element_blank())
  
  ggsave(paste(dirname(logFile),"/maze.png", sep=''), width=10, height=10)
}

# Go over all logs and plot paths
#  Get the file list
logFiles <- list.files(pattern="position.txt$", full.names=TRUE, recursive=TRUE)
logFiles
sapply(logFiles, function(x) plotPath(x,"maze.xml"))