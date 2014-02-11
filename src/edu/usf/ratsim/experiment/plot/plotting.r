require(XML, quietly = TRUE)
require(shape, quietly = TRUE)
require(ggplot2, quietly = TRUE)
require(grid, quietly = TRUE)
require(plyr, quietly = TRUE)
require(parallel, quietly = TRUE)
require(doParallel, quietly = TRUE)

mazePlotTheme <- function(p){
  p + theme(axis.line=element_blank(),axis.text.x=element_blank(),
        axis.text.y=element_blank(),axis.ticks=element_blank(),
        axis.title.x=element_blank(),
        axis.title.y=element_blank(),legend.position="none",
        panel.background=element_blank(),panel.border=element_blank(),panel.grid.major=element_blank(),
        panel.grid.minor=element_blank(),plot.background=element_blank())
}

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
  m <- geom_path(data=dat,aes(x,y))
  
  ns <- getNodeSet(doc, "/world//food")
  r <- as.numeric(xmlGetAttr(ns[[1]], "r"))
  x <- as.numeric(xmlGetAttr(ns[[1]], "xp"))
  # y coordinate is -z
  y <- - as.numeric(xmlGetAttr(ns[[1]], "zp"))
  dat <- circleFun(c(x,y),2*r,npoints = 100, 0, 2, TRUE)
  p <- geom_polygon(data=dat, aes(x,y), color="grey", fill="grey")
  
  # Return a list with the maze and platform
  list(m,p)
}

ratPathPlot <- function(pathData, p){
  pathSegs <- pathData[1:nrow(pathData)-1,]
  # Add two new columns with shifted data
  pathSegs[c('nX', 'nY')] <- pathData[-1,c('x','y')]
  p + geom_segment(data=pathSegs[c('x','y','nX','nY','random')], aes(x,y,xend=nX,yend=nY,color = random)) + scale_color_manual(values=c(true="red", false="blue")) 
}

ratPathPointsPlot <- function(pathData, p){
  p + geom_point(data=pathData, aes(x,y),  col="green", bg="red",cex=1)
}

ratStartPointPlot <- function (pathData, p){
  p + geom_point(data=head(pathData, n=1), aes(x,y), col="green", bg="green",cex=4)
}

ratEndPointPlot <- function (pathData, p){
  p + geom_point(data=tail(pathData, n=1),aes(x,y), col="blue", bg="blue", cex=4)
}

policyArrowsPlot <- function(policyData, p){
  # Compute deltax and y
  policyDataNonNA <- policyData[!is.na(policyData['angle']),]
  
  if (nrow(policyDataNonNA) > 0){
    segLen = .0001
    policyDataNonNA[, 'deltax'] <- cos(policyDataNonNA['angle']) * segLen
    policyDataNonNA[, 'deltay'] <- sin(policyDataNonNA['angle']) * segLen
    p + geom_segment(data=policyDataNonNA, aes(x = x, y = y, xend = x + deltax, yend = y + deltay), arrow = arrow(length = unit(0.1,"cm")))
  } else {
    p
  }
}

policyDotsPlot <- function(policyData, p){
  policyDataNA <- policyData[is.na(policyData['angle']),]
  if (nrow(policyDataNA) > 0){
    p + geom_point(data=policyDataNA,aes(x,y), col="black", bg="black", cex=2)
  } else {
    p
  }
}

plotPathOnMaze <- function (name, pathData, mazeFile){
  # Get the individual components of the plot
  p <- ggplot()
  p <- p + maze
  p <- ratPathPlot(pathData, p)
  #  p <- ratPathPointsPlot(pathData, p)
  p <- ratStartPointPlot(pathData, p)
  p <- ratEndPointPlot(pathData, p)
  # Some aesthetic stuff
  p <- mazePlotTheme(p)
#   list(p, paste("plots/path",name,".jpg", sep=''))
#   pdf(paste("plots/path",name,".pdf", sep=''))
#   print(p)
#   dev.off()
  # Save the plot to an image
#   ggsave(plot=p,filename=paste("plots/path/",name,
#                                ".pdf", sep=''), width=10, height=10)
  saveRDS(p, paste("plots/path/",name,".obj", sep=''))
}

plotPolicyOnMaze <- function(name, pathData, policyData, maze){  
  #  Take out points outside the circle
  eps = .01
#   policyData <- policyData[(policyData['x']^2 + policyData['y']^2 < .5^2 - eps),] 
  
  p <- ggplot()
  p <- p + maze
  p <- ratPathPlot(pathData, p)
  #  p <- ratPathPointsPlot(pathData, p)
p <- ratStartPointPlot(pathData, p)
p <- ratEndPointPlot(pathData, p)
p <- policyArrowsPlot(policyData, p)
p <- policyDotsPlot(policyData, p)

  # Some aesthetic stuff
p <- mazePlotTheme(p)
  # Save the plot to an image
# print(system.time(ggsave(plot=p,filename=paste("plots/policy/",name,
#                                ".pdf", sep=''), width=10, height=10)))
saveRDS(p, paste("plots/policy/",name,".obj", sep=''))
}

plotArrivalTime <- function(pathData){
  runTimes <- ddply(pathData, .(subject, repetition), summarise, runTime = length(x))
  summarizedRunTimes <- ddply(runTimes, .(repetition), summarise, sdRT = sd(runTime)/sqrt(length(runTime)), mRT = mean(runTime))
  print(summarizedRunTimes)
  p <- ggplot(summarizedRunTimes, aes(x=repetition, y=mRT)) + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT), width=.3) + geom_point()
  ggsave(plot=p,filename=paste("plots/runtime/",pathData[[1,'trial']],
                             ".pdf", sep=''), width=10, height=10)
}

mazeFile <- "maze.xml"

maze <- mazePlot(mazeFile)

pathFile = 'position.txt'
policyFile = 'policy.txt'

policyData <- read.csv(policyFile, sep='\t')
pathData <- read.csv(pathFile, sep='\t')

splitPath <- split(pathData, pathData[c('trial', 'subject', 'repetition')], drop=TRUE)
splitPol <- split(policyData, policyData[c('trial', 'subject', 'repetition')], drop=TRUE)

# One worker per plot
registerDoParallel()

# Plot arrival times as a function of repetition number
# ddply(pathData, .(trial), plotArrivalTime)

# Saving image non-parallel:
#user  system elapsed
#73.457   2.219  95.331
# Saving image parallel:
#   user  system elapsed
# 62.713   1.106  95.903
# Saving without circle constraint:
# user  system elapsed
# 77.764   1.355  82.097
# Saving objs in parallel:
# user  system elapsed
# 17.039   0.323  20.475
system.time(llply(names(splitPol), function(x){
  # Split data by layers
  splitPolLayer <- split(splitPol[[x]], splitPol[[x]][c('layer')], drop=TRUE)
  # Plot different layers with same path data
  lapply(names(splitPolLayer), function (y) plotPolicyOnMaze(paste(x,y,sep='.'),
                                                             splitPath[[x]], 
                                                           splitPolLayer[[y]],
                                                           maze))
}, .parallel = TRUE))

# Plot just path
llply(names(splitPath), function(x) plotPathOnMaze(x,
            splitPath[[x]], maze), .parallel = TRUE)


