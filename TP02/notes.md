
# Programmation répartie -  TP n° 2 #

## Exercice n° 1 ##

> Question 1

Non, on ne peut avoir plusieurs écrivains, dans la base de données.
En effet, l'accès en écriture, telle qu'elle est définie
par la classe ```ReentrantReadWriteLock``` se fait par exclusion mutuelle entre les écrivains.
Il ne peut donc y avoir qu'un seul écrivain à la fois.

> Question 2

Oui, il est posible d'avoir plusieurs lecteurs dans la basse de données.
En effet, un ```ReentrantReadWriteLock``` permet à plusieurs threads lecteurs de lire des données
"en même temps" sans provoquer d'incohérence dans la basse de données, tant qu'un thread ecrivain
n'essaie pas d'acquérir un lock d'écriture.

> Question 3

On ne peut pas avoir à la fois des lecteurs et des écrivains. En effet, si un écrivain
prend le verrou et écrit des données, alors aucun lecteur ne peut lire dans la base de données.
Cela est spécifié par la documentation de ```ReentrantReadWriteLock```.

> Question 4

Si on remplace la ligne suivante :

```
com
```

par cette ligne :

```
lecteur[0].interrupt();
```

alors il y aura interblocage.
En effet, lorsque l'exception ```InterruptedException``` est lancée, puis attrapée
dans le bloc ```catch```, à aucun moment on prend soin de libérer le verrou.

Voici un exmeple d'affichage en sortie :

```
 lecteur 0  lecteur 1 0 0 0 0

verrou enlevé : 0 sort
- interompu 1

// plus rien après
```

> Question 5

Pour s'assurer que les verrous soient toujours relachés, même en cas d'interruption,
il faut mettre les instructions de relâchement dans un bloc ```finally```.

Dans ```Lect.java``` :

```
base.lock.readLock().lock();

try {

	System.out.print(" lecteur " + ThreadID.get() + " ");

	for (int i = 0; i < base.tab.length; i++)
		System.out.print(base.tab[i] + " ");

	System.out.println("");
	Thread.sleep((long) Math.random() * base.tab.length * 1000);

} catch (InterruptedException e) {

	System.out.println("- interompu " + ThreadID.get());
	break;

} finally {     // Quoiqu'il arrive, on relâche le verrou

	base.lock.readLock().unlock();
}
```

Dans ```Red.java``` :

```
base.lock.writeLock().lock();

try {

	int j = (int) (Math.random() * 100);

	System.out.println(" ecrivain n° " + ThreadID.get() + " ecrit -> " + j);

	for (int i = 0; i < base.tab.length; i++)
		base.tab[i] = j;

	Thread.sleep((long) Math.random() * base.tab.length * 1);

} catch (InterruptedException e) {

	System.out.println(" interompu " + ThreadID.get());
	break;

} finally {     // Quoiqu'il arrive, on relâche le verrou

	base.lock.writeLock().unlock();
}
```

## Exercice n° 2 ##

Avec l'implémentation ```TropSimple``` de ```ReadWriteLock```, on a l'exclusion mutuelle
entre les ecrivains, mais aussi entre les lecteurs et les écrivains.
En revanche, on ne peut avoir plusieurs lecteurs qui peuvent lire en même temps.
