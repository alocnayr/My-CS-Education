import pandas as pd
import calculate_mean_std as cms

data = cms.get_data()
data = cms.label(data)

sample = pd.read_csv(r"sample.csv", encoding="utf-8")

merged = pd.merge(sample, data, on=['Country', 'Year'])

# print(merged[["Country", "true_label", "Prediction"]])

# print(merged)

# print(merged['true_label'][5])


# method for calculating precision
# parameter sample is a csv that contains the ground truth and ML generated predictions
# ground truth column = 'true_label' and predictions column = 'Prediction'
# parameter label is whether the label is "Short", "Normal", or "Long"
def precision(sample, label):
    # precision is a measure of correct positive guesses / total positive guesses
    # need to find true positive and false positive

    truePositiveCount = 0
    falsePositiveCount = 0

    for i in range(len(sample)):
        if sample['true_label'][i] == sample['Prediction'][i] == label:
            truePositiveCount += 1
        if sample['true_label'][i] != label and sample['Prediction'][i] == label:
            falsePositiveCount += 1
    # avoid 0 in the denominator
    if (truePositiveCount + falsePositiveCount) == 0:
        return 0
    return truePositiveCount / (truePositiveCount + falsePositiveCount)


# method for calculating recall
# parameter sample is a csv that contains the ground truth and ML generated predictions
# ground truth column = 'true_label' and predictions column = 'Prediction'
# parameter label is whether the label is "Short", "Normal", or "Long"
def recall(sample, label):
    # recall is a measure of correct positive guesses / all positive labels
    # need to find true positive and false negative
    truePositiveCount = 0
    falseNegativeCount = 0

    # if true label is = to label then it must either be true positive or false negative ->
    # look at prediction, if prediction != label, false negative
    for i in range(len(sample)):
        if sample['true_label'][i] == sample['Prediction'][i] == label:
            truePositiveCount += 1
        if sample['true_label'][i] == label and sample['Prediction'][i] != label:
            falseNegativeCount += 1

    # avoid 0 in the denominator
    if (truePositiveCount + falseNegativeCount) == 0:
        return 0
    return truePositiveCount / (truePositiveCount + falseNegativeCount)


# method for calculating F1
# parameter sample is a csv that contains the ground truth and ML generated predictions
# ground truth column = 'true_label' and predictions column = 'Prediction'
# parameter label is whether the label is "Short", "Normal", or "Long"
def f1(sample, label):
    # f1 is the harmonic mean between precision and recall
    # need to find precision and recall

    # call each function that we already made
    precisionScore = precision(sample, label)
    recallScore = recall(sample, label)

    # avoid 0 in the denominator
    if (precisionScore + recallScore) == 0:
        return 0
    return 2 * (precisionScore * recallScore) / (precisionScore + recallScore)


# print out results
print("Precision for Short: ", precision(merged, "Short"))
print("Recall for Short: ", recall(merged, "Short"))
print("F1 for Short:  ", f1(merged, "Short"))
print("")

print("Precision for Normal: ", precision(merged, "Normal"))
print("Recall for Normal: ", recall(merged, "Normal"))
print("F1 for Normal:  ", f1(merged, "Normal"))
print("")

print("Precision for Long: ", precision(merged, "Long"))
print("Recall for Long: ", recall(merged, "Long"))
print("F1 for Long:  ", f1(merged, "Long"))
print("")