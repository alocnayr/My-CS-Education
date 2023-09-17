import numpy as np
import matplotlib.pyplot as plt  # to show images


# Given an array of faces (N x M x M, where N is number of examples and M is number
# of pixes along each axis),
# return a design matrix Xtilde ((M**2 + 1) x N) whose last row contains all 1s.
def reshapeAndAppend1s(faces):
    numberFaces = faces.shape[0]
    dimensions = faces.shape[1]
    flattenedFaces = faces.reshape(numberFaces, dimensions**2)
    biasTerms = np.ones((numberFaces, 1))
    # make Xtilde be size (N, (M**2)+1) for bias term
    Xtilde = np.concatenate((flattenedFaces, biasTerms), axis=1)
    # convert to column vector
    return Xtilde.T


# Given a vector of weights w, a design matrix Xtilde, and a vector of labels y,
# return the (unregularized) MSE.
def fMSE(wtilde, Xtilde, y):
    yhat = np.dot(Xtilde.T, wtilde)
    mse = np.mean((yhat - y) ** 2)
    return mse


# Given a vector of weights w, a design matrix Xtilde, and a vector of labels y,
# and a regularization strength
# alpha (default value of 0), return the gradient of the (regularized) MSE loss.
def gradfMSE(wtilde, Xtilde, y, alpha=0.):
    numFeatures = Xtilde.shape[1]
    yhat = np.dot(Xtilde.T, wtilde)
    residuals = yhat-y
    error = np.dot(Xtilde, residuals)
    w = np.copy(wtilde)
    # Get rid of bias term
    w[-1] = 0
    gradientOfLossFunction = (1 / numFeatures) * error
    gradientOfRegularizationTerm = (alpha/numFeatures)*w
    gradient = gradientOfLossFunction + gradientOfRegularizationTerm
    return gradient


# Given a design matrix Xtilde and labels y, train a linear regressor for Xtilde
# and y using the analytical solution.
def method1(Xtilde, y):
    xDotxT = np.dot(Xtilde, Xtilde.T)
    return np.linalg.solve(xDotxT, np.dot(Xtilde, y))


# Given a design matrix Xtilde and labels y, train a linear regressor for Xtilde
# and y using gradient descent on fMSE.
def method2(Xtilde, y):
    return gradientDescent(Xtilde, y, alpha=0.)


# Given a design matrix Xtilde and labels y, train a linear regressor for Xtilde
# and y using gradient descent on fMSE
# with regularization.
def method3(Xtilde, y):
    ALPHA = 0.1
    return gradientDescent(Xtilde, y, alpha=ALPHA)


# Performs the gradient descent algorithm and
# outputs weights that minimize the mean squared error residuals
#
# @param Xtilde : Dataset with an additional bias column
# @param y : Labels
# @param alpha : The regularization parameter defaulted to 0
# @return The weights that minimize the mean squared error.
def gradientDescent(Xtilde, y, alpha=0.):
    EPSILON = 3e-3  # Step size aka learning rate
    T = 5000  # Number of gradient descent iterations
    wtilde = np.random.randn(Xtilde.shape[0]) * 0.01  # rand values from norm distribution

    for i in range(T):
        gradient = gradfMSE(wtilde, Xtilde, y, alpha)
        wtilde -= gradient * EPSILON

    return wtilde


if __name__ == "__main__":
    # Load data
    Xtilde_tr = reshapeAndAppend1s(np.load("age_regression_Xtr.npy"))
    ytr = np.load("age_regression_ytr.npy")
    Xtilde_te = reshapeAndAppend1s(np.load("age_regression_Xte.npy"))
    yte = np.load("age_regression_yte.npy")

    w1 = method1(Xtilde_tr, ytr)
    w2 = method2(Xtilde_tr, ytr)
    w3 = method3(Xtilde_tr, ytr)

    # Report fMSE cost using each of the three learned weight vectors
    print("Analytical Training Loss: ", fMSE(w1, Xtilde_tr, ytr))
    print("Analytical Testing Loss: ", fMSE(w1, Xtilde_te, yte))

    print("Gradient Descent Training Loss: ", fMSE(w2, Xtilde_tr, ytr))
    print("Gradient Descent Testing Loss: ", fMSE(w2, Xtilde_te, yte))

    print("Regularization Training Loss: ", fMSE(w3, Xtilde_tr, ytr))
    print("Regularization Testing Loss: ", fMSE(w3, Xtilde_te, yte))

    # Printing weight vectors
    print("w1: {}\nw2: {}\nw3: {}".format(w1, w2, w3))

    # test set rmse
    rmse3 = np.sqrt(fMSE(w3, Xtilde_te, yte))
    print("RMSE Regularization:", rmse3)

    # print egregious errors
    yhat = np.dot(Xtilde_te.T, w3)
    top5_wrong_yhat = np.argsort(abs(yte - yhat))[::-1][:5]
    for i in range(0, 5):
        print("y for top {}: {}".format(i+1, yte[top5_wrong_yhat[i]]))
        print("yhat for top {}: {}".format(i+1, yhat[top5_wrong_yhat[i]]))
        print("error: {}".format(abs(np.dot(Xtilde_te.T, w3)[top5_wrong_yhat[i]]-yte[top5_wrong_yhat[i]])))
        plt.imshow(Xtilde_te[:-1, top5_wrong_yhat[i]].reshape((48, 48)))
        plt.title("Egregious error image # {}".format(i+1))
        plt.show()


    # slice bias term before reshape
    plt.imshow(w1[:-1].reshape((48, 48)))
    plt.title("Analytical")
    plt.show()

    plt.imshow(w2[:-1].reshape((48, 48)))
    plt.title("Gradient Descent")
    plt.show()

    plt.imshow(w3[:-1].reshape((48, 48)))
    plt.title("Regularization")
    plt.show()
