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
pathData <- read.csv(pathFile, sep='\t')
feederData <- read.csv(feedersFile, sep='\t')
wallData <- read.csv(wallsFile, sep='\t')
policyData <- read.csv(policyFile, sep='\t')
save(pathData, file='position.RData')
save(feederData, file='feeders.RData')
save(wallData, file='walls.RData')
save(policyData, file='policy.RData')
file.remove(pathFile)
file.remove(feedersFile)
file.remove(wallsFile)
file.remove(policyFile)

splitPath <- split(pathData, pathData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
splitFeeders <- split(feederData, feederData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)
splitWalls <- split(wallData, wallData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)

#Save arrival times as a function of repetition number
saveArrivalTime(pathData)


