# WEBSHOP

## Leírás:
Egy egyszerű webshop oldal.
A vevő online böngészhet az áruk között, majd ha vásárolni akarna, bejelentkezés után a kosarába helyezhet termékeket majd véglegesítheti a vásárlási szándékát.
A vásárló szűrhet az áruk között, hogy ezzel könnyítve gyorsabban és egyszerűbben vásárolhasson.
Az adminisztrátor új árukat adhat hozzá a raktárkészlethet vagy módosíthatja/törölheti a meglévőket.


### 4 tábla:
- **_USERS_(ID, ACCESS_RIGHT, ADDRESS, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, PHONE_NUMBER)**
- **_WARES_(ID, DESCRIPTION, MANUFACTURER, NAME, PRICE, STOCK, TYPE)**
- **_ORDERS_(ID, ORDER_DATE, STATUS, CUSTOMER_ID)**
- **_ORDER_ITEMS_(ID, AMOUNT, WARE_ID, ORDER_ID)**


### Funkcionális követelmények:
- regisztráció
- belépés
- felhasználói adatok módosítása

- árukészlet megtekintése
- szűrés árutípusra
- szűrés árhatár megadásával
- szűrés gyártóra
- rendezés ár szerint
- rendezés név szerint

- áru kosárba tétele
- áru kosárból kivétele
- kosár ürítése
- rendelés leadása
- saját rendelések listázása

- áru hozzáadása
- áru adatainak módosítása
- áru törlése


### Nem funkcionális elvárások:
- Felhasználóbarát
- Egyszerű
- Letisztult felület
- Keresési eredmények gyors megjelenítése
- Jelszavas azonosítás
- Jelszavak biztonságos tárolása


### Szerepkörök:
- Adminisztrátor
- Vásárló
- Vendég
