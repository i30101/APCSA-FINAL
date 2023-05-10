import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


DAY_PATH = "./data/day-history.csv"
MONTH_PATH = "./data/month-history.csv"
YEAR_PATH = "./data/year-history.csv"
X_AXIS = list(i for i in range(1, 102))

df = pd.read_csv(DAY_PATH)


tickers = list(df.iloc[:, 0])
print(tickers)

def graph_day_ticker(ticker: str):
    if(ticker in tickers):
        i = tickers.index(ticker)
        row = list(df.iloc[i])
        ticker = row[0]
        data = row[1:]
        
        line_graph(f"Day Price of {ticker}", "Price ($)", data)


def line_graph(title, y_label, data):
    # determine graph color
    col = "green" if data[0] < data[-1] else "red"
    
    # label graph
    # plt.title(title)
    # plt.ylabel(y_label)
    
    # fig, ax = plt.subplots(figsize = (6, 6))
    # plt.show()
    
    plt.rcParams["figure.figsize"] = [7.00, 3.50]
    plt.rcParams["figure.autolayout"] = True
    X = np.linspace(0, 101, 101, endpoint=True)
    fig, ax = plt.subplots()
    ax.set_title(title)
    ax.set_ylabel(y_label)
    ax.plot(X, data, color=col, alpha=1.00)
    ax.fill_between(X, data, 0, color=col, alpha=.1)
    plt.show()
    
    
# graph_day_ticker("INTC")
graph_day_ticker("AXP")
# graph_day_ticker("AMGN")


