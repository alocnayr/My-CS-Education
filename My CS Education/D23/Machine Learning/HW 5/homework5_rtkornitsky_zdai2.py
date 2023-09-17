import numpy as np
import matplotlib.pyplot as plt
import scipy.optimize

IM_WIDTH = 48
NUM_INPUT = IM_WIDTH ** 2
NUM_HIDDEN = 20
NUM_OUTPUT = 1
train_losses = []
test_losses = []


def relu(z):
    return np.maximum(0, z)


def forward_prop(x, y, W1, b1, W2, b2):
    z = W1.dot(x) + b1[:, np.newaxis]
    h = relu(z)
    yhat = np.dot(W2, h) + b2
    loss = compute_loss(y, yhat)
    return loss, x, z, h, yhat


def compute_loss(y, yhat):
    loss = (1 / 2) * np.mean((y - yhat) ** 2)
    return loss


def back_prop(X, y, W1, b1, W2, b2):
    loss, x, z, h, yhat = forward_prop(X, y, W1, b1, W2, b2)
    gradW2 = np.dot((yhat - y), h.T) / yhat.shape[1]
    gradb2 = np.mean(yhat - y, axis=1)
    e1 = np.dot((yhat - y).T, W2)
    e2 = relu_derivative(z.T)
    g = e1 * e2
    b = np.mean(g, axis=0)
    gradb1 = b.flatten()
    gradW1 = np.dot(np.atleast_2d(g.T), x.T) / x.shape[1]
    return gradW1, gradb1, gradW2, gradb2


def relu_derivative(z):
    z = np.where(z <= 0, 0, 1)
    return z


def train(trainX, trainY, W1, b1, W2, b2, testX, testY, epsilon=1e-3, batchSize=128, numEpochs=100):
    num_round = int(trainY.shape[0] / batchSize)

    for e in range(0, numEpochs):
        idx = np.arange(num_round)
        np.random.shuffle(idx)
        for r in idx:
            X_minibatch = trainX[:, batchSize * r:batchSize * (r + 1)]
            Y_minibatch = trainY[batchSize * r:batchSize * (r + 1)]

            gradW1, gradb1, gradW2, gradb2 = back_prop(X_minibatch, Y_minibatch, W1, b1, W2, b2)

            W1 = W1 - epsilon * gradW1
            W2 = W2 - epsilon * gradW2
            b1 = b1 - epsilon * gradb1
            b2 = b2 - epsilon * gradb2

            if e == numEpochs - 1:
                loss_train, _, _, _, _ = forward_prop(trainX, trainY, W1, b1, W2, b2)
                loss_test, _, _, _, _ = forward_prop(testX, testY, W1, b1, W2, b2)
                train_losses.append(loss_train)
                test_losses.append(loss_test)

    return W1, b1, W2, b2,


def show_weight_vectors(W1):
    # Show weight vectors in groups of 5.
    for i in range(NUM_HIDDEN // 5):
        plt.imshow(np.hstack([np.pad(np.reshape(W1[idx, :], [IM_WIDTH, IM_WIDTH]), 2, mode='constant') for idx in
                              range(i * 5, (i + 1) * 5)]), cmap='gray'), plt.show()
    plt.show()


def loadData(which, mu=None):
    images = np.load("age_regression_X{}.npy".format(which)).reshape(-1, 48 ** 2).T
    labels = np.load("age_regression_y{}.npy".format(which))

    if which == "tr":
        mu = np.mean(images)

    # TODO: you may wish to perform data augmentation (e.g., left-right flipping, adding Gaussian noise).

    return images - mu, labels, mu


def checkGradient():
    testW1 = np.load("testW1.npy")  # shape(20, 2304)
    testb1 = np.load("testb1.npy")  # shape(20,)
    testW2 = np.load("testW2.npy")  # shape(1, 20)
    testb2 = np.load("testb2.npy")  # shape(), size 1, val=39.56
    oneSampleX = np.load("oneSampleX.npy")  # shape(2304,)
    oneSampley = np.load("oneSampley.npy")  # shape(), size 1, val=40.0
    gradW1, gradb1, gradW2, gradb2 = back_prop(np.atleast_2d(oneSampleX).T, oneSampley, testW1, testb1, testW2, testb2)
    correctGradW1 = np.load("correctGradW1OnSample.npy")
    correctGradb1 = np.load("correctGradb1OnSample.npy")
    correctGradW2 = np.load("correctGradW2OnSample.npy")
    correctGradb2 = np.load("correctGradb2OnSample.npy")
    # The differences should all be <1e-5
    print(np.sum(np.abs(gradW1 - correctGradW1)))
    print(np.sum(np.abs(gradb1 - correctGradb1)))
    print(np.sum(np.abs(gradW2 - correctGradW2)))
    print(np.sum(np.abs(gradb2 - correctGradb2)))


if __name__ == "__main__":
    # Load data
    if "trainX" not in globals():
        # trainX.shape (2304, 5000)
        # trainY.shape (5000, )
        trainX, trainY, mu = loadData("tr")
        # testX.shape (2304, 2500)
        # testY.shape (2500,)
        testX, testY, _ = loadData("te", mu)

    # Check the gradient value for correctness.
    # Note: the gradients shown below assume 20 hidden units.
    # checkGradient()

    # Initialize weights to reasonable random values
    W1 = 2 * (np.random.random(size=(NUM_HIDDEN, NUM_INPUT)) / NUM_INPUT ** 0.5) - 1. / NUM_INPUT ** 0.5
    b1 = 0.01 * np.ones(NUM_HIDDEN)
    W2 = 2 * (np.random.random(size=(NUM_OUTPUT, NUM_HIDDEN)) / NUM_HIDDEN ** 0.5) - 1. / NUM_HIDDEN ** 0.5
    b2 = np.mean(trainY)

    # Train NN
    W1, b1, W2, b2 = train(trainX, trainY, W1, b1, W2, b2, testX, testY)

    train_losses = train_losses[-20:]
    test_losses = test_losses[-20:]
    for i in range(0, 20):
        print("Training loss during the last {} mini-batch of SGD:{}".format((20 - i), train_losses[i]))
        print("Testing loss during the last {} mini-batch of SGD:{}".format((20 - i), test_losses[i]))

    show_weight_vectors(W1)