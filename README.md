# Smart Logistics Dashboard

Projekat iz predmeta IT355 - Veb sistemi 2 (Drugi projektni zadatak).

## Opis aplikacije
Smart Logistics Dashboard je veb aplikacija namenjena efikasnom upravljanju logističkim procesima, organizaciji pošiljki, skladišta, vozila i vozača. Omogućava centralizovano praćenje statusa pošiljki i osnovne statističke podatke unutar dashboard interfejsa.

## Tehnologije
- Java / Spring Boot
- Spring MVC
- Thymeleaf (Nivo pogleda)
- CSS stilizacija

## Funkcionalnosti
- Kompletan CRUD za upravljanje pošiljkama (Shipments)
- Automatsko generisanje jedinstvenih tracking kodova za nove pošiljke
- Filtriranje pošiljki po statusu i prioritetu
- Dashboard sa statističkim karticama (ukupan broj pošiljki, aktivna vozila, aktivni vozači)
- Application Scope čuvanje podataka u memoriji (bez baze podataka)

## Uputstvo za pokretanje i korišćenje
1. Klonirajte projekat sa GitHub-a.
2. Otvorite projekat u razvojnom okruženju (npr. IntelliJ IDEA).
3. Pokrenite aplikaciju pokretanjem glavne klase `SmartLogisticsDashboardApplication` ili komandom `./mvnw spring-boot:run` u terminalu.
4. Otvorite browser i idite na adresu: `http://localhost:8080`
5. Kroz navigacioni meni pristupite dashboard-u ili pregledu svih pošiljki gde možete vršiti dodavanje, izmenu, brisanje i filtriranje podataka.
