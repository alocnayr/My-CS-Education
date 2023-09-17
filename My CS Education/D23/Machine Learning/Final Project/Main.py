import pandas as pd
import numpy as np
from sklearn.cluster import KMeans
from sklearn.decomposition import PCA
from sklearn.ensemble import RandomForestClassifier, VotingClassifier
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.svm import SVC
from sklearn.pipeline import Pipeline
from tqdm import tqdm
from sklearn.linear_model import LogisticRegression
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt
import matplotlib.colors
import matplotlib.figure
import tensorflow as tf
from imblearn.over_sampling import SMOTE
from sklearn.metrics import roc_auc_score


def default_nos(test_data_subset):
    n = test_data_subset.shape[0]
    Nos = np.zeros(n)

    predictions = pd.DataFrame({
        'customer_ID': test_data_subset['customer_ID'],
        'prediction': Nos
    })

    return predictions


def train_ensemble(X_train: pd.DataFrame, y_train, X_test: pd.DataFrame, class_weight=None):
    df_labels = ['customer_ID', 'S_2']

    X_train_labels = X_train[df_labels]
    X_train = X_train.drop(df_labels, axis=1)

    X_test_labels = X_test[df_labels]
    X_test = X_test.drop(df_labels, axis=1)

    X_train = X_train.to_numpy()
    X_train = X_train.astype('float64')

    y_train = y_train.to_numpy()
    y_train = y_train[:, 1].astype('float64')

    X_test = X_test.to_numpy()
    X_test = X_test.astype('float64')

    # SMOTE for oversampling
    smote = SMOTE()
    X_train_oversampled, y_train_oversampled = smote.fit_resample(X_train, y_train)

    knn = KNeighborsClassifier()
    svm = SVC(probability=True, class_weight=class_weight)
    random_forest = RandomForestClassifier(n_estimators=100, class_weight=class_weight)

    ensemble = VotingClassifier(estimators=[('knn', knn), ('svm', svm), ('random_forest', random_forest)],
                                voting='soft')
    ensemble.fit(X_train_oversampled, y_train_oversampled)
    predictions = ensemble.predict(X_test)
    return predictions


# capture relationship between categorical variables and target variable (what is being predicted)
# encodes categorical features as numerical features by replacing each category with the mean of the target variable
def target_encode_categorical_features(X_train, y_train, X_test, categorical_features):
    X_train_encoded = X_train.copy()
    X_test_encoded = X_test.copy()

    for feature in categorical_features:
        if feature in X_train.columns:
            # calc mean of the target variable for each category in the training set
            mean_encoding = X_train.groupby(feature).agg({y_train.name: 'mean'})

            # replace categorical feature with target mean
            # turn categorical feature into a numerical feature
            X_train_encoded[feature] = X_train[feature].map(mean_encoding[y_train.name])
            X_test_encoded[feature] = X_test[feature].map(mean_encoding[y_train.name])

    return X_train_encoded, X_test_encoded


def oversample_data(X_train, y_train):
    smote = SMOTE(random_state=42)
    X_train_resampled, y_train_resampled = smote.fit_resample(X_train, y_train)
    return X_train_resampled, y_train_resampled


def train_knn(X_train: pd.DataFrame, y_train, X_test: pd.DataFrame):
    df_labels = ['customer_ID', 'S_2']

    X_train_labels = X_train[df_labels]
    X_train = X_train.drop(df_labels, axis=1)

    X_test_labels = X_test[df_labels]
    X_test = X_test.drop(df_labels, axis=1)

    X_train = X_train.to_numpy()
    X_train = X_train.astype('float64')

    y_train = y_train.to_numpy()
    y_train = y_train[:, 1].astype('float64')

    X_test = X_test.to_numpy()
    X_test = X_test.astype('float64')

    knn = KNeighborsClassifier()
    knn.fit(X_train, y_train)
    predictions = knn.predict(X_test)
    return predictions

def train_kmeans(X_train, y_train, X_test):
    df_labels = ['customer_ID', 'S_2']

    X_train_labels = X_train[df_labels]
    X_train = X_train.drop(df_labels, axis=1)

    X_test_labels = X_test[df_labels]
    X_test = X_test.drop(df_labels, axis=1)

    X_train = X_train.to_numpy()
    X_train = X_train.astype('float64')

    y_train = y_train.to_numpy()
    y_train = y_train[:, 1].astype('float64')

    X_test = X_test.to_numpy()
    X_test = X_test.astype('float64')

    kmeans = KMeans()
    kmeans.fit(X_train, y_train)
    predictions = kmeans.predict(X_test)
    return predictions


def apply_nn(network: tf.keras.Model, X: pd.DataFrame):
    df_all = list(X.columns)
    df_labels = ['customer_ID', 'S_2']

    X_labels = X[df_labels]
    X = X.drop(df_labels, axis=1)

    X = X.to_numpy()
    X = X.astype('float64')

    return network.predict(X)


def train_deep_nn(X_train: pd.DataFrame, y_train, num_layers):
    df_all = list(X_train.columns)
    df_labels = ['customer_ID', 'S_2']

    X_train_labels = X_train[df_labels]
    X_train = X_train.drop(df_labels, axis=1)

    X_train = X_train.to_numpy()
    X_train = X_train.astype('float64')

    y_train = y_train.to_numpy()
    y_train = y_train[:, 1].astype('float64')

    layers = [
        tf.keras.layers.Dense(128, activation='relu', input_shape=(X_train.shape[1],)),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dense(32, activation='relu'),
        tf.keras.layers.Dense(1, activation='sigmoid')
    ]

    models = []

    for i in range(num_layers - 4):
        model = tf.keras.models.Sequential(layers)

        # use SGD optimizer with learning rate of 0.01
        optimizer = tf.keras.optimizers.legacy.SGD(learning_rate=0.01)
        # use binary cross entropy for binary classification
        model.compile(loss='binary_crossentropy', optimizer=optimizer, metrics=[tf.keras.metrics.AUC()])

        model.fit(X_train, y_train, epochs=10, batch_size=32, verbose=0)  # verbose = 1 to print progress bar

        #predictions = model.predict(X_test)
        predictions = model.predict(X_train)

        print("TRAINING log loss for", i + 4, "layers")
        # print(model.compute_loss(X_train, y_train, model.predict(X_train)))
        print(log_loss(predictions, y_train))

        models.append(model)

        layers.insert(1, tf.keras.layers.Dense(128, activation='relu'))

    return models


def read_test_data(subset_rows, progress_bar_chunks):
    test_data_subset = pd.DataFrame()

    with tqdm(total=subset_rows, desc="Reading test_data.csv",
              colour='magenta') as pbar:  # set max value of progress bar and description
        for chunk in pd.read_csv('test_data.csv',
                                 chunksize=progress_bar_chunks):  # read csv in chunks to update progress bar
            test_data_subset = pd.concat([test_data_subset, chunk])  # concat chunks to create a subset of the data
            pbar.update(progress_bar_chunks)  # update progress bar

            if len(test_data_subset) >= subset_rows:  # break if subset_rows is reached
                break

    return test_data_subset


def read_train_data(subset_rows):
    train_data = pd.read_csv('train_data.csv', nrows=subset_rows)
    return train_data


def read_train_labels(subset_rows):
    train_labels = pd.read_csv('train_labels.csv', nrows=subset_rows)
    return train_labels


'''(-1/n) * sum all log losses -->-1/n * sum ( y*log(prediction) + (1-y)*log(1-prediction) )'''


def log_loss(predictions, train_labels):
    n = predictions.shape[0]
    epsilon = 1e-7
    try:
        p = np.atleast_2d(predictions['prediction']).T
        y = np.atleast_2d(train_labels['target']).T
    except:
        p = np.atleast_2d(predictions)
        y = np.atleast_2d(train_labels).T
    pt1 = y * np.log(p + epsilon)
    pt2 = (1 - y) * np.log(1 - p + epsilon)
    return -np.mean(pt1 + pt2)


def reformat_data(predictions: np.ndarray, labels):
    '''
    Takes in a list of predictions and labels and makes the predictions the same size...
    '''
    print(predictions[0,:10])
    predictions = predictions.to_frame()
    labels = labels.to_frame()
    predictions = predictions.groupby(['cutomer_ID']).mean
    print(predictions[0, :10])

    return predictions, labels

def FP(predictions, train_labels):
    diff = predictions - train_labels
    diff = np.where(diff > -0.5, 1, 0)
    FP = diff.sum()
    total = predictions.shape[0]
    FP_rate = FN/total
    return FP_rate

def FN(predictions, train_labels):
    diff = predictions - train_labels
    diff = np.where(diff > 0.9, 1, 0)
    FN = diff.sum()
    total = predictions.shape[0]
    FN_rate = FN/total
    return FN_rate

# Save the predictions to a csv file
def save_predictions(predictions, filename):
    predictions.to_csv(filename, index=False)  # index=False to avoid writing the index column


# figure out how and where to join pick out only the remaining data from the labels
def data_processing(training_data: pd.DataFrame, testing_data, y):
    df_all = list(training_data.columns)
    # "customer_ID and S_2 are the keys for the data..."

    df = training_data[df_all]
    df_test = testing_data[df_all]

    # drop all of the columns that are missing more than 80% of their data
    percent = 20.0
    min_count = int(((100 - percent) / 100) * df.shape[0] + 1)

    df = df.dropna(axis=1, thresh=min_count)
    # df_test = df_test.dropna(axis=1, thresh=min_count)
    df_test = df_test.loc[:, df.columns.to_list()]

    df = df.dropna()
    df_test = df_test.dropna()

    df = df.reset_index()
    df_test = df_test.reset_index()

    df = df.drop("index", axis=1)
    df_test = df_test.drop("index", axis=1)

    df_cat = ['B_30', 'B_38', 'D_114', 'D_116', 'D_117', 'D_120', 'D_126', 'D_63', 'D_64', 'D_66', 'D_68']

    df_all = list(df.columns)
    df_num = list(set(df_all) - set(df_cat))
    df_cat = list(set(df_all) - set(df_num))

    df_dropped = df.loc[:, df_num]
    df_test_dropped = df_test.loc[:, df_num]

    df_encoded = pd.get_dummies(df[df_all], columns=df_cat, drop_first=True)
    df_test_encoded = pd.get_dummies(df_test[df_all], columns=df_cat, drop_first=True)

    ids = df_encoded["customer_ID"].to_frame()
    dates = df_encoded["S_2"]

    y = ids.join(y.set_index('customer_ID'), on='customer_ID')

    # return df_encoded, df_test_encoded, y
    return df_dropped, df_test_dropped, y


def two_PCA(x, y):
    y = y['target']
    y = np.where(y == 1, '#1f77b4', '#bcbd22')
    pca = PCA(2)
    pipe = Pipeline([('scaler', StandardScaler()), ('pca', pca)])
    plot = plt.scatter(x['P_2'], x['D_48'], c=y)
    plt.show()
    return 0


def three_PCA(x, y):
    y = y['target']
    pca = PCA(3)
    pipe = Pipeline([('scaler', StandardScaler()), ('pca', pca)])
    fig = plt.figure()
    ax = fig.add_subplot(projection='3d')
    ax.scatter(x['P_2'], x['B_9'], x['S_3'], c=y)
    plt.show()
    return 0


# fit_to is preprocessed dataframe, X is testing data, y is testing labels
def fit_test_data(fit_to, X, y):
    df_all = list(fit_to.columns)
    # "customer_ID and S_2 are the keys for the data..."

    df_fit = fit_to[df_all]
    df_test = X[df_all]

    # drop the rows with null vals
    df_test = df_test.dropna()

    # ??
    df_fit = df_fit.reset_index()
    df_test = df_test.reset_index()

    # drops the first col that labels the rows?
    df = df_fit.drop("index", axis=1)
    df_test = df_test.drop("index", axis=1)

    # categorical data
    df_cat = ['B_30', 'B_38', 'D_114', 'D_116', 'D_117', 'D_120', 'D_126', 'D_63', 'D_64', 'D_66', 'D_68']

    ids = df_test["customer_ID"].to_frame()
    dates = df["S_2"]

    y = ids.join(y.set_index('customer_ID'), on='customer_ID')

    return df, df_test, y


def main():
    pd.set_option('display.max_rows', None)

    # num rows to read from the CSV file (needed to reduce the execution time and storage)
    subset_rows = 10000
    # chunk size for the progress bar
    progress_bar_chunks = 500

    test_data_subset = read_test_data(subset_rows, progress_bar_chunks)
    all_no_predictions = default_nos(test_data_subset)
    save_predictions(all_no_predictions, 'Nos_submission.csv')

    train_data_subset = read_train_data(subset_rows)
    train_labels_subset = read_train_labels(subset_rows)

    no_log_loss = log_loss(all_no_predictions, train_labels_subset)
    print("All 'No Default Risk' log loss:", no_log_loss)

    train_data_subset, test_data_subset, train_labels_subset = data_processing(train_data_subset, test_data_subset,
                                                                               train_labels_subset)

    # PCA's Visualization
    # two_PCA(train_data_subset, train_labels_subset)
    # three_PCA(train_data_subset, train_labels_subset)

    deep_nns = train_deep_nn(train_data_subset, train_labels_subset, 10)

    X_validation = read_train_data(2000).iloc[1000:, :]
    y_validation = read_train_labels(2000)

    garbage1, X_validation, y_validation = fit_test_data(train_data_subset, X_validation, y_validation)

    y_validation = y_validation.to_numpy() 
    y_labels = y_validation[:, 0]
    # add labels to y_hat and make that a CSV!!!
    y_validation = y_validation[:, 1].astype('float64')

    for i, deep_nn in enumerate(deep_nns): 
        y_hat = apply_nn(deep_nn, X_validation)
        print("Log loss for " + str(i + 4) + " layers:")
        print(log_loss(y_hat, y_validation))
        
    y_complete_prediction = np.hstack((np.atleast_2d(y_labels).T, y_hat))
    y_complete_prediction[0,0] = 'customer_ID'
    y_complete_prediction[0,1] = 'target'

    save_predictions(pd.DataFrame(y_complete_prediction), 'NN_submission.csv')

    print("VALIDATION Neural Network log loss: {:.4f}".format(log_loss(y_hat, y_validation)))
    print("FN rate:", FN(y_hat, np.atleast_2d(y_validation).T))

    knn_predictions = train_knn(train_data_subset, train_labels_subset, test_data_subset)
    knn_log_loss = log_loss(knn_predictions, y_validation)
    print("KNN log_loss: {:.4f}".format(knn_log_loss))

    categorical_features = ['B_30', 'B_38', 'D_114', 'D_116', 'D_117', 'D_120', 'D_126', 'D_63', 'D_64', 'D_66', 'D_68']
    X_train_encoded, X_test_encoded = target_encode_categorical_features(train_data_subset,
                                                                         train_labels_subset,
                                                                         test_data_subset, categorical_features)

    knn_predictions = train_knn(X_train_encoded, train_labels_subset, X_test_encoded)
    knn_log_loss = log_loss(knn_predictions, y_validation)
    print("KNN log loss after target encoding categorical features:".format(knn_log_loss))
    knn_predictions = reformat_data(knn_predictions, y_validation)
    print("FN rate:", FN(knn_predictions, y_validation))

    kmeans_predictions = train_kmeans(train_data_subset, train_labels_subset, test_data_subset)
    max = kmeans_predictions.max()
    print("Kmeans log loss:", log_loss(kmeans_predictions/max, y_validation))
    print("FN rate:", FN(kmeans_predictions, y_validation))

    ensemble_predictions = train_ensemble(train_data_subset, train_labels_subset, test_data_subset)
    ensemble_log_loss = log_loss(ensemble_predictions, y_validation)
    print("Ensemble log_loss with even weights: {:.4f}".format(ensemble_log_loss))

    ensemble_predictions = train_ensemble(train_data_subset, train_labels_subset, test_data_subset, class_weight={0: 1, 1: 20})
    ensemble_log_loss = log_loss(ensemble_predictions, y_validation)
    print("Ensemble log_loss with positive class weights: {:.4f}".format(ensemble_log_loss))

    ensemble_predictions = train_ensemble(X_train_encoded, train_labels_subset, X_test_encoded)
    ensemble_log_loss = log_loss(ensemble_predictions, y_validation)
    print("Ensemble log_loss with even weights after target encoding categorical features: {:.4f}".format(ensemble_log_loss))

    ensemble_predictions = train_ensemble(X_train_encoded, train_labels_subset, X_test_encoded, class_weight={0: 1, 1: 20})
    ensemble_log_loss = log_loss(ensemble_predictions, y_validation)
    print("Ensemble log_loss with positive class weights after target encoding categorical features: {:.4f}".format(ensemble_log_loss))
    print("FN rate:", FN(ensemble_predictions, y_validation))


if __name__ == '__main__':
    main()