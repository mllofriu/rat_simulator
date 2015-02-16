require(ggplot2, quietly = TRUE)
require(plyr, quietly = TRUE)

plotArrival <- function(pathData){
  summarizedRunTimes <- ddply(pathData, .(Group, repetition), summarise, sdRT = sd(runtime)/sqrt(length(runtime)), mRT = mean(runtime))
  summarizedRunTimes <- ddply(summarizedRunTimes, .(Group), summarise, repetition=repetition, mRT=mRT, sdRT=sdRT, runmedian = runmed(mRT, 31))
  print(pathData[1,'trial'])
  summarizedRunTimes$repetition <- summarizedRunTimes$repetition + runif(length(summarizedRunTimes$repetition)) * .1
  p <- ggplot(summarizedRunTimes, aes(x=repetition, y=mRT)) 
  p <- p + geom_errorbar(aes(ymin=mRT-sdRT, ymax=mRT+sdRT, color=Group), width=.6, alpha=.5) 
  p <- p + geom_point(aes(color=Group), alpha=.5)
  p <- p + geom_line(aes(y = runmedian, color = Group), size = 1.5)
  p <- p + ylab("Num. of Steps") + xlab("Episode") 
  p <- p + theme(legend.text = element_text(size=16), legend.title = element_text(size=16), text = element_text(size=16)) 
  p <- p + theme(legend.position = c(1, 1), legend.justification = c(1, 1), legend.background = element_rect(colour = NA, fill = NA))
  ggsave(plot=p,filename=paste("runtimesPerRepetition", ".", pathData[1,'trial'],".png", sep=''), width=10, height=10)
}

# Plot runtimes per episode
files <- list.files('.', 'summary.RData', recursive=T)
runtimeFrames<-lapply(files,function(x) {load(x); summarizedRunTimes})
runtimes<-Reduce(function(x,y) merge (x,y, all=T), runtimeFrames)
names(runtimes)[2] <- "Group"
levels(runtimes$Group) <- c("Multi-Scale (3 Layers)", "Small Scale (1 Layer)", "Medium Scale (1 Layer)", "Large Scale (1 Layer)")
ddply(runtimes, .(trial), plotArrival)

# ANOVAs
runtimes.aov <- aov(runtime ~ Group + factor(repetition), data=runtimes[runtimes$repetition>=50,])
anovaLog <- file("anova", open = "wt")
sink(anovaLog)
summary(runtimes.aov)
print(TukeyHSD(runtimes.aov,which = c("Group")))
sink()


 

