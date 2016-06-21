# Deploiement
## Base de données
- Installer MySQL si nécessaire : `sudo apt-get install mysql-server`
- Créer un utilisateur pour Spicy world si nécessaire :
  * `mysql -u root -p`
  * `CREATE USER 'spicyworld'@'localhost' IDENTIFIED BY 'dune';`
  * `GRANT ALL PRIVILEGES ON * . * TO 'spicyworld'@'localhost';`
- Importer le schéma décrit dans `schema.sql` :
  * `mysql -u spicyworld -p < schema.sql`	