import numpy as np

##############################
# Part 1
##############################
# Write your answers to the math problems below. To represent exponents, use the ^ symbol.
# 1.

#   partial derivative of h with respect to x = y+z+2yz^2
#   partial derivative of h with respect to y = x+2xz^2
#   partial derivative of h with respect to z = x+4xyz

# 2.

#   partial derivative of g with respect to u = log v
#   partial derivative of g with respect to v = u/v + 2v*exp(v^2)

# 3.

#   B must be a 4x6 matrix

# 4.

# partial derivative of g with respect to x = -1
# partial derivative of g with respect to y = -2

##############################
# Part 2
##############################


def problem1(A, B, C):
    return np.dot(A, B) - C


def problem2(A):
    return np.ones((A.shape[0], 1))


def problem3(A):
    copy = np.copy(A)
    np.fill_diagonal(copy, 0)
    return copy


def problem4(A, i):
    return np.sum(A[i])


def problem5(A, c, d):
    return np.mean(A[(A >= c) & (A <= d)])


def problem6(A, k):
    eigenValues, eigenVectors = np.linalg.eig(A)
    # get the indicies of largest eigenvalues
    largest_indicies = np.argsort(np.abs(eigenValues))[::-1][:k]
    return eigenVectors[largest_indicies, :].T


def problem7(A, x):
    return np.linalg.solve(A, x)


def problem8(x, k):
    return np.repeat(x[:, np.newaxis], k, axis=1)


def problem9(A):
    A_copy = np.random.permutation(A)
    return A_copy


def problem10(A):
    return np.mean(A, axis=1)
