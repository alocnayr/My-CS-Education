import pandas as pd
import numpy as np
from sklearn.linear_model import LinearRegression
from sklearn.metrics import mean_squared_error, r2_score


data = pd.read_csv("Life Expectancy Data.csv")
data = data.dropna(subset=['Alcohol', 'Schooling', 'Adult Mortality', ' BMI ', 'GDP', 'Life expectancy '])

# Filter developed/developing
developed = data[data["Status"] == "Developed"]
developing = data[data["Status"] == "Developing"]

# Between is inclusive
trainingDeveloped = developed[developed["Year"].between(2000, 2013)]
testingDeveloped = developed[developed["Year"].isin([2014, 2015])]

trainingDeveloping = developing[developing["Year"].between(2000, 2013)]
testingDeveloping = developing[developing["Year"].isin([2014, 2015])]

# Developed training and testing
XtrainDeveloped = trainingDeveloped[["Adult Mortality", "Alcohol", " BMI ", "GDP", "Schooling"]].values
YtrainDeveloped = trainingDeveloped["Life expectancy "].values
XtestDeveloped = testingDeveloped[["Adult Mortality", "Alcohol", " BMI ", "GDP", "Schooling"]].values
YtestDeveloped = testingDeveloped["Life expectancy "].values

# Developing training and testing
XtrainDeveloping = trainingDeveloping[["Adult Mortality", "Alcohol", " BMI ", "GDP", "Schooling"]].values
YtrainDeveloping = trainingDeveloping["Life expectancy "].values
XtestDeveloping = testingDeveloping[["Adult Mortality", "Alcohol", " BMI ", "GDP", "Schooling"]].values
YtestDeveloping = testingDeveloping["Life expectancy "].values

# Linear regression on developed training
modelDeveloped = LinearRegression()
modelDeveloped.fit(XtrainDeveloped, YtrainDeveloped)
YpredTrainDeveloped = modelDeveloped.predict(XtrainDeveloped)
YpredTestDeveloped = modelDeveloped.predict(XtestDeveloped)

# RMSE and R2 score on developed training and testing data
rmseTrainDeveloped = np.sqrt(mean_squared_error(YtrainDeveloped, YpredTrainDeveloped))
r2ScoreTrainDeveloped = r2_score(YtrainDeveloped, YpredTrainDeveloped)
rmseTestDeveloped = np.sqrt(mean_squared_error(YtestDeveloped, YpredTestDeveloped))
r2ScoreTestDeveloped = r2_score(YtestDeveloped, YpredTestDeveloped)

# Linear regression on developing countries
modelDeveloping = LinearRegression()
modelDeveloping.fit(XtrainDeveloping, YtrainDeveloping)
yPredTrainDeveloping = modelDeveloping.predict(XtrainDeveloping)
yPredTestDeveloping = modelDeveloping.predict(XtestDeveloping)

# RMSE and R2 score on developing training and testing data
rmseTrainDeveloping = np.sqrt(mean_squared_error(YtrainDeveloping, yPredTrainDeveloping))
r2ScoreTrainDeveloping = r2_score(YtrainDeveloping, yPredTrainDeveloping)
rmseTestDeveloping = np.sqrt(mean_squared_error(YtestDeveloping, yPredTestDeveloping))
r2ScoreTestDeveloping = r2_score(YtestDeveloping, yPredTestDeveloping)

# Print the results
print("Developed Countries:")
print("RMSE (Training):", rmseTrainDeveloped)
print("R2 Score (Training):", r2ScoreTrainDeveloped)
print("RMSE (Testing):", rmseTestDeveloped)
print("R2 Score (Testing):", r2ScoreTestDeveloped)
print("\nDeveloping Countries:")
print("RMSE (Training):", rmseTrainDeveloping)
print("R2 Score (Training):", r2ScoreTrainDeveloping)
print("RMSE (Testing):", rmseTestDeveloping)
print("R2 Score (Testing):", r2ScoreTestDeveloping)

# Print the regression coefficients for Developed countries
print("Coefficients for Developed Countries:", modelDeveloped.coef_)

# Print the regression coefficients for Developing countries
print("Coefficients for Developing Countries:", modelDeveloping.coef_)

