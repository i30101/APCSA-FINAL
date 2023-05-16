# version 1.0.0 11 May 2023
# author Andrew Kim and Dylan Nguyen
# graphing utility for stocks

import sys
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


# read csv data
dataframes = {
    "day" : pd.read_csv("./data/day-history.csv"),
    # "month" : pd.read_csv("./data/month-history.csv"),
    # "year" : pd.read_csv("./data/year-history.csv")
}

# timeframes for stock data
frames = list(dataframes.keys())

# all stock tickers
tickers = list(dataframes["day"]["Ticker"].values)


# extract data for desired ticker
def graph_ticker_history(ticker, index, frame: str):
    dataframe = dataframes[frame]
    row = list(dataframe.iloc[index])[1:]
    title = f"{frame.capitalize()} Price of {ticker}"
    closing_price = str(row[-1])
    if closing_price[-2 : -1] == ".":
        closing_price += "0"
    x_label = f"Current Price: ${closing_price}"
    y_label = "Price ($)"

    line_graph(title, x_label, y_label, row)

    
# create a line graph 
def line_graph(title, x_label, y_label, data):
    length = len(data)
    
    # determine graph color
    col = "green" if data[0] < data[-1] else "red"
    
    # size figure
    plt.rcParams["figure.figsize"] = [7, 5]
    plt.rcParams["figure.autolayout"] = True
    
    X = np.linspace(0, length, length, endpoint=True)
    fig, ax = plt.subplots()
    
    # label graph
    ax.set_title(title)
    closing_price = str(data[-1])
    if closing_price[-2 : -1] == ".":
        closing_price += "0"
    ax.set_xlabel(x_label)
    ax.set_ylabel(y_label)
    
    # set axes limits
    ax.set_xlim(0, length)
    ax.set_ylim(min(data) * 0.95, max(data) * 1.01)
    
    ax.plot(X, data, color=col, alpha=1.00)
    ax.fill_between(X, data, 0, color=col, alpha=.1)
    
    plt.show()
    

# process command line arguments
def process_args():
    frame = sys.argv[1]
    ticker = sys.argv[2]
    if(frame in frames and ticker in tickers):
        graph_ticker_history(ticker, tickers.index(ticker), frame)
    

process_args()
