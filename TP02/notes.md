
# Programmation répartie -  TP n° 2 #

> Auteurs

- Fekih Ahmed Meriem
- JEAN-PIERRE Luxon

## Exercice n° 1 ##

> Question 1

Non, on ne peut avoir plusieurs écrivains, dans la base de données.
En effet, l'accès en écriture, telle qu'elle est définie
par la classe ```ReentrantReadWriteLock``` se fait par exclusion entre les écrivains.
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

```java
// com
```

par cette ligne :

```java
lecteur[0].interrupt();
```

alors il y aura interblocage.
En effet, lorsque l'exception ```InterruptedException``` est lancée, puis attrapée
dans le bloc ```catch```, à aucun moment on prend soin de libérer le verrou dans ce cas de figure.

Voici un exemple d'affichage en sortie :

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

```java
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

```java
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

Avec l'implémentation ```TropSimple``` de ```ReadWriteLock```, on a l'exclusion
entre les ecrivains, mais aussi entre les lecteurs et les écrivains.
En revanche, on ne peut avoir plusieurs lecteurs qui peuvent lire en même temps.


## Exercice n° 3 ##

> Question n° 1

Nous avons bien l'exclusion entre les ecrivains, puis entre lecteurs et écrivains.
On peut également avoir plusieurs lecteurs qui lisent en même temps.

> Question n° 2

Supposons que des lecteurs lisent. Un écrivain **A** demande l’accés à la base de données,
puis un lecteur **B** veux lire.

Dans cette implémentation, il n'ya aucune garantie que **A** passera avant **B**,
c'est même le contraire. En effet, lorsque **A** va essayer d'écrire, vu qu'il y a
des lecteurs qui lisent, l'écrivain sera bloqué et devra attendre. Tandis que **B**
pourra lire la donnée et passer avant **A**.

> Question n° 3

Dans la class ```Sync``` :

```java
final static class Sync {
	private int readers = 0;
	private int writers = 0;
	private boolean wrequest = false;  // Indique qu'un écrivain veut écrire

	public synchronized void lockR() {
        // Si un écrivain a demandé le verrou (wrequest == true), on attend
        while (writers > 0 || wrequest) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		readers++;
	}

	public synchronized void unlockR() {
		readers--;
		notifyAll();
	}

	public synchronized void lockW() {
        //Dès le debut on met wrequest à true
		wrequest = true;
		while (readers > 0 || writers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		writers++;
        // Après avoir incémenté le nombre d'écrivains,
        // plus besoin de maintenir la requête.
		wrequest = false;
	}

	public synchronized void unlockW() {
		writers--;
		notifyAll();
	}
}
```

Idée :

- Si un écrivain veut écrire, il met une ```wrequest``` à ```true```.
- A la fin de ```lockW()```, l'écrivain n'a plus besoin de maintenir ```wrequest```,
car il a le verrou. Donc ```wrequest``` est mis à ```false```
- Un lecteur ne pourra pas ecrire si un écrivain faire la requête (```wrequest == true```).


> Question n° 4

Toujours dans la class ```Sync``` :

```java
final static class Sync {
	private int readers = 0;
	private int writers = 0;
	private int wrequest_count = 0;    // Nombre d'écrivains voulant écrire

	public synchronized void lockR() {
        // Si au moins un écrivain a demandé le verrou
        // (wrequest_count > 0), on attend
		while (writers > 0 || wrequest_count > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		readers++;
	}

	public synchronized void unlockR() {
		readers--;
		notifyAll();
	}

	public synchronized void lockW() {
        //Dès le debut on incrémente wrequest_count
		wrequest_count++;
		while (readers > 0 || writers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		writers++;
        // On a le verrou, plus besoin d'indiquer qu'on veut le prendre.
        // On décrémente le nombre de demandes en écriture.
		wrequest_count--;
	}

	public synchronized void unlockW() {
		writers--;
		notifyAll();
	}
}
```
