require(plyr, quietly = TRUE)


saveArrivalTime <- function(pathData){
  runTimes <- ddply(pathData, .(trial, group, subject, repetition), summarise, runTime = length(x))
  summarizedRunTimes <- ddply(runTimes, .(trial, group, repetition), summarise, runtime = mean(runTime))
  #write.csv(summarizedRunTimes, "summary.csv")
  save(summarizedRunTimes, file='summary.RData')
  print (summarizedRunTimes)
  #   print(summarizedRunTimes)
#   p <- ggplot(summarizedRunTimes, aes(x=group, y=mRT)) + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT, color=group), width=.3) + geom_point(aes(color=group))
#   #   print(p)
#   ggsave(plot=p,filename=paste("plots/runtime/",pathData[[1,'trial']],
#                                ".pdf", sep=''), width=10, height=10)
}


mazeFile <- "maze.xml"
pathFile = 'position.txt'
feedersFile = 'wantedFeeder.txt'
wallsFile = 'walls.txt'
policyFile = 'policy.txt'

if (file.exists(pathFile)) {
  pathData <- read.csv(pathFile, sep='\t')
  splitPath <- split(pathData, pathData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
  saveArrivalTime(pathData)
  save(pathData, file='position.RData')
  file.remove(pathFile)
}

if (file.exists(feedersFile)) {
  feederData <- read.csv(feedersFile, sep='\t')
  save(feederData, file='feeders.RData')
  file.remove(feedersFile)
}

if (file.exists(wallsFile)) {
  wallData <- read.csv(wallsFile, sep='\t')
  save(wallData, file='walls.RData')
  file.remove(wallsFile)
}

if (file.exists(policyFile)) {
  policyData <- read.csv(policyFile, sep='\t')
  save(policyData, file='policy.RData')
  file.remove(policyFile)
}


