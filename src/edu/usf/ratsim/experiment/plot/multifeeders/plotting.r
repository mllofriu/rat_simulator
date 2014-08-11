require(XML, quietly = TRUE)
require(shape, quietly = TRUE)
require(ggplot2, quietly = TRUE)
require(grid, quietly = TRUE)
require(plyr, quietly = TRUE)
require(parallel, quietly = TRUE)
require(doParallel, quietly = TRUE)
require(animation, quietly = TRUE)

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

mazePlot <- function(mazeFile, wantedFeeder = -1){
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
  
  ns <- getNodeSet(doc, "/world//feeder")
  feeders <- llply(ns, function (f) {
    r <- as.numeric(xmlGetAttr(f, "r"))
    x <- as.numeric(xmlGetAttr(f, "xp"))
    # y coordinate is -z
    y <- - as.numeric(xmlGetAttr(f, "zp"))
    dat <- circleFun(c(x,y),2*r,npoints = 100, 0, 2, TRUE)
    #print (paste(wantedFeeder," ", as.numeric(xmlGetAttr(f, "id"))))
    if (wantedFeeder == as.numeric(xmlGetAttr(f, "id")) ){
      p <- geom_polygon(data=dat, aes(x,y), color="green", fill="green")
     } else {
      p <- geom_polygon(data=dat, aes(x,y), color="grey", fill="grey")
     } 
  })
  
  
  #Return a list with the maze and platform
  list(m,feeders)
  #m
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

wallPlot <- function(wallData,p){

  if (!is.null(wallData)){
    
    p + geom_segment(data=wallData, aes(x,y,xend=xend,yend=yend),  col="black", cex=2)
  } else {
    p
  }
}

plotPathOnMaze <- function (name, pathData, wallData, maze){
  # Get the individual components of the plot
  p <- ggplot()
  p <- p + maze
  p <- ratPathPlot(pathData, p)
  #  p <- ratPathPointsPlot(pathData, p)
  p <- ratStartPointPlot(pathData, p)
  p <- ratEndPointPlot(pathData, p)

  
  p <- wallPlot(wallData, p)

  # Some aesthetic stuff
  p <- mazePlotTheme(p)
  #   list(p, paste("plots/path",name,".jpg", sep=''))
  #   pdf(paste("plots/path",name,".pdf", sep=''))
  #   print(p)
  #   dev.off()
  # Save the plot to an image
  if (name == '')
    print(p)  
  else
    ggsave(plot=p,filename=paste("plots/path/",name,
                                 ".pdf", sep=''), width=10, height=10)
  
  #   saveRDS(p, paste("plots/path/",name,".obj", sep=''))
}

plotPolicyOnMaze <- function(name, pathData, policyData, wallData, maze){  
  #  Take out points outside the circlle
  eps = .01
  policyData <- policyData[(policyData['x']^2 + policyData['y']^2 < .5^2 - eps),] 
  
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
  
  
  ggsave(plot=p,filename=paste("plots/policy/",name,
                               ".pdf", sep=''), width=10, height=10)
  #   saveRDS(p, paste("plots/policy/",name,".obj", sep=''))
}

saveArrivalTime <- function(pathData){
  runTimes <- ddply(pathData, .(trial, group, subject, repetition), summarise, runTime = length(x))
  summarizedRunTimes <- ddply(runTimes, .(trial, group, subject), summarise, runtime = mean(runTime))
  write.csv(summarizedRunTimes, "summary.csv")
  #   print(summarizedRunTimes)
#   p <- ggplot(summarizedRunTimes, aes(x=group, y=mRT)) + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT, color=group), width=.3) + geom_point(aes(color=group))
#   #   print(p)
#   ggsave(plot=p,filename=paste("plots/runtime/",pathData[[1,'trial']],
#                                ".pdf", sep=''), width=10, height=10)
}

incrementalPath <- function(pathData, feederData, wallData)
{
  for (i in seq(2, dim(pathData)[1], dim(pathData)[1]/100)) { 
    maze <- mazePlot(mazeFile, feederData[i,"wantedFeeder"])
    plotPathOnMaze('', pathData[1:i,],wallData, maze)
  }
}



mazeFile <- "maze.xml"
pathFile = 'position.txt'
feedersFile = 'wantedFeeder.txt'
wallsFile = 'walls.txt'
# policyFile = 'policy.txt'

# policyData <- read.csv(policyFile, sep='\t')
pathData <- read.csv(pathFile, sep='\t')
feederData <- read.csv(feedersFile, sep='\t')
wallData <- read.csv(wallsFile, sep='\t')

splitPath <- split(pathData, pathData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
splitFeeders <- split(feederData, feederData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
splitWalls <- split(wallData, wallData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
# splitPol <- split(policyData, policyData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)

# One worker per plot
#registerDoParallel()

#Plot arrival times as a function of repetition number
#ddply(pathData, .(trial), plotArrivalTime)

#Save arrival times as a function of repetition number
saveArrivalTime(pathData)

# Saving image non-parallel:
# invisible(llply(names(splitPol), function(x){
#   # Split data by layers and intention
#   #   splitPolLayer <- split(splitPol[[x]], splitPol[[x]][c('layer','intention')], drop=TRUE)
#   splitPolLayer <- split(splitPol[[x]], splitPol[[x]][c('layer')], drop=TRUE)
#   # Plot different layers with same path data
#   lapply(names(splitPolLayer), function (y) plotPolicyOnMaze(paste(x,y,sep='.'),
#                                                              splitPath[[x]], 
#                                                              splitPolLayer[[y]],
#                                                              maze))
# }, .parallel = TRUE))


maze <- mazePlot(mazeFile)

# # Plot just path

invisible(llply(names(splitPath), function(x) plotPathOnMaze(x,
                                                             splitPath[[x]], splitWalls[[x]], maze), .parallel = FALSE))

# for (i in 2:dim(recallPath)[1]) {
#   + plotPathOnMaze('', recallPath[1:i,], maze)
#   + 
# ani.options(outdir = paste(getwd(),'/plots/path/', sep=''))
# invisible(llply(names(splitPath), function(x) saveMovie(incrementalPath(splitPath[[x]], splitFeeders[[x]], splitWalls[[x]]), interval = .2, movie.name = paste(x,'pathAnimation.gif', sep=''), ani.width=500, ani.height = 500,)))
