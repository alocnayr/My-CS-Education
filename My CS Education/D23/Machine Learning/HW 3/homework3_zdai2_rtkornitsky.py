from math import ceil
import numpy as np
import matplotlib.pyplot as plt


# Given training and testing data, learning rate epsilon, batch size, and regularization strength alpha,
# conduct stochastic gradient descent (SGD) to optimize the weight matrix Wtilde (785x10).
# Then return Wtilde.
def SGD(Xtilde_train, Y_train, Xtilde_test, Y_test, epsilon, batchSize, alpha):
    ntilde = 100
    round_num = ceil(Y_train.shape[0] / ntilde)
    epoch = 100
    w = 1e-5 * np.random.randn(Xtilde_train.shape[0], Y_train.shape[1])  # shape(785, 10)
    # # randomize order of example in training set
    # Xtrain_shuffled = Xtilde_train.T    #shape(60000, 785)
    # Xtrain_shuffled = np.random.shuffle(Xtrain_shuffled)
    # keep track of losses for 20 mini-batches
    miniBatchLosses = []
    for e in range(epoch):
        shuffled_round_num = np.arange(0, round_num)
        np.random.shuffle(shuffled_round_num)
        for i in shuffled_round_num:
            X_mini_batch = Xtilde_train[:, ntilde * i:ntilde * (i + 1)]
            # print("X mini batch shape: {}".format(X_mini_batch.shape))
            Y_mini_batch = Y_train[ntilde * i:ntilde * (i + 1), :]
            # print("Y mini batch shape: {}".format(Y_mini_batch.shape))
            gradient = gradientDes(X_mini_batch, Y_mini_batch, w)
            w = w - epsilon * gradient
            if e == epoch - 1:  # last epoch
                miniBatchLosses.append(fCE(X_mini_batch, Y_mini_batch, w, alpha))
    return w, miniBatchLosses[-20:]


def gradientDes(X, Y, w):
    Yhat = SMReg(X, w)
    n = Y.shape[0]
    return np.dot(X, (Yhat - Y)) / n


# predict Yhat with SoftMaxRegression
def SMReg(X_train, w):
    z = np.dot(X_train.T, w)  # shape(100, 10)
    z_exp = np.exp(z)
    z_sum = np.sum(z_exp, axis=1)  # shape(100, 1)
    return z_exp / z_sum[:, None]


def fCE(X, Y, w, alpha):
    Y_hat = SMReg(X, w)
    sum = np.sum(Y * np.log(Y_hat))
    n = Y.shape[0]
    L2 = alpha / (2*n) * np.sum(np.dot(w.T, w))
    return -1 / n * sum + L2


def fPC(X, Y, w):
    yhat = SMReg(X, w)
    Y = np.argmax(Y, axis=1)
    Yhat = np.argmax(yhat, axis=1)
    n = Y.shape[0]
    return np.sum((Yhat == Y)) / n


if __name__ == "__main__":
    # Load data
    trainingImages = np.load("fashion_mnist_train_images.npy") / 255.0  # Normalizing by 255 helps accelerate training
    trainingLabels = np.load("fashion_mnist_train_labels.npy")
    testingImages = np.load("fashion_mnist_test_images.npy") / 255.0  # Normalizing by 255 helps accelerate training
    testingLabels = np.load("fashion_mnist_test_labels.npy")
    # print("Training Image Shape is {}".format(trainingImages.shape))
    # print("Testing Image Shape is {}".format(testingImages.shape))

    # Append a constant 1 term to each example to correspond to the bias terms
    # Training Image Shape after appending 1s is (785, 60000)
    # Testing Image Shape after appending 1s is (785, 10000)
    Xtilde_train = np.hstack((trainingImages, np.ones((trainingImages.shape[0], 1)))).T
    Xtilde_test = np.hstack((testingImages, np.ones((testingImages.shape[0], 1)))).T
    # print("Training Image Shape after appending 1s is {}".format(trainingImagesBiased.shape))
    # print("Testing Image Shape after appending 1s is {}".format(testingImagesBiased.shape))

    # Change from 0-9 labels to "one-hot" binary vector labels. For instance,
    # if the label of some example is 3, then its y should be [ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 ]
    # Training label shape is (60000, 10)
    # Testing label shape is (10000, 10)
    Y_train = np.zeros((trainingLabels.shape[0], trainingLabels.max() + 1))
    Y_train[np.arange(trainingLabels.shape[0]), trainingLabels] = 1
    # print("One hot encoder shape is {}".format(Y_train.shape))
    Y_test = np.zeros((testingLabels.shape[0], testingLabels.max() + 1))
    Y_test[np.arange(testingLabels.shape[0]), testingLabels] = 1

    # Train the model
    Wtilde, lastTwentyMiniBatchLosses = SGD(Xtilde_train, Y_train, Xtilde_test, Y_test, epsilon=0.1, batchSize=100, alpha=.1)

    print("Training loss during the last 20 mini-batches of SGD:")
    for i, trainingLoss in enumerate(lastTwentyMiniBatchLosses):
        print(f"Mini-batch {i + 1}: {trainingLoss}")

    # Compute the prediction and accuracy on the test set
    y_pred = SMReg(Xtilde_test, Wtilde)
    y_pred_labels = np.argmax(y_pred, axis=1)
    y_test_labels = np.argmax(Y_test, axis=1)

    # Print the predictions and labels
    print("yhat and ground truth for 100 samples in test set:")
    for i in range(100):
        print(f"Sample {i+1}: Prediction = {y_pred_labels[i]}, Ground truth = {y_test_labels[i]}")

    pc_accuracy = fPC(Xtilde_test, Y_test, Wtilde)
    print(f"PC accuracy on test set: {pc_accuracy}")

    # Compute the cross-entropy loss on the training and testing sets
    train_loss = fCE(Xtilde_train, Y_train, Wtilde, alpha=.1)
    print(f"Training set cross-entropy loss: {train_loss:.4f}")

    # Compute the cross-entropy loss on the training and testing sets
    test_loss = fCE(Xtilde_test, Y_test, Wtilde, alpha=.1)
    print(f"Testing set cross-entropy loss: {test_loss:.4f}")

        # Visualize the trained weight vectors w(1), . . . , w(10) as images
    fig, axes = plt.subplots(2, 5, figsize=(10, 5))
    for i, ax in enumerate(axes.flat):
        weight_vector = Wtilde[:-1, i]  # Exclude the bias term
        weight_image = weight_vector.reshape(28, 28)
        ax.imshow(weight_image, cmap="gray")
        ax.set_title(f"w({i+1})")
        ax.axis("off")

    plt.show()