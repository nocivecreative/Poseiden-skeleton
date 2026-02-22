# OpenClassrooms - Cursrus Dev Java #
Ce projet à été créé dans le cadre de ma formation **Développeur d'application Java** dispensée par [OpenClassrooms](https://openclassrooms.com/)

## Contexte
> ### Étudiant  : **Franck Mounier** ###
> ### Projet : P7 - Complétez votre backend pour rendre votre application plus sécurisée ###
> ### Type : Livrable ###
> #### Repo source : [Lien github](https://github.com/OpenClassrooms-Student-Center/JavaDA_PROJECT7_RESTAPI) ####
> #### Date de démarrage du projet : 06/02/2026 ####

# Poseidon Capital Solution
## Technical:

1. Spring Boot 4.0.2
2. Java 25 
3. Thymeleaf 3.1+
4. Bootstrap v.4.3.1 


## DataBase init
> Mysql 8+
> Script d'initialisation : doc\data_v2.sql

2 utilisateurs test sont créés :

> User
> login : user
> password : Azerty12*

> Administrator
> login : admin
> password : Azerty12*


### Variable à modifier/créer :

Fichier : application.properties

Binding avec datasource :
> spring.datasource.poseidon.url=jdbc:mysql://localhost:3307/poseidon?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true

Username SQL :
> spring.datasource.poseidon.username=[USERNAME]

### injection de password DB : via variable d'environnement système :
> (SPRING_DATASOURCE_POSEIDON_PASSWORD) ou dans application.properties : spring.datasource.poseidon.password=[PASSWORD]

## Démarrage de l'application :

> mvn spring-boot:run

## Execution des tests :

> mvn clean test 

## Rapport de couverture Jacoco :

> mvn jacoco:report