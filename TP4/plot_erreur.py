import pandas as pd
import matplotlib.pyplot as plt

def plot_erreur(csv_file, label):
    # Charger les données du CSV
    df = pd.read_csv(csv_file)

    # Extraire les valeurs utiles
    erreurs = df['erreur']