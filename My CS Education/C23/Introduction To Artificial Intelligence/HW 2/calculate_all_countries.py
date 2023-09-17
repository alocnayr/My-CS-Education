import pandas as pd
import numpy as np
from sklearn.metrics import mean_squared_error, r2_score
from sklearn.linear_model import LinearRegression


def calculate_all_countries_le():

    data = pd.read_csv("Life Expectancy Data.csv")
    data = data.dropna(subset=['GDP', 'Life expectancy '])

    # for outputting purposes
    rmse_train_developed_degree1 = []
    rmse_train_developed_degree2 = []
    rmse_train_developed_degree3 = []
    rmse_train_developed_degree4 = []
    rmse_test_developed_degree1 = []
    rmse_test_developed_degree2 = []
    rmse_test_developed_degree3 = []
    rmse_test_developed_degree4 = []
    r2_train_developed_degree1 = []
    r2_train_developed_degree2 = []
    r2_train_developed_degree3 = []
    r2_train_developed_degree4 = []
    r2_test_developed_degree1 = []
    r2_test_developed_degree2 = []
    r2_test_developed_degree3 = []
    r2_test_developed_degree4 = []
    avg_rmse_train_developed_degree1 = []
    avg_rmse_train_developed_degree2 = []
    avg_rmse_train_developed_degree3 = []
    avg_rmse_train_developed_degree4 = []
    avg_rmse_test_developed_degree1 = []
    avg_rmse_test_developed_degree2 = []
    avg_rmse_test_developed_degree3 = []
    avg_rmse_test_developed_degree4 = []
    avg_r2_train_developed_degree1 = []
    avg_r2_train_developed_degree2 = []
    avg_r2_train_developed_degree3 = []
    avg_r2_train_developed_degree4 = []
    avg_r2_test_developed_degree1 = []
    avg_r2_test_developed_degree2 = []
    avg_r2_test_developed_degree3 = []
    avg_r2_test_developed_degree4 = []

    rmse_train_developing_degree1 = []
    rmse_train_developing_degree2 = []
    rmse_train_developing_degree3 = []
    rmse_train_developing_degree4 = []
    rmse_test_developing_degree1 = []
    rmse_test_developing_degree2 = []
    rmse_test_developing_degree3 = []
    rmse_test_developing_degree4 = []
    r2_train_developing_degree1 = []
    r2_train_developing_degree2 = []
    r2_train_developing_degree3 = []
    r2_train_developing_degree4 = []
    r2_test_developing_degree1 = []
    r2_test_developing_degree2 = []
    r2_test_developing_degree3 = []
    r2_test_developing_degree4 = []
    avg_rmse_train_developing_degree1 = []
    avg_rmse_train_developing_degree2 = []
    avg_rmse_train_developing_degree3 = []
    avg_rmse_train_developing_degree4 = []
    avg_rmse_test_developing_degree1 = []
    avg_rmse_test_developing_degree2 = []
    avg_rmse_test_developing_degree3 = []
    avg_rmse_test_developing_degree4 = []
    avg_r2_train_developing_degree1 = []
    avg_r2_train_developing_degree2 = []
    avg_r2_train_developing_degree3 = []
    avg_r2_train_developing_degree4 = []
    avg_r2_test_developing_degree1 = []
    avg_r2_test_developing_degree2 = []
    avg_r2_test_developing_degree3 = []
    avg_r2_test_developing_degree4 = []

    # Fit the linear, quadratic, cubic, and quartic functions for each country in the dataset
    degree = [1, 2, 3, 4]
    for country in data["Country"].unique():

        country_data = data[data["Country"] == country]

        # Between is inclusive
        training_data = country_data[country_data["Year"].between(2000, 2013)]
        testing_data = country_data[country_data["Year"].isin([2014, 2015])]

        # Values for training
        X_train = training_data[["GDP"]].values
        y_train = training_data["Life expectancy "].values

        # Values for testing
        X_test = testing_data[["GDP"]].values
        y_test = testing_data["Life expectancy "].values

        for d in degree:
            # Make sure you have two datapoints
            if len(X_train) < 2 or len(X_test) < 2:
                continue

            # Poly features raised to degree d
            X_poly_train = np.power(X_train, d)
            X_poly_test = np.power(X_test, d)

            # Linear regression
            model = LinearRegression()
            model.fit(X_poly_train, y_train)

            # Predict training data
            y_pred_train = model.predict(X_poly_train)

            # Predict testing data
            y_pred_test = model.predict(X_poly_test)
            # print("Predicted life expectancy for 2014 and 2015 for country", country, "degree", d, y_pred_test)

            # RMSE and R2 score on the training data
            rmse_train = np.sqrt(mean_squared_error(y_train, y_pred_train))
            r2_train = r2_score(y_train, y_pred_train)

            # RMSE and R2 score on the testing data
            rmse_test = np.sqrt(mean_squared_error(y_test, y_pred_test))
            r2_test = r2_score(y_test, y_pred_test)

            # filter by developed/developing
            if country_data["Status"].iloc[0] == "Developed":

                if d == 1:
                    rmse_train_developed_degree1.append(rmse_train)
                    rmse_test_developed_degree1.append(rmse_test)
                    r2_train_developed_degree1.append(r2_train)
                    r2_test_developed_degree1.append(r2_test)
                if d == 2:
                    rmse_train_developed_degree2.append(rmse_train)
                    rmse_test_developed_degree2.append(rmse_test)
                    r2_train_developed_degree2.append(r2_train)
                    r2_test_developed_degree2.append(r2_test)
                if d == 3:
                    rmse_train_developed_degree3.append(rmse_train)
                    rmse_test_developed_degree3.append(rmse_test)
                    r2_train_developed_degree3.append(r2_train)
                    r2_test_developed_degree3.append(r2_test)
                if d == 4:
                    rmse_train_developed_degree4.append(rmse_train)
                    rmse_test_developed_degree4.append(rmse_test)
                    r2_train_developed_degree4.append(r2_train)
                    r2_test_developed_degree4.append(r2_test)
            else:

                if d == 1:
                    rmse_train_developing_degree1.append(rmse_train)
                    rmse_test_developing_degree1.append(rmse_test)
                    r2_train_developing_degree1.append(r2_train)
                    r2_test_developing_degree1.append(r2_test)
                if d == 2:
                    rmse_train_developing_degree2.append(rmse_train)
                    rmse_test_developing_degree2.append(rmse_test)
                    r2_train_developing_degree2.append(r2_train)
                    r2_test_developing_degree2.append(r2_test)
                if d == 3:
                    rmse_train_developing_degree3.append(rmse_train)
                    rmse_test_developing_degree3.append(rmse_test)
                    r2_train_developing_degree3.append(r2_train)
                    r2_test_developing_degree3.append(r2_test)
                if d == 4:
                    rmse_train_developing_degree4.append(rmse_train)
                    rmse_test_developing_degree4.append(rmse_test)
                    r2_train_developing_degree4.append(r2_train)
                    r2_test_developing_degree4.append(r2_test)

    if len(rmse_train_developed_degree1) != 0:
        avg_rmse_train_developed_degree1 = np.mean(rmse_train_developed_degree1)
    if len(rmse_train_developed_degree2) != 0:
        avg_rmse_train_developed_degree2 = np.mean(rmse_train_developed_degree2)
    if len(rmse_train_developed_degree3) != 0:
        avg_rmse_train_developed_degree3 = np.mean(rmse_train_developed_degree3)
    if len(rmse_train_developed_degree4) != 0:
        avg_rmse_train_developed_degree4 = np.mean(rmse_train_developed_degree4)

    if len(rmse_test_developed_degree1) != 0:
        avg_rmse_test_developed_degree1 = np.mean(rmse_test_developed_degree1)
    if len(rmse_test_developed_degree2) != 0:
        avg_rmse_test_developed_degree2 = np.mean(rmse_test_developed_degree2)
    if len(rmse_test_developed_degree3) != 0:
        avg_rmse_test_developed_degree3 = np.mean(rmse_test_developed_degree3)
    if len(rmse_test_developed_degree4) != 0:
        avg_rmse_test_developed_degree4 = np.mean(rmse_test_developed_degree4)

    if len(r2_train_developed_degree1) != 0:
        avg_r2_train_developed_degree1 = np.mean(r2_train_developed_degree1)
    if len(r2_train_developed_degree2) != 0:
        avg_r2_train_developed_degree2 = np.mean(r2_train_developed_degree2)
    if len(r2_train_developed_degree3) != 0:
        avg_r2_train_developed_degree3 = np.mean(r2_train_developed_degree3)
    if len(r2_train_developed_degree4) != 0:
        avg_r2_train_developed_degree4 = np.mean(r2_train_developed_degree4)

    if len(r2_test_developed_degree1) != 0:
        avg_r2_test_developed_degree1 = np.mean(r2_test_developed_degree1)
    if len(r2_test_developed_degree2) != 0:
        avg_r2_test_developed_degree2 = np.mean(r2_test_developed_degree2)
    if len(r2_test_developed_degree3) != 0:
        avg_r2_test_developed_degree3 = np.mean(r2_test_developed_degree3)
    if len(r2_test_developed_degree4) != 0:
        avg_r2_test_developed_degree4 = np.mean(r2_test_developed_degree4)


    if len(rmse_train_developing_degree1) != 0:
        avg_rmse_train_developing_degree1 = np.mean(rmse_train_developing_degree1)
    if len(rmse_train_developing_degree2) != 0:
        avg_rmse_train_developing_degree2 = np.mean(rmse_train_developing_degree2)
    if len(rmse_train_developing_degree3) != 0:
        avg_rmse_train_developing_degree3 = np.mean(rmse_train_developing_degree3)
    if len(rmse_train_developing_degree4) != 0:
        avg_rmse_train_developing_degree4 = np.mean(rmse_train_developing_degree4)

    if len(rmse_test_developing_degree1) != 0:
        avg_rmse_test_developing_degree1 = np.mean(rmse_test_developing_degree1)
    if len(rmse_test_developing_degree2) != 0:
        avg_rmse_test_developing_degree2 = np.mean(rmse_test_developing_degree2)
    if len(rmse_test_developing_degree3) != 0:
        avg_rmse_test_developing_degree3 = np.mean(rmse_test_developing_degree3)
    if len(rmse_test_developing_degree4) != 0:
        avg_rmse_test_developing_degree4 = np.mean(rmse_test_developing_degree4)

    if len(r2_train_developing_degree1) != 0:
        avg_r2_train_developing_degree1 = np.mean(r2_train_developing_degree1)
    if len(r2_train_developing_degree2) != 0:
        avg_r2_train_developing_degree2 = np.mean(r2_train_developing_degree2)
    if len(r2_train_developing_degree3) != 0:
        avg_r2_train_developing_degree3 = np.mean(r2_train_developing_degree3)
    if len(r2_train_developing_degree4) != 0:
        avg_r2_train_developing_degree4 = np.mean(r2_train_developing_degree4)

    if len(r2_test_developing_degree1) != 0:
        avg_r2_test_developing_degree1 = np.mean(r2_test_developing_degree1)
    if len(r2_test_developing_degree2) != 0:
        avg_r2_test_developing_degree2 = np.mean(r2_test_developing_degree2)
    if len(r2_test_developing_degree3) != 0:
        avg_r2_test_developing_degree3 = np.mean(r2_test_developing_degree3)
    if len(r2_test_developing_degree4) != 0:
        avg_r2_test_developing_degree4 = np.mean(r2_test_developing_degree4)

    # Print the results
    print("")
    print("Developed countries:")
    print("AVG RMSE Linear Training Developed", avg_rmse_train_developed_degree1)
    print("AVG RMSE Quadratic Training Developed", avg_rmse_train_developed_degree2)
    print("AVG RMSE Cubic Training Developed", avg_rmse_train_developed_degree3)
    print("AVG RMSE Quartic Training Developed", avg_rmse_train_developed_degree4)

    print("AVG RMSE Linear Testing Developed", avg_rmse_test_developed_degree1)
    print("AVG RMSE Quadratic Testing Developed", avg_rmse_test_developed_degree2)
    print("AVG RMSE Cubic Testing Developed", avg_rmse_test_developed_degree3)
    print("AVG RMSE Quartic Testing Developed", avg_rmse_test_developed_degree4)

    print("AVG R2 Linear Training Developed", avg_r2_train_developed_degree1)
    print("AVG R2 Quadratic Training Developed", avg_r2_train_developed_degree2)
    print("AVG R2 Cubic Training Developed", avg_r2_train_developed_degree3)
    print("AVG R2 Quartic Training Developed", avg_r2_train_developed_degree4)

    print("AVG R2 Linear Testing Developed", avg_r2_test_developed_degree1)
    print("AVG R2 Quadratic Testing Developed", avg_r2_test_developed_degree2)
    print("AVG R2 Cubic Testing Developed", avg_r2_test_developed_degree3)
    print("AVG R2 Quartic Testing Developed", avg_r2_test_developed_degree4)

    print("")
    print("Developing countries:")
    print("AVG RMSE Linear Training Developing", avg_rmse_train_developing_degree1)
    print("AVG RMSE Quadratic Training Developing", avg_rmse_train_developing_degree2)
    print("AVG RMSE Cubic Training Developing", avg_rmse_train_developing_degree3)
    print("AVG RMSE Quartic Training Developing", avg_rmse_train_developing_degree4)

    print("AVG RMSE Linear Testing Developing", avg_rmse_test_developing_degree1)
    print("AVG RMSE Quadratic Testing Developing", avg_rmse_test_developing_degree2)
    print("AVG RMSE Cubic Testing Developing", avg_rmse_test_developing_degree3)
    print("AVG RMSE Quartic Testing Developing", avg_rmse_test_developing_degree4)

    print("AVG R2 Linear Training Developing", avg_r2_train_developing_degree1)
    print("AVG R2 Quadratic Training Developing", avg_r2_train_developing_degree2)
    print("AVG R2 Cubic Training Developing", avg_r2_train_developing_degree3)
    print("AVG R2 Quartic Training Developing", avg_r2_train_developing_degree4)

    print("AVG R2 Linear Testing Developing", avg_r2_test_developing_degree1)
    print("AVG R2 Quadratic Testing Developing", avg_r2_test_developing_degree2)
    print("AVG R2 Cubic Testing Developing", avg_r2_test_developing_degree3)
    print("AVG R2 Quartic Testing Developing", avg_r2_test_developing_degree4)


print(calculate_all_countries_le())