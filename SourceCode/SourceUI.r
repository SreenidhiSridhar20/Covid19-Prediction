library(shiny)
library(ggplot2)
library(gridExtra)
library(caTools)
library(e1071)
library(caret)
pageWithSidebar(
headerPanel('Covid k-means clustering and Classification naive bayes'),
sidebarPanel(
numericInput("clusters", "Cluster count", 3,
min = 1, max = 9),
textInput("caption", "Caption:", "Data Summary"),
selectInput("confusionMat", "Choose a dataset:",
choices = (temp_cough$capacity)),
numericInput("obs", "Number of observations to view:", 10)
),
mainPanel(
h3(textOutput("caption")),
verbatimTextOutput("summary"),
tableOutput("view"),
tableOutput("confusionMat"),
plotOutput("plot1")
)
)
