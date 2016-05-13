# DevOps - Docker
Deploiement via Docker de TCF

## Présentation de l’équipe

* Arnaud GARNIER
* Lisa JOANNO
* Grégory ROBIN

## Présentation du projet

Nous avons implémenté deux versions du projet :

* la version avec les containers .Net et j2e lancés séparément.
* la version avec ces deux containers composés.

Nous avons laissé le client à la racine du projet afin de pouvoir essayer les containers.

## Containers .NET et J2E séparés

Les deux images sont présentes dans le dossier images/.
Le Dockerfile du container du service de paiement est dans img-payment.
Pour build et lancer le container du paiement : 

    cd images/img-payment
    docker build -t <username>/payment .
    docker run -it -p 9090:9090 <username>/payment

On expose le port 9090, et on rend le terminal du container intéractif.
    
Le Dockerfile du container des webservices est dans img-j2e.
Pour build et lancer le container du j2e : 

    cd images/img-j2e
    docker build -t <username>/j2e .
    docker run --net="host" -p 8080:8080 <username>/j2e

On expose le port 8080 et on a besoin de spécifier --net="host" afin que le serveur j2e puisse également être client du service de paiement.

## Docker-compose

Dans le dossier images/ se trouve le docker-compose.yml.
Pour lancer la composition :

    cd images/
    docker-compose up

Et pour rebuild les containers :

    sudo docker-compose up --build
