
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

**Remarques préliminaires**

- On Définit une classe qui générera un identifiant pour chaque nouveau thread.
- L'identifiant du thread est local au thread.
- Les deux implémentations de *MyThread* (i.e ```MyThread21``` et ```MyThread22```) ont le même code en commun. La différence est que dans la première version, l'identifiant du thread est initialisé **à l'exécution** du thread, tandis que dans le deuxième version, l'identifiant est initialisé **à la construction** du thread.

Je n'ai pas remarqué de différence entre les deux versions.

## Exercice 3 ##

On veut tester le comportement d'un programme multithread manipulant une variable
de classe partagée issue de ```Main```

### Remarque préliminaire ###

Dans l'hypothèse où ```cur``` ∈ ]-∞, +∞[, on peut dire que ce programme peut ne pas terminer.
En effet, il existe une exécution tel que le thread ```MyObject``` ne termine pas.

Dans ```MyObject```, l'exécution se termine lorsque ```cur = 10```. Or, ```cur```
est réinitialisé à ```Main.check``` à chaque que la condition ```Main.check > cur```
est vrai dans la boucle *for* dans ```MyObject```.

De plus, dans ```Stop```, ```Main.check``` est incrémenté jusqu'à atteindre la valeur ```11```.

Il suffit donc que le thread ```Stop``` se lance en tout premier, et ceux,
durant toute son exécution, puis termine (avec en post-condition ```Main.check = 11```),
et que le thread ```MyObject``` se lance pour avoir l'absence de terminaison du programme
(```cur != 10``` ne sera donc jamais vrai dans cette exécution).

### Analyse du comportement du programme ###

** Avec le mot-clé ```volatile```**

Nous avons (toujours ?) systématiquement l'affichage suivant:

```
check = 1 cur = 0
check = 2 cur = 1
check = 3 cur = 2
check = 4 cur = 3
check = 5 cur = 4
check = 6 cur = 5
check = 7 cur = 6
check = 8 cur = 7
check = 9 cur = 8
check = 10 cur = 9
coucou
received 11 stop

```

** Sans le mot-clé ```volatile```**

Le programme ne termine pas, et ceux, malgré l'appel de ```Thread.sleep()```.

### Explication ###

Cela est dû au fait que lorsqu'on utilise le mot-clé volatile, on indique que la variable sera modifié par plusieurs threads. De ce fait, la valeur de la variable ```Main.check``` ne sera pas placé dans le cache cache locale d'un thread.

De plus, l'accès à une variable volatile fonctionne plus ou moins comme si on la manipulait dans un bloc *synchronized*, mais appliqué à la variable elle-même.

Source - [The volatile keyword in Java]

---

[spec]: https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.2
[The volatile keyword in Java]: https://javamex.com/tutorials/synchronization_volatile.shtml
