import pandas as pd


# This method reads in the Life Expectancy csv that is used for the rest of the other methods
def get_data():
    lifeExpectancyData = pd.read_csv(r"Life Expectancy Data.csv", encoding="utf-8")
    return lifeExpectancyData


# This method takes in a dataframe and returns a tuple for the number of developing and developed countries
def get_country_count(lifeExpectancyData):

    countOfDevelopingCountries = 0
    countOfDevelopedCountries = 0

    # iterate through each row in the dataframe one element at a time
    for i, row in lifeExpectancyData.iterrows():
        if row['Status'] == 'Developing':
            if not pd.isna(row['Life expectancy ']):
                # everytime you come across a valid developing (not empty) country, add to developing count
                countOfDevelopingCountries += 1
        if row['Status'] == 'Developed':
            if not pd.isna(row['Life expectancy ']):
                # everytime you come across a valid developed (not empty) country, add to developed count
                countOfDevelopedCountries += 1

    return countOfDevelopingCountries, countOfDevelopedCountries


def cal_mean(lifeExpectancyData):
    # create variables for keeping track of sum and count of developing/develop countries to divide sum/count later
    sumOfDevelopingCountriesLE = 0
    numberOfDevelopingCountries = get_country_count(lifeExpectancyData)[0]

    sumOfDevelopedCountriesLE = 0
    numberOfDevelopedCountries = get_country_count(lifeExpectancyData)[1]

    # iterate through each row in the dataframe one element at a time
    for i, row in lifeExpectancyData.iterrows():

        # filter out developing countries
        if row["Status"] == "Developing":

            # make sure the value at that row in 'Life expectancy ' is not empty or nan
            if not (pd.isna(row["Life expectancy "])):
                # get and add the country's life expectancy to the total sum of developing country's
                currentDevelopingCountryLifeExpectancy = row["Life expectancy "]
                sumOfDevelopingCountriesLE += currentDevelopingCountryLifeExpectancy

        # if not a developing country, must be a developed country
        else:
            # make sure the value at that row in 'Life expectancy ' is not empty or nan
            if not (pd.isna(row["Life expectancy "])):
                # get and add the country's life expectancy to the total sum of developed country's
                currentDevelopedCountryLifeExpectancy = row["Life expectancy "]
                sumOfDevelopedCountriesLE += currentDevelopedCountryLifeExpectancy

    # check to make sure we aren't going to end up dividing by 0 for developing countries
    if numberOfDevelopingCountries == 0:
        meanLifeExpectancyDevelopingCountries = 0
    else:
        # definition of mean calculation, sum/count
        meanLifeExpectancyDevelopingCountries = sumOfDevelopingCountriesLE / numberOfDevelopingCountries

    # check to make sure we aren't going to end up dividing by 0 for developed countries
    if numberOfDevelopedCountries == 0:
        meanLifeExpectancyDevelopedCountries = 0
    else:
        # definition of mean calculation, sum/count
        meanLifeExpectancyDevelopedCountries = sumOfDevelopedCountriesLE / numberOfDevelopedCountries

    # return a tuple of developing,developed countries means
    return meanLifeExpectancyDevelopingCountries, meanLifeExpectancyDevelopedCountries


def cal_std(lifeExpectancyData):
    # calculate mean of the column 'Life expectancy ' for each dataframe
    meanOfDevelopingCountries = cal_mean(lifeExpectancyData)[0]
    meanOfDevelopedCountries = cal_mean(lifeExpectancyData)[1]
    numberOfDevelopingCountries = get_country_count(lifeExpectancyData)[0]
    numberOfDevelopedCountries = get_country_count(lifeExpectancyData)[1]

    sumOfResidualSquaredDevelopingCountries = 0
    sumOfResidualSquaredDevelopedCountries = 0

    for i, row in lifeExpectancyData.iterrows():
        if row['Status'] == 'Developing':
            # make sure the value at that row in 'Life expectancy ' is not empty or nan
            if not pd.isna(row['Life expectancy ']):
                # value at (row - mean)^2 -> add that to sum
                sumOfResidualSquaredDevelopingCountries += (row['Life expectancy '] - meanOfDevelopingCountries) ** 2
        if row['Status'] == 'Developed':
            # make sure the value at that row in 'Life expectancy ' is not empty or nan
            if not pd.isna(row['Life expectancy ']):
                # value at (row - mean)^2 -> add that to sum
                sumOfResidualSquaredDevelopedCountries += (row['Life expectancy '] - meanOfDevelopedCountries) ** 2

    varianceOfDevelopingCountries = sumOfResidualSquaredDevelopingCountries / numberOfDevelopingCountries
    varianceOfDevelopedCountries = sumOfResidualSquaredDevelopedCountries / numberOfDevelopedCountries

    # std is sqrt variance
    stdOfDevelopingCountries = varianceOfDevelopingCountries ** 0.5
    stdOfDevelopedCountries = varianceOfDevelopedCountries ** 0.5

    # return both std
    return stdOfDevelopingCountries, stdOfDevelopedCountries


currData = get_data()
testMeanOfDevelopingCountries, testMeanOfDevelopedCountries = cal_mean(currData)
testStdOfDevelopingCountries, testStdOfDevelopedCountries = cal_std(currData)

print("Mean life expectancy for developing countries: ", testMeanOfDevelopingCountries)
print("Mean life expectancy for developed countries: ", testMeanOfDevelopedCountries)
print("Standard deviation of life expectancy for developing countries: ", testStdOfDevelopingCountries)
print("Standard deviation of life expectancy for developed countries: ", testStdOfDevelopedCountries)
print("")


# method for assigning labels to each country based on mean and std
def label(lifeExpectancyData):
    # calculate means of developing and developed countries
    meanDevelopingCountries, meanDevelopedCountries = cal_mean(lifeExpectancyData)
    stdDevelopingCountries, stdDevelopedCountries = cal_std(lifeExpectancyData)

    # create a new column called 'true_label' and default their values to 'Normal', we'll change them later if needed
    lifeExpectancyData["true_label"] = "Normal"

    # iterate through all the rows in the dataframe to change each row individually as needed
    for index, row in lifeExpectancyData.iterrows():
        if row["Status"] == "Developing":
            # replace any value in 'true_label' column that isn't considered Normal
            if row["Life expectancy "] < (meanDevelopingCountries - stdDevelopingCountries):
                lifeExpectancyData.at[index, "true_label"] = "Short"
            elif row["Life expectancy "] > (meanDevelopingCountries + stdDevelopingCountries):
                lifeExpectancyData.at[index, "true_label"] = "Long"
        elif row["Status"] == "Developed":
            # replace any value in 'true_label' column that isn't considered Normal
            if row["Life expectancy "] < (meanDevelopedCountries - stdDevelopedCountries):
                lifeExpectancyData.at[index, "true_label"] = "Short"
            elif row["Life expectancy "] > (meanDevelopedCountries + stdDevelopedCountries):
                lifeExpectancyData.at[index, "true_label"] = "Long"
    return lifeExpectancyData


# method for turning csv into txt
def dump_data(true_label_data):
    true_label_data.to_csv('true_label.txt', sep='\t', index=False)


data = get_data()
data = label(data)
dump_data(data)
