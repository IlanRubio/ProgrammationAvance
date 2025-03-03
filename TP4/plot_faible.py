import pandas as pd
import matplotlib.pyplot as plt

def plot_speedup_faible(csv_file):
    # Charger les données du CSV
    df = pd.read_csv(csv_file)

    # Calculer T(1) et Ntot(1) (référence pour le calcul du speedup)
    T1 = df[df['nb_worker'] == 1]['temps(ms)'].mean()
    Ntot1 = df[df['nb_worker'] == 1]['processus_total'].mean()

    # Calculer le speedup faible
    df['Speedup_Faible'] = (T1 * Ntot1) / (df['temps(ms)'] * df['processus_total'])

    # Supprimer les doublons en moyennant les valeurs pour chaque nb_worker
    df_unique = df.groupby('nb_worker', as_index=False).mean()

    # Tracer la courbe du Speedup faible
    plt.figure(figsize=(8, 5))
    plt.plot(df_unique['nb_worker'], df_unique['Speedup_Faible'], marker='o', linestyle='-', color='b', label='Speedup mesuré')

    # Tracer la courbe idéale (Speedup = 1)
    plt.axhline(y=1, linestyle='--', color='r', label='Speedup idéal (1)')

    # Ajouter des labels et une légende
    plt.xlabel("Nombre de Threads")
    plt.ylabel("Speedup")
    plt.title("Courbe du Speedup en fonction des Threads (Scalabilité Faible)")
    plt.legend()
    plt.grid()
    plt.show()

# Exemple d'appel de la fonction
plot_speedup_faible("distributedMC_javaSocket/Out_socket/out_socket_G26_4c_1w_12e7_sfaible.csv")
