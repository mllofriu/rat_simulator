repArrivalTime <- function(repFolder){
  pathFiles <- list.files(path=repFolder,
                          pattern="position.txt$", full.names=TRUE)
  # Get the length of each path
  pathLenghts <- sapply(pathFiles, function(x) system(
    paste("wc -l", x)))
  sum(pathLenghts)
}

# Function to plot the average arrival time of the trial per repetition, among all rats
pathArrivalTime <- function(trialFolder){
  # Find the path files again, but within the folder
  pathFiles <- list.files(path=trialFolder,
                          pattern="position.txt$",
                          full.names=TRUE, recursive=TRUE)
  # Find the repetitions
  reps <- levels(factor(basename(dirname(pathFiles))))
  # Find all path files of each repetition - returns a matrix with cols = 'rep0' .. 'repN'
  repFolders <- sapply(reps, function (x) list.files(
    path=trials[1], pattern=x, include.dirs=TRUE, recursive=TRUE,full.names=TRUE))
  # Get all path files for each repetition
  sapply(repFolders, repArrivalTime)
}

# Search for the position files
pathFiles <- list.files(pattern="position.txt$", full.names=TRUE, recursive=TRUE)
# Using the structure trial/rat/rep/position.txt, extract trial folders
trials <- dirname(dirname(dirname(pathFiles)))
trials <- levels(factor(trials))

pathArrivalTime(trials[1])