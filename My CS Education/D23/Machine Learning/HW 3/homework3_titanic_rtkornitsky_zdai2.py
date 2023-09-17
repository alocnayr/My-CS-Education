import pandas as pd
import numpy as np

from homework3_zdai2_rtkornitsky import SGD, SMReg


# One hot encoder that creates a 2D array with num_classes rows and columns
# Then uses the array of indices to set the corresponding row to 1
def oneHotEncoder(array, num_classes):
    yTrain = np.zeros((array.shape[0], num_classes))
    yTrain[np.arange(array.shape[0]), array] = 1
    return yTrain

if __name__ == "__main__":
    # Load training data
    trainingData = pd.read_csv("train.csv")
    yTrain = trainingData.Survived.to_numpy()
    # shape (891, 2)
    yTrainOneHotEncoder = oneHotEncoder(yTrain, 2)
    sexTrainingData = trainingData.Sex.map({"male":0, "female":1}).to_numpy()
    # Convert Pclass to 0-indexed
    pclassTrainingData = trainingData.Pclass.to_numpy() - 1
    sibspTrainingData = trainingData.SibSp.to_numpy()

    # Pclass and SibSp conversion to one-hot encoding
    # 3 classes for Pclass with ticket values 1, 2, 3
    pclassTrainingDataOneHotEncoder = oneHotEncoder(pclassTrainingData, 3)
    # Number of siblings and spouses on board, count number of unique values for classes
    sibspTrainingDataOneHotEncoding = oneHotEncoder(sibspTrainingData, np.max(sibspTrainingData) + 1)

    # Concatenate features
    # shape (13, 891)
    xTrainingData = np.hstack((sexTrainingData.reshape(-1, 1), pclassTrainingDataOneHotEncoder, sibspTrainingDataOneHotEncoding)).T
    # print(X_train.shape)

    # Load testing data
    testingDataSet = pd.read_csv("test.csv")
    passengerIDs = testingDataSet.PassengerId.to_numpy()
    sexTestingSet = testingDataSet.Sex.map({"male": 0, "female": 1}).to_numpy()
    pclassTestingSet = testingDataSet.Pclass.to_numpy() - 1
    sibspTestingSet = testingDataSet.SibSp.to_numpy()

    # Pclass and SibSp conversion to one-hot encoding
    pclassTestingSetOneHotEncoder = oneHotEncoder(pclassTestingSet, 3)
    sibspTestingSetOneHotEncoder = oneHotEncoder(sibspTestingSet, np.max(sibspTestingSet) + 1)

    # Concatenation of features
    # shape (13, 418)
    xTest = np.hstack((sexTestingSet.reshape(-1, 1), pclassTestingSetOneHotEncoder, sibspTestingSetOneHotEncoder)).T
    # print(X_test.shape)

    # Train SGD model
    W = SGD(xTrainingData, yTrainOneHotEncoder, None, None, epsilon=0.1, batchSize=100, alpha=.1)[0]
    print(W.shape)

    # Predictions
    yTestPredictions = SMReg(xTest, W)
    yTestPredictionLabels = np.argmax(yTestPredictions, axis=1)

    # Output to CSV
    with open("gender_submission.csv", "w") as f:
        f.write("PassengerId,Survived\n")
        for passengerID, predictionLabel in zip(passengerIDs, yTestPredictionLabels):
            f.write(f"{passengerID},{predictionLabel}\n")

    print("Predictions written in gender_submission.csv")