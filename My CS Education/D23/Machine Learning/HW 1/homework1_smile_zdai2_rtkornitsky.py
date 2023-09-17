import numpy as np
import matplotlib.patches as patches
import matplotlib.pyplot as plt


# portion correct
def fPC(y, yhat):
    return np.mean(y == yhat)


def measureAccuracyOfPredictors(predictors, X, y):
    # returns a nparray of results for whether each face is smiling
    predicted_results = make_decision(predictors, X, y)
    return fPC(y, predicted_results)


def stepwiseClassification(trainingFaces, trainingLabels):
    # list of tuples
    current_predictors = []
    final_predictors = []
    max_accuracy = 0
    row = trainingFaces.shape[1]
    col = trainingFaces.shape[2]
    # select 6 features
    for j in range(6):
        current_predictors = final_predictors.copy()
        for r1 in range(row):
            for c1 in range(col):
                for r2 in range(row):
                    for c2 in range(col):
                        # ignore case where both indicates same location
                        if (r1 == r2 and c1 == c2):
                            continue
                        # ignore cases already in predictors
                        if (r1, c1, r2, c2) in final_predictors:
                            continue

                        # append this new tuple of indicies to current list of predictors (the crowd)
                        current_predictors.append((r1, c1, r2, c2))
                        # print(current_predictors)
                        # find the accuracy of the current list predictors
                        accuracy = measureAccuracyOfPredictors(current_predictors, trainingFaces, trainingLabels)

                        # if current accuracy is greater than max accuracy, update max_accuracy and keep this list
                        # otherwise pop
                        if accuracy >= max_accuracy:
                            max_accuracy = accuracy
                            if len(final_predictors) > j:
                                final_predictors.pop()
                            final_predictors.append((r1, c1, r2, c2))
                        current_predictors.pop()
        print("Found a pair {}".format(final_predictors))
    return final_predictors, max_accuracy


# makes a decision prediction about whether the image is smiling or not
def make_decision(current_features, X, y):
    list_predictions = np.zeros(y.shape)

    # for each feature (each tuple) in current_features (the list), calculate the result of (r1, c1) > (r2, c2)
    for feature in current_features:
        r1 = feature[0]
        c1 = feature[1]
        r2 = feature[2]
        c2 = feature[3]

        difference = X[:, r1, c1] - X[:, r2, c2]

        difference[difference > 0] = 1
        difference[difference <= 0] = 0

        list_predictions = list_predictions + difference

    predictions = list_predictions / len(current_features)
    predictions[predictions > 0.5] = 1
    predictions[predictions <= 0.5] = 0

    # return an nparray of 0 or 1 based on smile or not
    return np.array(predictions)


def analyze_accuracy(trainingFaces, trainingLabels, testingFaces, testingLabels):
    list_sample = [400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000]
    predictors = []
    for sample_count in list_sample:
        predictors = stepwiseClassification(trainingFaces[:sample_count], trainingLabels[:sample_count])
        training_accuracy = measureAccuracyOfPredictors(predictors[0], trainingFaces[:sample_count],
                                                        trainingLabels[:sample_count])
        testing_accuracy = measureAccuracyOfPredictors(predictors[0], testingFaces, testingLabels)
        print("training accuracy for {} training samples= {}".format(sample_count, training_accuracy))
        print("testing accuracy for {} training samples (over entire test set) = {}".format(sample_count,
                                                                                            testing_accuracy))

    show_face(testingFaces, predictors)


def show_face(testingFaces, predictors):
    # Show an arbitrary test image in grayscale
    im = testingFaces[0, :, :]
    fig, ax = plt.subplots(1)
    ax.imshow(im, cmap='gray')

    # display rectangles in different colors
    colors = ['r', 'b', 'g', 'm', 'c', 'y']

    for i in range(0, 6):
        r1, c1, r2, c2 = predictors[0][i]
        # Show r1,c1
        rect = patches.Rectangle((c1 - 0.5, r1 - 0.5), 1, 1, linewidth=2, edgecolor=colors[i], facecolor='none')
        ax.add_patch(rect)
        # Show r2,c2
        rect = patches.Rectangle((c2 - 0.5, r2 - 0.5), 1, 1, linewidth=2, edgecolor=colors[i], facecolor='none')
        ax.add_patch(rect)

    # Display the merged result
    plt.show()


def loadData(which):
    faces = np.load("{}ingFaces.npy".format(which))
    faces = faces.reshape(-1, 24, 24)  # Reshape from 576 to 24x24
    labels = np.load("{}ingLabels.npy".format(which))
    return faces, labels


if __name__ == "__main__":
    testingFaces, testingLabels = loadData("test")
    trainingFaces, trainingLabels = loadData("train")

# stepwiseClassification_vec = np.vectorize(stepwiseClassification)
# print(stepwiseClassification(trainingFaces[:15], trainingLabels[:15], testingFaces, testingLabels))
analyze_accuracy(trainingFaces, trainingLabels, testingFaces, testingLabels)
# predictors = [[(20, 7, 17, 7), (13, 5, 11, 13), (20, 17, 16, 17), (12, 18, 10, 13), (19, 11, 14, 7), (12, 4, 12, 11)], 1]
# show_face (testingFaces, predictors)