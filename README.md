# TP2_Chat

# AUTHORS
AMAL Mouataz : mouataz.amal@etu-univ.grenoble.alpes.fr
LONGA Benjamin : benjamin.longa@etu-univ.grenoble.alpes.fr

# /!\ Warning /!\
Cette application de chat ne peut être utilisée qu'à partir d'un terminal non-Windows
(pas d'un IDE) en raison de certaines fonctionnalités.

# Architecture
Le serveur enregistre deux interfaces au Registre RMI :
	- Chat : interface permettant au client de publier ses messages sur le chat (méthode
		publish), d'envoyer un message privé à un utilisateur (méthode whisper) et
		d'afficher l'historique des conversations (fonction loadHistory).
		Cette interface comporte également la méthode broadcast permettant de diffuser le
		message aux autres utilisateurs.
		L'implémentation de cette interface gère l'historique des messages.
	- RegistryClients : interface permettant au client de s'enregistrer auprès du serveur
		(fonction register) et de se retirer des inscrits lorsqu'il quitte le chat
		(méthode unregister).

Le client envoie directement au serveur une interface :
	- Client_itf : interface permettant au serveur d'envoyer un message au client
		(méthode receive), de récupérer le nom du client (fonction getName) et de fixer
		sa couleur à l'enregistrement (méthode setColor).

# Définition du CLASSPATH & Compilation
Afin de définir le CLASSPATH, modifiez le chemin dans le fichier classpath.sh afin qu'il
reflète votre arborescence. Entrez ensuite la commande "source classpath.sh" dans chaque
terminal utilisé.
Pour compiler les fichiers sources, entrez "./compil.sh", les fichiers .class et .jar
sont ainsi générés.

# Lancement du chat
Dans le terminal "serveur", commencez par entrer la commande "rmiregistry &" afin de
lancer le registre RMI. Entrez ensuite "java nf.ChatServer" pour lancer le serveur. Un
message "Serveur prêt" devrait apparaître si tout s'est correctement déroulé.

Dans les terminaux "clients", entrez "java nf.ChatClient localhost". N'oubliez pas qu'il
faut définir le CLASSPATH pour chaque nouveau terminal.

# Utilisation du chat
Lorsque vous vous connectez au serveur, un nom vous est demandé. Il doit être composé
de caractères alphabétiques uniquement. Si le nom est correct et libre (non utilisé
sur le chat actuellement), le service de chat est disponible.
La liste des commandes s'affiche alors. Vous pouvez désormais discuter.
Afin de quitter le chat, le bon usage est d'utiliser la commande /quit.

# Fonctionnalités
Ce système de chat possède les fonctionnalités suivantes :
	- Restriction des noms d'utilisateurs aux caractères alphabétiques
	- Historique permanent des conversations accessible via la commande /history
		(enregistré dans le fichier history.txt)
	- Colorisation des messages (couleur attribuée au hasard à l'enregistrement de
		l'utilisateur) - couleur interchangeable via la commande /color
	- Affichage de la liste des utilisateurs via la commande /list
	- Une commande /help afin d'afficher les commandes disponibles
	- Notification des connexions/déconnexions des autres utilisateurs
	- Système de messages privés via la commande /w
