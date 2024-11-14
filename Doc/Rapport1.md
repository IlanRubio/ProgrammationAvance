# Programmation Avancée

## Sommaire

- [Introduction](#introduction)
- [TP0](#tp0)
- [TP1](#tp1)
- [TP2](#tp2)
- [TP3](#tp3)
- [Conclusion](#conclusion)

## Introduction
Introduction au sujet de la programmation avancée...

## TP0
Architecture matériel des ordinateurs de la salle G26.

| Composant | Détails                                                          |
|-----------|------------------------------------------------------------------|
| **CPU**   | **Processeur :** Intel(R) Core(TM) i7-7700 CPU @ 3.60GHz         |
|           | **Vitesse de base :** 3,60 GHz                                   |
|           | **Sockets :** 1                                                  |
|           | **Cœurs :** 4                                                    |
|           | **Processeurs logiques :** 8                                     |
|           | **Virtualisation :** Activé                                      |
|           | **Cache de niveau 1 :** 256 Ko                                   |
|           | **Cache de niveau 2 :** 1,0 Mo                                   |
|           | **Cache de niveau 3 :** 8,0 Mo                                   |
|           | **Processus :** 121                                              |
|           | **Threads :** 1740                                               |
|           | **Handles :** 57051                                              |
| **RAM**   | **Mémoire :** 32,0 Go                                            |
|           | **Vitesse :** 2400 MHz                                           |
| **GPU**   | **GPU 0 :** Intel(R) HD Graphics 630                             |
|           | **Version du pilote :** 27.20.100.9664                           |
|           | **Version DirectX :** 12 (FL 12.1)                               |
|           | **Emplacement physique :** Bus PCI 0, périphérique 2, fonction 0 |

Architecture logicielle de mon téléphone Samsung S20 : 

| Composant      | Détails       |
|----------------|---------------|
| **Processeur** | Exynos 990    |
| **GPU**        | Mali-G77 MP11 |
| **RAM**        | 16 Go         |

Ce premier tp permet de mettre en avant les composants de chaques ordinateurs de la salle afin de comprendre au mieux leurs fonctionnements et leurs vitesse de réponses.
De plus, il permet de mettre en avant les différences qu'il y a entre les composants des ordinateurs/smartphones d'aujourd'hui par rapoort aux premiers existants.


## TP1
![DiagrammeTP1.png](DiagrammeTP1.png)

Pour ce tp1, le but était d'afficher dans un premier temps un module qui se déplace dans une fenêtre définie.

```java
class UnMobile extends JPanel implements Runnable {
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 10,  sonCote = 40;
    int sonTemps = (int) (Math.random() * ((40 - 5) + 1)) + 5;
    static semaphoreGlobal sem = new semaphoreGlobal(1);

    UnMobile(int telleLargeur, int telleHauteur) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
    }

    public void run() {
        for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas) {
            repaint();
            try {
                Thread.sleep(sonTemps);
            } catch (InterruptedException telleExcp) {
                telleExcp.printStackTrace();
            }
        }
    }
    public void paintComponent(Graphics telCG) {
        super.paintComponent(telCG);
        telCG.fillRect(sonDebDessin, saHauteur / 2, sonCote, sonCote);
    }
}
```
Cette partie de code permet alors de créer un mobile et ses fonctionnalités. 
Pour ensuite l'instancier, il faut alors créer un objet un mobile dans une classe le permettant. 

Pour le créer et l'afficher, j'ai créé une classe `UneFenetre`. Dans cette classe, on instancie le mobile et le thread de ce mobile. 

Thread : unité d'exécution au sein d'un programme. Cela répresente une séquence de tâche pouvant être exécuté de manière indépendate au sein d'un processus.
En java, un Thread est instancié en utilisant son constructeur. 

```java
class UneFenetre extends JFrame {
    UnMobile sonMobile;
    private final int LARG = 400, HAUT = 250;

    public UneFenetre() {
        super("le Mobile");
        sonMobile = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile);

        // creer une thread laThread avec sonMobile
        Thread laTache = new Thread(sonMobile);
        laTache.start();
        
        setSize(3000, 2500);
        setVisible(true);
        // lancer laThread
    }
}
```

Pour lancer le mobile, la classe `TpMobile` permet de créer un objet Fenetre permettant ainsi de lancer une fenetre avec le mobile se déplaçant.

```java
public class TpMobile {
    public static void main(String[] telsArgs) {
        new UneFenetre();
    }
}
```

Pour que le mobile fasse le retour, il faut alors ajouter une boucle, dans la méthode run de la classe `UnMobile` permettant de faire le chemin dans le sens contraire. 

```java
for (sonDebDessin = saLargeur - sonPas; sonDebDessin > 0; sonDebDessin -= sonPas) {
    repaint();
    try {
        Thread.sleep(sonTemps);
    } catch (InterruptedException telleExcp) {
        telleExcp.printStackTrace();
    }
}
```

Pour la suite, j'ai créé deux autres mobiles qui effectuent le même mouvement que le premier. Pour se faire, il fallait instancier deux nouveaux mobiles dans la classe `UneFenetre`.
De plus, le chemin parcouru par ces trois mobiles étaient multiplié par 3. Ainsi, j'ai divisé en trois parties/colonnes pour le chemin des mobiles.
Donc j'ai utilisé un GridPane pour permettre de choisir le nombre de colonne voulu.
```java
class UneFenetre extends JFrame {
    UnMobile sonMobile;
    UnMobile sonMobile1;
    UnMobile sonMobile2;
    private final int LARG = 400, HAUT = 250;

    public UneFenetre() {
        // TODO
        super("le Mobile");
        Container leContainer = getContentPane();
        leContainer.setLayout(new GridLayout(3, 1));
        sonMobile = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile);

        // creer une thread laThread avec sonMobile
        Thread laTache = new Thread(sonMobile);
        laTache.start();

        sonMobile1 = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile1);
        Thread laTache1 = new Thread(sonMobile1);
        laTache1.start();

        sonMobile2 = new UnMobile(LARG, HAUT);
        leContainer.add(sonMobile2);
        Thread laTache2 = new Thread(sonMobile2);
        laTache2.start();

        setSize(3000, 2500);
        setVisible(true);
        // lancer laThread
    }
}
```
Pour permettre également aux mobiles de déplacer j'ai donc modifié la classe `UnMobile`, en créant de nouvelle boucle pour faire que les mobiles avancent et reviennent plus loin.

```java
public void run() {
        while (true) { //pour le faire tourner en permanence
            // du debut au premier tiers
            for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }


            // premier tiers au deuxieme
            for (sonDebDessin = saLargeur; sonDebDessin < 2 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            // deuxieme tiers à la fin
            for (sonDebDessin = 2 * saLargeur; sonDebDessin < 3 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            // fin au deuxieme tiers
            for (sonDebDessin = 3 * saLargeur - sonPas; sonDebDessin > 2 * saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            //deuxieme tiers au premier
            for (sonDebDessin = 2 * saLargeur - sonPas; sonDebDessin > saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            //premier tiers au debut
            for (sonDebDessin = saLargeur - sonPas; sonDebDessin > 0; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
        }
    }
```

Par la suite, pour qu'un seul mobile puisse passer à la fois dans la colonne du milieu. J'ai du déterminer la section critique ainsi que la ressource critique.

`Une ressource critique est une ressource qui ne peut être utilisée que par
un seul processus à la fois. Par exemple une zone mémoire, ou une
imprimante.` cf - CM2 - Programmation parallèle


`Une section critique est portion de code dans laquelle ne s’exécute qu’un thread à la fois. Une
section critique est utilisée lorsque plusieurs thread accède à une même
ressource.` cf - CM2 - Programmation parallèle 

Ainsi dans ce code la section critique est la boucle suivante dans la classe `UnMobile`.
```java
for (sonDebDessin = saLargeur; sonDebDessin < 2 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
```

Ainsi pour empêcher l'accès à plusieurs mobiles dans cette deuxième colonne, j'ai utilisé une classe sémaphore pour permettre l'accès à un thread à la fois.

```Sémaphore : L'objectif est de contrôler l'accès à une ou plusieurs ressources.
Pour se faire : on utilise des :
- Sémaphore binaire : resource à accès unique
- Sémaphore général : ressource à accès multiple

Il y a deux primtives pour les gestions des accès avec :
- Wait() : permet l’accès à une ressource
- Signal() : permet la libération de la ressource

```

Classe mère des sémaphores : 
```java
public abstract class semaphore {

    protected int valeur=0; // nombre de ressource

    protected semaphore (int valeurInitiale){
	valeur = valeurInitiale>0 ? valeurInitiale:0;
    } // ? -> contraction de if, then, else

    public synchronized void syncWait(){
	try {
	    while(valeur<=0){
		wait();
        }
	    valeur--;
	} catch(InterruptedException e){}
    }

    public synchronized void syncSignal(){
	if(++valeur > 0) notifyAll();
    }
}

```

Pour utiliser cette classe pour les mobiles, j'ai alors créé une classe `semaphoreGlobal`. Cette classe est une classe fille permettant de faire appel à la classe.
```java
public final class semaphoreGlobal extends semaphore{
    public semaphoreGlobal(int valeurInitiale){
        super(valeurInitiale);
    }
}
```

Ainsi pour empêcher que deux mobiles soient en même temps dans la colonne du milieu, il faut ajouter ses sémaphores avant et après les ressources critiques.
En utilisant les méthodes `syncWait()` avant la section critique et `syncSignal()` après la section critique, pour l'aller et le retour des mobiles.

```java
import java.awt.*;
import java.util.concurrent.Semaphore;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable {
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 10,  sonCote = 40;
    int sonTemps = (int) (Math.random() * ((40 - 5) + 1)) + 5;
    static semaphoreGlobal sem = new semaphoreGlobal(1);

    UnMobile(int telleLargeur, int telleHauteur) {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
    }

    public void run() {
        while (true) { //pour le faire tourner en permanence
            // du debut au premier tiers
            for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }


            // premier tiers au deuxieme
            sem.syncWait();
            for (sonDebDessin = saLargeur; sonDebDessin < 2 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
            sem.syncSignal();

            // deuxieme tiers à la fin
            for (sonDebDessin = 2 * saLargeur; sonDebDessin < 3 * saLargeur - sonPas; sonDebDessin += sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            // fin au deuxieme tiers
            for (sonDebDessin = 3 * saLargeur - sonPas; sonDebDessin > 2 * saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }

            //deuxieme tiers au premier
            sem.syncWait();
            for (sonDebDessin = 2 * saLargeur - sonPas; sonDebDessin > saLargeur; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
            sem.syncSignal();

            //premier tiers au debut
            for (sonDebDessin = saLargeur - sonPas; sonDebDessin > 0; sonDebDessin -= sonPas) {
                repaint();
                try {
                    Thread.sleep(sonTemps);
                } catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }
            }
        }
    }

    public void paintComponent(Graphics telCG) {
        super.paintComponent(telCG);
        telCG.fillRect(sonDebDessin, saHauteur / 2, sonCote, sonCote);
    }
}
```

Afin de mieux apercevoir les arrêts de mobiles, j'ai alors décidé de donner à chaque mobiles de vitesses différentes en modifiant leurs temps avec le code suivant : 
```java
int sonTemps = (int) (Math.random() * ((40 - 5) + 1)) + 5;
```

Ainsi, les mobiles sont fonctionnels et fonctionnent sur 3 colonnes, avec un seul mobile à la fois circulant dans celle du milieu.
Vous pouvez essayer les mobiles avec `TpMobile`
## TP2
Contenu du TP2...

## TP3
Contenu du TP3...

## Conclusion
Conclusion au sujet de la programmation avancée...