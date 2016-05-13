# Base ubuntu
FROM ubuntu:14.04

# Installation de MONO pour lancer le serveur
RUN sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF
RUN echo "deb http://download.mono-project.com/repo/debian wheezy main" | sudo tee /etc/apt/sources.list.d/mono-xamarin.list
RUN apt-get -y update
RUN apt-get -y install mono-complete

# Copie du .exe
COPY dotNet/server.exe /usr/src

EXPOSE 9090

# Lancement du serveur
CMD [ "mono",  "/usr/src/server.exe" ]

