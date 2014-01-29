require(XML)
require(shape)
require(ggplot2)

# Define the circle; add a point at the center if the 'pie slice' if the shape is to be filled
# taken from stackoverflow http://stackoverflow.com/questions/12794596/how-fill-part-of-a-circle-using-ggplot2
circleFun <- function(center=c(0,0), diameter=1, npoints=100, start=0, end=2, filled=TRUE){
  tt <- seq(start*pi, end*pi, length.out=npoints)
  df <- data.frame(
    x = center[1] + diameter / 2 * cos(tt),
    y = center[2] + diameter / 2 * sin(tt)
  )
  if(filled==TRUE) { #add a point at the center so the whole 'pie slice' is filled
    df <- rbind(df, center)
  }
  return(df)
}

mazePlot <- function(mazeFile){
  # Same as xmlParse()
  doc <- xmlParseDoc(mazeFile)
  root <- xmlRoot(doc)
  ns <- getNodeSet(doc, "/world//pool")
  r <- as.numeric(xmlGetAttr(ns[[1]], "r"))
  x <- as.numeric(xmlGetAttr(ns[[1]], "xp"))
  # y coordinate is z
  y <- as.numeric(xmlGetAttr(ns[[1]], "zp"))
  
#   Unfilled circle
  dat <- circleFun(c(x,y),2*r,npoints = 100, 0, 2, FALSE)
  geom_path(data=dat,aes(x,y))
}

platformPlot <- function(mazeFile){
  # Same as xmlParse()
  doc <- xmlParseDoc(mazeFile)
  root <- xmlRoot(doc)
  ns <- getNodeSet(doc, "/world//food")
  r <- as.numeric(xmlGetAttr(ns[[1]], "r"))
  x <- as.numeric(xmlGetAttr(ns[[1]], "xp"))
  # y coordinate is -z
  y <- - as.numeric(xmlGetAttr(ns[[1]], "zp"))
  
  dat <- circleFun(c(x,y),2*r,npoints = 100, 0, 2, TRUE)
  geom_polygon(data=dat, aes(x,y), color="grey", fill="grey")
}

ratPathPlot <- function(pathData){
  path <- geom_path(data=pathData, aes(x,y))
}

ratStartPointPlot <- function (pathData){
  start <- geom_point(data=head(pathData, n=1), aes(x,y), col="green", bg="green",cex=4)
}
ratEndPointPlot <- function (pathData){
  end <- geom_point(data=tail(pathData, n=1),aes(x,y), col="blue", bg="blue", cex=4)
}

plotPathOnMaze <- function (mazeFile, pathFile){
  # Get the individual components of the plot
  maze <- mazePlot(mazeFile)
  platform <- platformPlot(mazeFile)
  pathData = read.csv(pathFile, sep='\t')
  path <- ratPathPlot(pathData)
  start <- ratStartPointPlot(pathData)
  end <- ratEndPointPlot(pathData)
  # Put the components of the plot togheter
  p <- ggplot() + maze + platform + path  + start + end
  # Some aesthetic stuff
  p + theme(axis.line=element_blank(),axis.text.x=element_blank(),
            axis.text.y=element_blank(),axis.ticks=element_blank(),
            axis.title.x=element_blank(),
            axis.title.y=element_blank(),legend.position="none",
            panel.background=element_blank(),panel.border=element_blank(),panel.grid.major=element_blank(),
            panel.grid.minor=element_blank(),plot.background=element_blank())
  # Save the plot to an image
  ggsave(paste(dirname(pathFile),"/maze.png", sep=''), width=10, height=10)
}

# Go over all logs and plot paths
#  Get the file list
logFiles <- list.files(pattern="position.txt$", full.names=TRUE, recursive=TRUE)
logFiles
sapply(logFiles, function(x) plotPathOnMaze("maze.xml", x))
