# Programmation Avancée - Rapport n°2

RUBIO Ilan INFA-3

## Introduction

Ce rapport examine l'application de la technique de Monte Carlo pour estimer la valeur de π. 
La méthode consiste à générer des points aléatoires dans un espace défini, 
puis à calculer le rapport entre les points situés à l'intérieur d'une région d'intérêt et ceux situés à l'intérieur de l'espace total. 
Cette approche est particulièrement utile pour des problématiques complexes où une solution analytique est difficile à obtenir.

Pour réaliser ce rapport, des intelligences artificielles ont été utilisées.

## Monte Carlo pour calculer π

Calcul de Pi par une méthode de Monte Carlo.

### Généralités

#### Monte Carlo Histoire
La méthode de Monte Carlo tire son nom du fameux casino de Monte Carlo, en raison de son utilisation intensive du hasard. 
Elle a été largement développée au cours du XXe siècle, notamment par les chercheurs du projet Manhattan pour modéliser des phénomènes complexes.

Aujourd'hui, cette méthode est employée dans de nombreux domaines scientifiques, notamment en physique, en finance et en géophysique. 
Par exemple, les géophysiciens utilisent cette technique pour modéliser l'écoulement de l'eau dans les nappes souterraines.

#### Principe du calcul de π par Monte Carlo


L'idée fondamentale est d'utiliser la probabilité d'un point aléatoirement placé dans un carré pour estimer π.

1. Considérons un carré de côté 1 inscrit dans un repère orthonormé.

2. Un quart de cercle est tracé à l'intérieur de ce carré, avec un rayon de 1.

3. On génère un grand nombre de points aléatoires à l'intérieur du carré.

4. On compte combien de ces points tombent à l'intérieur du quart de cercle, en vérifiant si la condition :
```x²+y² <= 1 ``` est satisfaite.

Le rapport entre le nombre de points à l'intérieur du quart de cercle et le nombre total de points permet d'estimer π en utilisant la relation suivante :
```π ≈ 4 * ncible/ntotal```

L'aire du quart de disque s'écrit A d/4 = πr²/4 = π/4

![MonteCarlo](MonteCarlo.jpg)


**Figure 1 :** illustre le tirage aléatoire de point xp de coordonnées
(xp,yp) où xp, yp suivent une loi (]0,1[).

La probabilité qu'un point Xp soit dans le quart de disque est telle que 

```P(Xp|dp<1)=(Ad/4)/Ac = π/4```

On effectue n_total tirage. Si n_total est grand alors on approche 
```P(Xp|dp<1) ≈ ncible/ntotal```.

Avec ncible le nombre de points dans la cible.

On peut alors approcher π par ```π≈4* ncible/n_total```.


### Algorithme  : Monte Carlo

On écrit alors l'algorithme :
```
n_cible=0
//générer et compter n_cible
for p=0 : n_total-1
    générer xp;
    générer yp;
    if xp²+yp² < 1 then
        ncible ++;
    endif
endfor
//calculer PI
PI = 4*(n_cible/ntotal);
```
Dans cette version de l'algorithme tout est executé séquentiellement.
Afin de l'améliorer et pouvoir le paralléliser, il faut dans un premier temps déterminer quelles sont les différents tâches.

On remarque alors 2 tâches : 
1. **Tâche 1** : Générer et compter n_cible


Cette première tâche contient alors 2 sous tâches : 
* Sous tâche 1 : Générer xp et yp
* Sous tâche 2 : Incrémenter ncible si ````xp²+yp² < 1````

2. **Tâche 2** : Calculer pi

Dans ces tâches, on peut alors remarquer des dépendances.
* La tâche 2 ne peut être effectuée tant que la tâche 1 n'est pas terminée.
* La sous tâche 2 dépend de la première, le point doit être généré avant de savoir s'il est dans la zone souhaitée.

En déterminant ces dépendances, on peut alors remarquer une section critique dans cet algrtihme :
```
if xp²+yp² < 1 then
        ncible ++;
    endif
```
On peut donc en déduire que ```ncible ``` est une ressource critique. Cette ressource doit ainsi être protégée pour éviter des conflits.

### Parallélisation
Pour ce code, on a utilisé deux paradigmes, le parallélisme d'itération parallèle (parallélisme de boucle) et le paradigme Master/Worker.

#### Parallélisme de boucle

PLe parallélisme de boucle est un paradigme de programmation parallèle qui permet d'exécuter plusieurs itérations d'une boucle simultanément. 
Dans le cas de la méthode de Monte Carlo, chaque itération génère un point aléatoire et vérifie s'il se situe à l'intérieur du cercle. 
En exécutant ces itérations en parallèle, on peut accélérer le calcul de π.

On peut retrouver ce paradigme avec le code Assignement102.

#### Master/Worker
Le paradigme Master/Worker fonctionne ainsi :

* Workers : Chaque worker se voit attribuer une tâche spécifique. Dans notre cas, il s'agit de réaliser n tirages aléatoires.
* Master : Il distribue les tâches aux workers et traite les résultats. Ici, il effectue le calcul : ```4 * ncible/ntotal```
* Nous avons donc n processus workers indépendants exécutant simultanément n_total tirages aléatoires chacun. 
Une fois tous les workers ayant terminé leur tâche, le master estime la valeur de π.

![MasterWorker](MasterWorker.jpg)
Ce schéma représente le fonctionnement de ce paradigme. 

On peut retrouver ce code avec le code Pi.java.

## Java

### Assignement102

#### Fonctionnement
![UML Assignement102](Assignement102.jpg)


La classe ```PiMonteCarlo```gère l'exécution du calcul de Monte-Carlo en utilisant le parallélisme avec l'API Concurrent.
Cette classe contient une classe interne ```MonteCarlo```. Cette classe implémente Runnable.

* Simule un tirage de coordonnées (x, y) et vérifie s'il tombe dans le quart de cercle.
* Si ```x²+y² <= 1 ```, alors le point est dans le cercle et on incrémente nAtomSuccess.

La classe ```Assignment102``` : 
* Instancie un objet ```PiMonteCarlo``` avec deux paramèetres le nombre de lancers et le nombre de threads.
* Mesure le temps d'exécution.
* Retourne le résultat

#### Scalabilité forte
![Graphe de scalabilité forte](Graphe/Scalabite_forte_assignement.png)
On peut voir que la scalabilité forte est très mauvaise. On remarque qu'elle est presque constante malgré une baisse 
lorsque l'on passe à 6 processus.

On peut donc en déduire que ce code n'est pas efficace. C'est à dire que la parallélisation
ne permet pas de rendre ce code plus performant et donc d'estimer Pi plus rapidement.

#### Scalabilité faible
![Graphe de scalabilité faible](Graphe/Scalabite_faible_assignement.png)
**refaire le graphe**

#### Comparaison
![comparaison scalabilite](Graphe/Comparaison_assignement.png)

On peut remarquer que les graphes de scalabilités fortes et faibles sont presque équivalentes.
Cela peut s'expliquer par le fait la parallélisation de code n'est pas efficace.
En effet, la majeure partie de temps de calculs de se fait dans la section critique.
Cela empêche de pouvoir paralléliser efficacement ce code.

#### Erreur
**Prendre les données et faire les graphes**

### Pi.java
**FAIRE UML**

#### Fonctionnement

Repose sur l'implémentation de Callable et de Futures.

``Classe Pi``
* Elle récupère les arguments d'entrée (nombre d'itérations et nombre de threads).
* Elle exécute l'approximation via la classe Master.

``Classe Master``
* Cette classe orchestre l'exécution parallèle des tâches en : 
  * Créant une liste de tâches ``List<Callable<Long>>``
  * Les exécutant avec un ``ExecutorService``
  * Récupérant les résultats et calculant 𝜋
* S'assure de l'exécution parallèle des tâches.
* S'occupe de la récupération des résultats et calcul de π.

``Classe Worker``
* Chaque thread exécute une instance de ``Worker`` qui :
  * Génère ``numIterations`` points aléatoires.
  * Compte combien tombent dans le quart de cercle.

#### Scalabilité forte

![Graphe de scalabilité forte](Graphe/Scalabite_forte_pi.png)
#### Scalabilité faible
![Graphe de scalabilité faible](Graphe/Scalabite_faible_pi.png)
#### Comparaison
![Graphe de comparaison scalabilite](Graphe/Comparaison_pi.png)
#### Erreur
![Graphe erreur](Graphe/erreur_pi.png)
### MasterSocket / WorkerSocket

#### Fonctionnement
![UML Master](MasterSocket.jpg)
#### Scalabilité forte
**Prendre les données et faire les graphes**
#### Scalabilité faible
**Prendre les données et faire les graphes**
#### Erreur
**Prendre les données et faire les graphes**

## Définition

* **Speedup** : L’accélération Sp est le gain de vitesse d’exécution en fonction du nombre
  de processus P. On l’exprime comme le rapport du temps d’éxécution sur
  un processus T1, sur le temps d’exécution sur P processus, Tp. On le calcule avec ```Sp = T1/Tp```
On peut le représenter avec la courbe suivante :
  ![Graphe speedup](SppedUp.png)

* **Scalabilité forte** : La scalabilité forte évalue la capacité d’un programme à diminuer son temps d’exécution lorsque le nombre de cœurs augmente, tout en conservant une charge de travail constante. 
Elle mesure ainsi l’efficacité avec laquelle le programme utilise les ressources supplémentaires.

* **Scalabilité faible** : La scalabilité faible mesure la capacité d’un programme à maintenir un temps d’exécution stable lorsque la charge de travail et le nombre de cœurs augmentent. 
Elle évalue dans quelle mesure le programme peut traiter efficacement une charge de travail croissante en exploitant davantage de ressources.

* **ISO/IEC 25010** : La norme **ISO/IEC 25010** est un standard international qui définit un modèle de qualité pour l'évaluation des logiciels et des systèmes informatiques.
  Elle appartient à la famille des normes SQuaRE (Software Product Quality Requirements and Evaluation).

* **ISO/IEC 25022** : La norme ISO/IEC 25022 fait partie de la série SQuaRE (Software Product Quality Requirements and Evaluation) et se concentre sur l'évaluation de la qualité en usage des systèmes et logiciels.

* **Future** : Un Future est un objet qui représente le résultat d'une tâche asynchrone qui s'exécutera dans le futur. 
Il agit comme un conteneur pour un résultat qui n'est pas encore disponible.
Les Futures permettent de :
  * Vérifier si la tâche est terminée
  * Attendre que la tâche se termine et récupérer le résultat

## Performance 

Pour étudier, les performances des codes, on utilise les normes **ISO/IEC 25010** et **ISO/IEC 25022**.

### ISO/IEC 25010

Cette norme est utilisée pour évaluer et améliorer la qualité des logiciels dans des domaines variés comme le développement d’applications, les systèmes embarqués. 
Elle permet aux entreprises de garantir un niveau de qualité optimal pour leurs produits.

#### Le modèle de qualité en usage

Il définit 5 caractéristiques liées à l'expérience utilisateur :

* **Efficacité** (Réalisation des objectifs)
* **Efficience** (Effort minimal pour atteindre un objectif)
* **Satisfaction** (Confort et confiance de l'utilisateur)
* **Sécurité** en usage (Prévention des erreurs humaines)
* **Couverture** du contexte d'utilisation (Adaptabilité à différents contextes)

####  Le modèle de qualité du produit

Il définit 8 caractéristiques de qualité logicielle :

* **Fonctionnalité** (Pertinence fonctionnelle, Exactitude, Complétude)
* **Performance et efficacité** (Temps de réponse, Utilisation des ressources)
* **Compatibilité** (Interopérabilité, Cohabitation avec d'autres systèmes)
* **Utilisabilité** (Facilité d'utilisation, Accessibilité)
* **Fiabilité** (Maturité, Disponibilité, Tolérance aux pannes)
* **Sécurité** (Confidentialité, Intégrité, Authentification)
* **Maintenabilité** (Modularité, Facilité de correction et d'évolution)
* **Portabilité** (Adaptabilité, Capacité d'installation)

### ISO/IEC 25022
* Aide à identifier les points d’amélioration pour optimiser l’expérience utilisateur.
* Permet d’évaluer un produit avant son lancement ou pendant son utilisation réelle.
* Fournit des mesures objectives pour comparer différents systèmes ou versions.

####  Le modèle de qualité du produit

Effectiveness : 

Efficiency : 

#### Le modèle de qualité en usage