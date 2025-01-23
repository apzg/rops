# Uruchomienie

Uruchomienie aplikacji z poziomu interpretera poleceń (cmd) lub terminala:
- mvnw clean install spring-boot:run

Dostęp do API po przez Swaggera:
- http://localhost:8080/swagger-ui/index.html

Dostęp do API wymaga zalogowania użytkownikiem 'user' i hasłem 'password'.

# Opis zmian

Autentykacja i autoryzacja nie były częścią zadania do realizacji, stąd nie zostały zaimplementowane, a jedynie wykorzystany został InMemoryUserDetailsManager z zahardkodowanym loginem i hasłem.

By ułatwić użytkowanie i testowanie prototypu aplikacji, wykorzystana została baza H2 uruchamiana w pamięci. Przy każdorazowym uruchomieniu baza jest zasilana danymi za pomocom pliku data.sql.

Przy budowie API zarówno Project i Task potraktowane zostały jako zasoby, z czego Task może istnieć wyłącznie w ramach zasobu Project. Nie zostało opisane w zadaniu jak oba zasoby będą konsumowane, stąd została wybrana konwencja popularna w aplikacjach o podobnym profilu (np. Jira). 
W tym celu operacje obu zasobów zostały rozdzielone do odpowiednich kontrolerów, a API odpowiednio zagnieżdżone.

Kod został pogrupowany zgodnie z konwencją MVC, stąd pakiety:
 - controller - wyodrębniona z warstwy kontrolera specyfikacja API z mapowaniem obiektów z/do DTO
 - service - wyodrębniona z warstwy kontrolera logika biznesowa aplikacji operująca na właściwym modelu/entity
 - repository - wyodrębnione z warstwy modelu obiekty dostępu do danych
 - entity - wyodrębnione z warstwy modelu klasy reprezentujące bazodanowe encje, właściwy model aplikacji

Ze względu na wykorzystanie obiektow DTO, konieczne było wykorzystanie wzorca mappera. Wzorzec buider nie powinien być wykorzystywany w przypadku DTO.

Komentowanie kodu źródłowego powinno być ograniczone do minimum, a czytelność uzyskana po przez wypracowanie wspólnych konwencji w ramach danego projektu. Zamiast dokumentacji kodu, zostało udokumentowane API przy pomocy Swaggera, co docelowo powinno dobrze poslużyć także jako dokumentacja kodu źródłowego.

Uzupełniono operacje CRUD dla Project i Task, gdzie oba zasoby umożliwiają pobranie listy z paginacją. Kryteria filtracji zadań (Task) nie zostały sprecyzowane, stąd nie został wprowadzony rozbudowany mechanizm filtracji, a w przypadku podania wartości dla jednego z filtrów, wynik wyszukiwania zostanie zawężony do rekordów o zadanej wartości (operacja equal).

Wprowadzono częściową walidację danych, DTO. Walidacja nie została wskazana w opisie, dodana została na potrzeby demonstacji globalnej obsługi błędów. Obsługa błędów odbywa się w jednym miejscu w celu zapewnienia spójności API.

Wprowadzono logowanie błędów na poziomie GlobalExceptionHandler, by zmienić poziom logowania należy zmienić wartość 'application.properties':
 - logging.level.pl.poznan.demo.exception

Dodano testy jednostkowe dla serwisów i części komponentów, a takze testy integracyjne dla kontrolerów i repozytoriów. Dla ułatwienia pracy z prototypem, testy integracyjne korzystają z tej samej predefiniowanej bazy, jaka jest wykorzystana po starcie aplikacji, a parametry testów zostały zahardkodowane.

# Zadanie

Pracujesz nad aplikacją do zarządzania projektami, w której każdy projekt ma przypisane zadania.
Kod wykorzystuje REST API, Spring Data JPA oraz relację między encjami Project i Task. Twoim zadaniem jest:

## Poprawienie istniejących problemów:

- Wydajność: Zoptymalizuj operacje na bazie danych, szczególnie związane z relacją Project - Task. Upewnij się, że aplikacja unika problemów takich jak n+1 queries, niepotrzebne pobieranie dużych ilości danych, czy brak paginacji w zapytaniach.
- Błędy: Zidentyfikuj i popraw potencjalne błędy w kodzie, np. błędne mapowania encji, niespójności w danych, brak obsługi wyjątków.
- Czystość kodu: Uporządkuj kod, stosując dobre praktyki programistyczne, takie jak:
  - Rozdzielenie logiki na warstwy (kontroler, serwis, repozytorium),
  - Zastosowanie wzorców projektowych, np. Builder do konstruowania żłożonych obiektów czy DTO dla przesyłania danych między warstwami,
  - Dodanie odpowiedniej dokumentacji kodu i/lub komentarzy.

## Dodanie nowej funkcjonalności:

- Zaimplementuj brakujące operacje CRUD dla Project i Task (Create, Read, Update, Delete) wraz z odpowiednimi endpointami REST API.
- Stwórz możliwość filtrowania zadań (encja Task) przypisanych do projektu na podstawie różnych kryteriów, takich jak:
  - Status zadania (np. TODO, IN_PROGRESS, DONE),
  - Priorytet,
  - Termin realizacji (dueDate).

## Testowanie:

- Dodaj testy jednostkowe i integracyjne dla nowej funkcjonalności oraz poprawionych metod (przy użyciu JUnit lub innego narzędzia do testowania).
- Upewnij się, że aplikacja działa poprawnie w środowisku lokalnym, w tym integracja z bazą danych (np. H2 dla środowiska deweloperskiego).

## Przygotowanie środowiska:

Przygotuj aplikację do uruchomienia w lokalnym środowisku deweloperskim. Wymagania:
- Plik konfiguracyjny (np. application.properties lub application.yml) z ustawieniami dla lokalnej bazy danych,
- Instrukcja uruchomienia w pliku README.md.

## Publikacja kodu:

- Umieść kod w repozytorium Git (np. GitHub lub GitLab).
- Upewnij się, że repozytorium zawiera:
  - Cały kod źródłowy,
  - Pliki konfiguracyjne,
  - Plik README.md z instrukcją uruchomienia i opisem wprowadzonych zmian,
  - Plik .gitignore z odpowiednimi wykluczeniami (np. target/, *.log, *.class).

# Dodatkowe wskazówki:

- Upewnij się, że baza danych i encje Project oraz Task są poprawnie skonfigurowane w relacji (np. @OneToMany / @ManyToOne).
- W endpointach API zwracaj czytelne odpowiedzi, stosując wzorzec DTO, aby nie eksponować wewnętrznych szczegółów implementacji encji.
- Zadbaj o logowanie ważnych operacji w aplikacji.

Na koniec prześlij link do zdalnego repozytorium, aby umożliwić weryfikację Twojej pracy.