import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import GaussianNB
from sklearn.metrics import precision_score, recall_score, f1_score

data = pd.read_csv('student-mat_modified.csv')

# Use numerical features: age, failures, freetime, and absences to predict Performance
# train test spliting
features = data[['age', 'failures', 'freetime', 'absences']]
target = data['Performance']

XTrain, XTest, yTrain, yTest = train_test_split(features, target, test_size=0.2, random_state=42)

# Use Gaussian Naive Bayes model
naiveBayesModel = GaussianNB()
naiveBayesModel.fit(XTrain, yTrain)

# Predict the performance
predictedPerformance = naiveBayesModel.predict(XTest)

# Print the predicted and actual results
# print(np.hstack((predictedPerformance.reshape(-1, 1), target.values.reshape(-1, 1))))

# Precision, recall, f1
precision = precision_score(yTest, predictedPerformance, average='macro')
recall = recall_score(yTest, predictedPerformance, average='macro')
f1 = f1_score(yTest, predictedPerformance, average='macro')

# Print the precision, recall, and f1 score
print("Precision:", precision)
print("Recall:", recall)
print("F1 Score:", f1)