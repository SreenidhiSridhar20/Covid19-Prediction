temp_cough$capacity[temp_cough$Temp >= 28 & temp_cough$Temp <=40 & temp_cough$Cough <30] <- "normal"
temp_cough$capacity[temp_cough$Temp >= 47 & temp_cough$Cough >30 ] <- "abnormal"
temp_cough$capacity[temp_cough$Temp >= 91 & temp_cough$Cough >100 ] <- "critical"
#clustering in Kmeans Algorithm
temp_cough_samp <- temp_cough
temp_cough_samp$capacity <-NULL
(kmeans_temp_cough<- kmeans(temp_cough_samp,3))
kmeans_temp_cough$cluster
table_proj<-table(temp_cough$capacity,kmeans_temp_cough$cluster)
plot(temp_cough_samp[c("Temp", "Cough")],
col = kmeans_temp_cough$cluster)
points(kmeans_temp_cough$centers[,c("Temp", "Cough")],
col = 1:3,pch = 8, cex=2)
#classification in naive bayes Algorithm
library(caTools)
library(e1071)
library(caret)
set.seed(NULL)
split = sample.split(temp_cough$capacity, SplitRatio = 0.20)
training_set= subset(temp_cough,split == FALSE)
test_set= subset(temp_cough,split == TRUE)
training_set$capacity<- as.factor(unlist(training_set$capacity))
test_set$capacity <- as.factor(unlist(test_set$capacity ))
test_set[0:2]
model <- naiveBayes(training_set, training_set$capacity)
summary(model)
pred <- predict(model,test_set[0:2],laplace = 1 )
summary(pred)
confusionMatrix(pred, test_set$capacity)
normal<-subset(temp_cough,capacity=="normal")
abnormal<-subset(temp_cough,capacity=="abnormal")
critical<-subset(temp_cough,capacity=="critical")
