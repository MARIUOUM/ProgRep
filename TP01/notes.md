
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
