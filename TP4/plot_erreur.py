import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

def plot_errors_from_csv(folder_path):
    all_files = [f for f in os.listdir(folder_path) if f.endswith(".csv")]
    
    if not all_files:
        print("Aucun fichier CSV trouvé dans le dossier spécifié.")
        return
    
    plt.figure(figsize=(8, 6))
    data = {}
    
    for file in all_files:
        file_path = os.path.join(folder_path, file)
        df = pd.read_csv(file_path)
        
        if {'processus_total', 'erreur'}.issubset(df.columns):
            for proc, err in zip(df['processus_total'], df['erreur']):
                if proc not in data:
                    data[proc] = []
                data[proc].append(err)
        else:
            print(f"Colonnes manquantes dans {file}, vérifiez le format du fichier.")
    
    if data:
        all_processus = []
        all_medians = []
        for proc, errs in sorted(data.items()):
            median_err = np.median(errs)
            all_processus.append(proc)
            all_medians.append(median_err)
            plt.scatter([proc] * len(errs), errs, alpha=0.5, label=None)  # Affichage des points individuels
            plt.scatter(proc, median_err, color='red', s=100, edgecolors='black', label='Médian' if proc == all_processus[0] else None)
    
    plt.xscale('log')
    plt.yscale('log')
    plt.xlabel("Processus total (log scale)")
    plt.ylabel("Erreur (log scale)")
    plt.title("Erreur en fonction du nombre total de processus")
    plt.legend()
    plt.grid(True, which="both", linestyle="--", linewidth=0.5)
    plt.show()

plot_errors_from_csv('distributedMC_javaSocket\Out_socket')
