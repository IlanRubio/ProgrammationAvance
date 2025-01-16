# Programmation Avancée - Rapport n°2

RUBIO Ilan INFA-3


## Sommaire

- [Introduction](#introduction)
- [TP4](#tp4)

## Introduction

## TP4

Calcul de Pi par une méthode de Monte Carlo.

### Généralités

#### Monte Carlo Histoire
Quand on parle de la méthode de Monte de Carlo, on parle de tirage aléatoire. C'est une référence historique au 
casino Monte Carlo. Cette méthode est très utilisé de nos jours.
Cette méthode est très utilisé pour les calculs scientifiques. Notamment pour des calculs avec des géophysiciens. Ils peuvent
s'en servir pour calculer comment s'écoule l'eau dans les souterrains par exemple. 

Cette méthode sur un ensemble de tirage aléatoire tous indépendants.

Pour π :

Soit un carré de côté 1. Soit un quart de disque de rayon r=1.
L'aire de carré s'écrit Ac = r²

L'aire du quart de disque s'écrit A d/4 = πr²/4 = π/4

**Insérer Figure 1**

Figure 1 : illustre le tirage aléatoire de point xp de coordonnées
(xp,yp) où xp, yp suivent une loi (]0,1[).

La probabilité qu'un point Xp soit dans le quart de disque est telle que 

P(Xp|dp<1)=(Ad/4)/Ac = π/4

On effectue n_total tirage. Si n_total est grand alors on approche 
P(Xp|dp<1) ≈ ncible/ntotal.

avec ncible le nombre de points dans la cible.

On peut alors approcher π par π≈4* ncible/n_total.


### Algorithme 1 : Monte Carlo

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


### Parallélisation