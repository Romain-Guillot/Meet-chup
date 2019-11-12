# UQAC_Mobile_Projet

Dans le cadre d'un projet de développement d’applications mobiles, nous proposons une application de partage de souvenir à travers la création et l’organisation d'événements, mais surtout à travers le partage de photos et la mise en commun des souvenirs des personnes ayant participé à l'événement. Finalement, l’utilisateur de l’application pourra avoir des propositions d’événements dépendamment de sa position et des possibilités qu’il y aura autour de lui. 

## Fonctionnalités

### Prioritaires
- [x] **Gestion du profil utilisateur :** *Connexion ; Inscription ; Renseignement du profil (*username*) ; Visualisation du compte utilisateur ; Modification du compte utilisateur*
- [ ] **Visualisation des événements de l'utilisateur (homepage) :** *Liste des événements auxquels l'utilisateur participe*
- [ ] **Création d'un nouvel événement**
- [ ] **Gestion des participants d'un événement :** *Rejoindre un événement grâce à une clé d'invitation ; Visualisation et modification de la clé d'invitation d'un événement ; Suppression de sa participation au sein d'un événement*
- [ ] **Fil d'actualité d'un événement :** *Informations sur l'événement ; Fil d'actualité de tous les posts publiés sur l'événement*
- [ ] **Galerie d'un événement :** *Galerie photos et vidéos de tous les posts de l'événement sous forme de grille*
- [ ] **Publication de post sur l'événément**
- [ ] **Visualisation de post**

### Secondaires
- [ ] Gestion de l'organisation d'un événement (to-do list avec système d'attribution des tâches)
- [ ] Système de commentaire sur les post (ajout, visualisation, suppression)
- [ ] Notification d'arrivée (physique) sur le lieu de l'événement
- [ ] Identification des personnes sur les photos


## Divers

### Thème
L'application utilise le *Material Design*. Quand cela est possible il est en général recommandé d'utiliser les [composants Material Design pour Android (liste des composants disponibles à gauche)](https://material.io/develop/android/). Par exemple :
- [Text fields](https://material.io/develop/android/components/text-input-layout/) (étend de EditText)
- [Material Button](https://material.io/develop/android/components/material-button/) (étend de Button)
- [Snackbars](https://material.io/develop/android/components/snackbar/)

Note : Les composants qui *remplacent* les vues classiques (*Material Button*, *Text fields*, etc.) étendent en général des vues classiques (*Button*, *EditText*, etc.) donc le code des activités / fragments n'est pas affecté.

La customisation de l'application se situe dans le fichier `style.xml`.


### ActionBar - Retour en arrière
Excepté pour l'activté principale, il faut en général que les activités de l'application affichent une *ActionBar* avec un titre et un bouton pour revenir arrière (la flèche). Pour cela pour pouvez étendre l'activité de `ChildActivity` qui va ajouter le bouton de retour en arrière à l'*ActionBar* de l'activité. 

Voir : `ui > utils > ChildActivity.java`
