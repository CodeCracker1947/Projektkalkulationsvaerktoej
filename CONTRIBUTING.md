# Contributing

Dette dokument beskriver, hvordan man kan bidrage til projektet.

## Opsætning
1. Klon repository
2. Åbn projektet i IntelliJ IDEA
3. Sørg for at Java 21 er valgt
4. Kør projektet med `dev`-profilen

## Projektstruktur
- Controller: håndterer HTTP requests
- Service: indeholder forretningslogik
- Repository: håndterer databaseadgang

## Retningslinjer
- Arbejd i separate branches
- Lav små commits med beskrivende beskeder
- Test ændringer lokalt før de merges
- Følg den eksisterende struktur i projektet

## Tests
Projektet indeholder unit tests og integrationstests, som bør køre uden fejl før merge.
