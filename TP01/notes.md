
# Notes à propos du TP #

## Exercice 1 ##

### Question 1 ###

Si l'instruction de type ``` x = x + 1 ``` était atomique, alors nous aurions *value*
et *valuesbis* aux valeurs suivantes:

```java
value = 6000
valuebis = 6000
```

### Question 2 ###

Après avoir exécuté le programme, nous avons le résultat suivant:

```
value: 4051, valuebis: 4068 et last: 1000   // thread W
value: 5994, valuebis: 5994 et last: 5000   // thread R
value: 5994, valuebis: 5994 et last: 0
```

Donc, l'instruction de type ``` x = x + 1 ``` n'est pas atomique.

### Question 3 ###

Il faudra utiliser un moniteur au niveau de la classe ```MonObjet``` de la manière suivante :

```java
// Dans MonObjet
public synchronized void add() {
    last.set(new Integer(last.get() + 1));
    value = value + 1;
    valuebis = valuebis + 1;
}
```
De cette manière, on pourra faire l'incrémentation de manière concurrente.

### Question 4 ###

La variable ```value``` est partagée entre différents threads, tandis que ```last``` est locale à chaque thread.

## Exercice 2 ##

Remarques préliminaires :

- On Définit une classe qui générera un identifiant pour chaque nouveau thread.
- L'identifiant du thread est local au thread.
- Les deux implémentations de *MyThread* (i.e ```MyThread21``` et ```MyThread22```) ont le même code en commun. La différence est que dans la première version, l'identifiant du thread est initialisé **à l'exécution** du thread, tandis que dans le deuxième version, l'identifiant est initialisé **à la construction** du thread.

Après l'exécution du programme avec la première version, voici une sortie possible qui a été générée:

```
Le thread 0
Le thread 9
Le thread 8
Le thread 7
Le thread 6
Le thread 5
Le thread 4
Le thread 3
Le thread 2
Le thread 1
Le thread n° 0 apres le sommeil
Le thread n° 3 apres le sommeil
Le thread n° 5 apres le sommeil
Le thread n° 4 apres le sommeil
Le thread n° 1 apres le sommeil
Le thread n° 8 apres le sommeil
Le thread n° 7 apres le sommeil
Le thread n° 6 apres le sommeil
Le thread n° 2 apres le sommeil
Le thread n° 9 apres le sommeil
```

Après l'exécution du programme avec la deuxième version, voici une sortie possible qui a été générée:

```
```
