# Projektkalkulationsværktøj – Alpha Solutions

Dette projekt er udviklet som en del af 2. semester eksamensprojektet på datamatikeruddannelsen.
Systemet bruges til at give overblik over projekter, opgaver og status for både projektledere og udviklere.

## Teknologier
- Java 21
- Spring Boot
- Thymeleaf
- JDBC
- MySQL
- Maven

## Forudsætninger
For at køre projektet lokalt skal følgende være installeret:
- Java 21
- Maven
- MySQL eller H2
- IntelliJ IDEA (anbefalet)

## Miljøer
Projektet anvender Spring Boot profiles:
- `dev` til lokal udvikling - H2 database
- `prod` til deployment i Azure - MySQL database

## Deployment
Applikationen er deployet i Azure og anvender en ekstern MySQL-database.

https://projectcalculationtool-gnffa2h3f6fwe3b3.norwayeast-01.azurewebsites.net/

## Projektstruktur
Projektet er opdelt i controller-, service- og repository-lag for at holde ansvar og funktionalitet adskilt.
