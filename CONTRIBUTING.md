# Contribution

## Développer une fonctionnalité
1. Choisir la fonctionnalité à développer, puis créer / s'attribuer une tâche sur le **[Trello](https://trello.com/b/EEpovBgx/projet-final)** et la passer en *"In progress"* ;
1. `pull` la master `origin` pour récupérer toutes les modifications effectuées ;
1. Créer votre branche pour développer votre fonctionnalité ;
1. Lorsque la fonctionnalité est prête, `merge` la branche master `origin` dans votre branche, résolvez les conflits si nécessaire, et tester de nouveau si tout fonctionne (si il y a eu des conflits douloureux à faire ...) ;
1. Créer votre ***pull request*** avec toutes les informations nécessaires pour que quelqu'un puisse la valider (voir l'exemple de template à la fin) et enfin passer votre tâche Trello à *"To validate"*.

Note : il sera sûrement nécessaire de compléter les spécifications de la fonctionnalité sur le **[Drive](https://docs.google.com/document/d/1Vyn0oBPA7_CRFKx2rJASiuy2etdFMVRx1h-N7Zw17CI/edit)** avant de la développer pour avoir les idées claires avant de commencer et pour que l'approbateur de la fonctionnalité sâche ce que la fonctionnalité est sensé faire en details ainsi que sa portée.


## Valider une fonctionnalité
1. Assignez-vous comme approbateur sur la tâche *Trello* de la fonctionnalité ;
1. Mettez-vous sur la branche de la fonctionnalité (`pull` dépuis la remote `origin` si nécessaire)
1. Référez-vous à la **[Definition of Done](https://docs.google.com/document/d/1KLgnAZOMwf-xO0_z7s5mDfObZ-VHm8revc4_wzP6fjg/edit)** pour valider la fonctionnalité ;
1. Si tout est bon au sens de la DoD :
    1. Valider la tâche *Trello* ;
    1. Valider la *pull request* ;
    1. `merge` la *pull request*.
1. Sinon, ajouter des commentaires sur la *pull request* ou sur la code (fonctionnalité de Github) pour décrire ce qu'il ne va pas et/ou discuter avec l'auteur de la *pull request*.

N'oubliez pas de tester les fonctionnalités avec différents scenarios et différentes configurations (rotation de l'écran, vérouillage /devérouillage, retour launcher, désactivation de la connexion internet, etc.)

Note : Si il y a des bugs connus mineurs dans la *pull request* (pas de crash de l'application par exemple ...) elle peut quand même être `merge`, mais des **[issues](https://github.com/Romain-Guillot/UQAC_Mobile_Projet/issues)** Github doivent toutes les répertoriées pour en garder une trace.


## Exemple d'un template d'une *pull request*

```
## Introduction
<introduction de la pull request,
lien vers la spécification de la fonctionnalité,
description de ce qui n'a pas été implémenté par rapport à la spécification,
...>

## Changements mineurs (optionnel)
<les changements mineurs qui ne concerne par directement la fonctionnalité,
mettre uniqument les changements dignent d'interêt (manifest ou des build.gradle typiquement)>

## Changements majeurs
<mettre les fichiers / packages du code qui ont été drastiquement changés>

## Bugs connus
<liste des bugs non critiques>

## TODO (optionnel)
<Si la fonctionnalité n'a pas encore implémentée toute la spécification
mettre ici les choses à faire pour la terminer>
```
