import pandas as pd
import numpy as np
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.linear_model import LinearRegression


def calculate_Afghanistan():

    data = pd.read_csv("Life Expectancy Data.csv")
    afghanistan = data[data["Country"] == "Afghanistan"]

    # Between is inclusive
    trainingData = afghanistan[afghanistan["Year"].between(2000, 2013)]
    testingData = afghanistan[afghanistan["Year"].isin([2014, 2015])]

    # Training
    Xtrain = trainingData[["GDP"]].values
    yTrain = trainingData["Life expectancy "].values

    # Testing
    Xtest = testingData[["GDP"]].values
    Ytest = testingData["Life expectancy "].values

    # Linear, quadratic, cubic, and quartic functions
    degree = [1, 2, 3, 4]
    rmseTrain = []
    r2Train = []
    rmseTest = []
    r2Test = []
    for d in degree:
        # Polynomial features to power of d
        X_poly_train = np.power(Xtrain, d)
        X_poly_test = np.power(Xtest, d)

        # Linear regression
        model = LinearRegression()
        model.fit(X_poly_train, yTrain)

        # Predict training
        yPredTrain = model.predict(X_poly_train)

        # Predict testing
        yPredTest = model.predict(X_poly_test)
        print("Life expectancy prediction for 2014 and 2015 for degree", d, yPredTest)

        # RMSE and R2 training
        rmseTrain.append(np.sqrt(mean_squared_error(yTrain, yPredTrain)))
        r2Train.append(r2_score(yTrain, yPredTrain))

        # RMSE and R2 testing
        rmseTest.append(np.sqrt(mean_squared_error(Ytest, yPredTest)))
        r2Test.append(r2_score(Ytest, yPredTest))

    print("")
    print("RMSE training data:", rmseTrain)
    print("R2 training data:", r2Train)
    print("RMSE testing data:", rmseTest)
    print("R2 testing data:", r2Test)


print(calculate_Afghanistan())
