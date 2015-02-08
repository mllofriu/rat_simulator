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
  summarizedRunTimes <- ddply(pathData, .(Group, repetition), summarise, sdRT = sd(runtime)/sqrt(length(runtime)), mRT = mean(runtime))
#   runtimes.aov <- aov(runtime ~ group + repetition, data=pathData)
  print(pathData[1,'trial'])
#   print(summary(runtimes.aov))
#   print(TukeyHSD(runtimes.aov))
  #print(summarizedRunTimes)
  p <- ggplot(summarizedRunTimes, aes(x=repetition, y=mRT)) 
  p <- p + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT, color=Group), width=.3) 
  p <- p + geom_point(aes(color=Group)) + geom_line(aes(colour=Group))
  p <- p + scale_y_log10()
  p <- p + ylab("Num. of Steps") + xlab("Episode") 
  p <- p + theme(legend.text = element_text(size=16), legend.title = element_text(size=16), text = element_text(size=16)) 
  p <- p + theme(legend.position = c(.3, 1), legend.justification = c(1, 1), legend.background = element_rect(colour = NA, fill = NA))
#   print(p)
  ggsave(plot=p,filename=paste("runtimesPerRepetition", ".", pathData[1,'trial'],".png", sep=''), width=10, height=10)
}

#summaryFile = 'summary.csv'
#dirs = list.dirs(recursive=FALSE)
files <- list.files('.', 'summary.RData', recursive=T)
runtimeFrames<-lapply(files,function(x) {load(x); summarizedRunTimes})
 
# Adapt from previous format
#runtimeFrames<-lapply(runtimeFrames, function(x) x[-5])

runtimes<-Reduce(function(x,y) merge (x,y, all=T), runtimeFrames)
names(runtimes)[2] <- "Group"
ddply(runtimes, .(trial), plotArrival)




# policyData <- read.csv(policyFile, sep='\t')
# pathData <- read.csv(pathFile, sep='\t')
# 
# 
# splitPath <- split(pathData, pathData[c('trial', 'group', 'subject', 'repetition')], drop=TRUE)



