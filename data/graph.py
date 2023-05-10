import pandas as pd


dayPath = "./data/day-history.csv"
monthPath = "./data/month-history.csv"
yearPath = "./data/year-history.csv"

df = pd.read_csv(dayPath)

print(df.to_string())