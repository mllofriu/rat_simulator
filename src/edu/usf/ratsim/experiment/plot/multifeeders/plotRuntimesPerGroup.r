# require(XML, quietly = TRUE)
# require(shape, quietly = TRUE)
 require(ggplot2, quietly = TRUE)
# require(grid, quietly = TRUE)
 require(plyr, quietly = TRUE)
# require(parallel, quietly = TRUE)
# require(doParallel, quietly = TRUE)
# require(animation, quietly = TRUE)


plotArrival <- function(pathData){
  #print (pathData)
  summarizedRunTimes <- ddply(pathData, .(group), summarise, sdRT = sd(runtime)/sqrt(length(runtime)), mRT = mean(runtime))
  runtimes.aov <- aov(runtime ~ group, data=pathData)
  print(pathData[1,'trial'])
  print(summary(runtimes.aov))
  print(TukeyHSD(runtimes.aov))
  #print(summarizedRunTimes)
  p <- ggplot(summarizedRunTimes, aes(x=group, y=mRT)) + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT, color=group), width=.3) + geom_point(aes(color=group))
#   print(p)
  ggsave(plot=p,filename=paste("runtimes", ".", pathData[1,'trial'],".pdf", sep=''), width=10, height=10)
}

#summaryFile = 'summary.csv'
#dirs = list.dirs(recursive=FALSE)
files <- list.files('.', 'summary.RData', recursive=T)
runtimeFrames<-lapply(files,function(x) {load(x); summarizedRunTimes})
 
# Adapt from previous format
#runtimeFrames<-lapply(runtimeFrames, function(x) x[-5])
runtimes<-Reduce(function(x,y) merge (x,y, all=T), runtimeFrames)

ddply(runtimes, .(trial), plotArrival)




# policyData <- read.csv(policyFile, sep='\t')
# pathData <- read.csv(pathFile, sep='\t')
# 
# 
# splitPath <- split(pathData, pathData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)



