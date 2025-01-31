import pandas as pd
import matplotlib.pyplot as plt

def plot_speedup(csv_file, label):
    # Charger les données du CSV
    df = pd.read_csv(csv_file)

    # Extraire les valeurs utiles
    threads = df['nb_worker']
    durations = df['temps(ms)']

    # Calculer la moyenne des durées pour chaque nombre de threads
    mean_durations = durations.groupby(threads).mean()

    # Calculer le speedup
    t1 = mean_durations.iloc[0]  # Temps d'exécution pour 1 thread
    speedup = t1 / mean_durations

    # Tracer la courbe
    plt.plot(mean_durations.index, speedup, marker='o', linestyle='-', label=label)
    plt.plot(mean_durations.index, mean_durations.index, linestyle='--', color='r', label='Speedup idéal' if label == "Speedup mesuré" else "")

# Configuration de la figure
plt.figure(figsize=(8, 5))

# Tracer les courbes pour chaque fichier
#plot_speedup("TP4/Out_pi/out_pi_G26_4c_12e6.csv", label='Speedup G26 12e6')
plot_speedup("TP4/Out_pi/out_pi_G26_4c_12e7.csv", label='Speedup G26 12e7')
#plot_speedup("TP4/Out_pi/out_pi_G26_4c_12e8.csv", label='Speedup G26 12e8')
#plot_speedup("distributedMC_javaSocket\Out_socket\out_socket_G26_4c_1w.csv", label='Speedup G26 mw')
plot_speedup("distributedMC_javaSocket\Out_socket\out_socket_G26_4c_1w_12e7.csv", label='Speedup G26 12e7 mw')

# Configuration des axes et de la légende
plt.xlabel("Nombre de Threads")
plt.ylabel("Speedup")
plt.title("Courbe du Speedup en fonction des Threads")
plt.legend()
plt.grid()
plt.show()
