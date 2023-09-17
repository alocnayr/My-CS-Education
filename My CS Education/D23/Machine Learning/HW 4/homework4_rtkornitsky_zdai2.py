from cvxopt import solvers, matrix
import numpy as np
import sklearn.svm
import matplotlib.pyplot as plt


class SVM4342 ():
    def __init__ (self):
        pass

    # Expects each *row* to be an m-dimensional row vector. X should
    # contain n rows, where n is the number of examples.
    # y should correspondingly be an n-vector of labels (-1 or +1).
    def fit (self, X, y):

        # Get the number of rows and columns in X
        examples, features = X.shape

        # G, h, P, q are the parameters of the quadratic program

        # SVM constraints: -y * (w ^ T * x + b) <= -1
        # need to turn into G * x <= h
        # G is a matrix of size (rows, cols + 1)
        # Coefficients of w and b in the constraints equation
        G = np.hstack((-y[:, None] * X, -y[:, None]))
        # h is a vector of size (rows,)
        # The right hand side of the constraints equation
        h = -np.ones(examples)
        # P is a matrix of size (cols + 1, cols + 1)
        # Coefficients of w and b in the objective function
        # (1/2)X^TPX -> make P identity matrix
        P = np.eye(features + 1)
        # Make the last element 0 because don't include b
        P[features, features] = 0
        # q is a vector of size (cols + 1,)
        # Matrix of all zeros because we don't want the linear term
        q = np.zeros(features + 1)

        # Solve -- if the variables above are defined correctly, you can call this as-is:
        sol = solvers.qp(matrix(P, tc='d'), matrix(q, tc='d'), matrix(G, tc='d'), matrix(h, tc='d'))

        # Fetch the learned hyperplane and bias parameters out of sol['x']
        # To avoid any annoying errors due to broadcasting issues, I recommend
        # that you flatten() the w you retrieve from the solution vector so that
        # it becomes a 1-D np.array.

        # Flatten solution vector x
        w_flattened = np.array(sol['x']).flatten()

        # Splice all elements except the last one to get weights
        self.w = w_flattened[:-1]
        # Bias term is the last element of the flattened vector
        self.b = w_flattened[-1]

    # Given a 2-D matrix of examples X, output a vector of predicted class labels
    def predict (self, x):
        # Predictions of test example by dotting feature values and the learned hyperplane parameters.
        # Then adding the bias term
        predictions = x.dot(self.w) + self.b
        # Return the sign of the predictions
        return np.sign(predictions)

def test1 ():
    # Set up test problem
    X = np.array([ [1,1], [2,1], [1,2], [2,3], [1,4], [2,4] ])
    y = np.array([-1,-1,-1,1,1,1])

    # Train your model
    svm4342 = SVM4342()
    svm4342.fit(X, y)
    print(svm4342.w, svm4342.b)

    # Plot the data as well as the optimal separating hyperplane
    xvals = np.arange(-2, 4, 0.01)  # This is a good range of x values
    w = svm4342.w
    b = svm4342.b
    # w[0] * x + w[1] * y + b = 0
    # solve for y
    # w[0] and w[1] are scalars
    yvals = (-b - w[0]*xvals) / w[1]  # calculate based on xvals and the learned SVM parameters
    plt.plot(xvals, yvals)
    plt.scatter(X[y==1,0], X[y==1,1])  # positively labeled points
    plt.scatter(X[y==-1,0], X[y==-1,1])  # negatively labeled points
    plt.show()

    # Compare with sklearn
    svm = sklearn.svm.SVC(kernel='linear', C=1e15)  # 1e15 -- approximate hard-margin
    svm.fit(X, y)
    print(svm.coef_, svm.intercept_)

    acc = np.mean(svm4342.predict(X) == svm.predict(X))
    print("Acc={}".format(acc))

def test2 (seed):
    np.random.seed(seed)

    # Generate random data
    X = np.random.rand(20,3)
    # Generate random labels based on a random "ground-truth" hyperplane
    while True:
        w = np.random.rand(3)
        y = 2*(X.dot(w) > 0.5) - 1
        # Keep generating ground-truth hyperplanes until we find one
        # that results in 2 classes
        if len(np.unique(y)) > 1:
            break

    svm4342 = SVM4342()
    svm4342.fit(X, y)

    # Compare with sklearn
    svm = sklearn.svm.SVC(kernel='linear', C=1e15)  # 1e15 -- approximate hard margin
    svm.fit(X, y)
    diff = np.linalg.norm(svm.coef_.flatten() - svm4342.w) + np.abs(svm.intercept_ - svm4342.b)
    print(diff)

    acc = np.mean(svm4342.predict(X) == svm.predict(X))
    print("Acc={}".format(acc))

    if acc == 1 and diff < 1e-1:
        print("Passed")

if __name__ == "__main__": 
    test1()
    for seed in range(5):
        test2(seed)
