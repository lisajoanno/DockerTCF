# Base Tomee+
FROM tomee:8-jre-1.7.3-plus

# Copie du .war vers le container, dans le dossier lancé automatiquement par tomee au lancement du container.
ADD j2e/target/tcf-backend.war /usr/local/tomee/webapps/tcf-backend.war

EXPOSE 8080 
