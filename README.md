
# 📺 TVApp – Spring Boot REST API

Application de gestion simplifiée de chaînes et programmes TV, avec fonctionnalités REST, AOP, Scheduling et Swagger UI.

---

## 🔧 Configuration requise

- Java 17+
- Spring Boot 3.x
- Base de données MySQL (XAMPP)
- Swagger UI activé
- Jackson (`@JsonIgnore`) pour éviter les boucles
- Spring AOP & Scheduling

---

## ⚙️ Démarrage

1. Lancer MySQL via XAMPP
2. Créer la base `tvdb`
3. Configurer `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tvdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
springdoc.default-produces-media-type=application/json
springdoc.default-consumes-media-type=application/json

server.port=8080

logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

4. Lancer le projet
5. Accéder à Swagger : [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Liste des Endpoints

### 1. ➕ Ajouter un utilisateur
**Méthode :** `POST`  
**URL :** `/api/tv/utilisateur`  
**Body JSON :**
```json
{
  "nom": "Mohamed",
  "profession": "ETUDIANT",
  "dateInscription": "2021-05-15"
}
```

### 2. 📺 Ajouter un programme avec une chaîne (Cascade)
**Méthode :** `POST`  
**URL :** `/api/tv/programme-chaine`  
**Body JSON :**
```json
{
  "nom": "SBOU3I",
  "chaine": {
    "nom": "TV2",
    "theme": "FAMILLE"
  }
}
```

### 3. 🔗 Ajouter un programme à une chaîne existante
**Méthode :** `POST`  
**URL :** `/api/tv/programme-chaine/{chId}`  
**Exemple :** `/api/tv/programme-chaine/2`  
**Body JSON :**
```json
{
  "nom": "NBA - PLAYOFFS"
}
```

### 4. ❤️ Affecter un programme comme favori à un utilisateur
**Méthode :** `POST`  
**URL :** `/api/tv/favori`  
**Query Parameters :**
- `prNom=NBA - PLAYOFFS`
- `usrNom=Mohamed`  
  **Exemple complet :**
```
/api/tv/favori?prNom=NBA - PLAYOFFS&usrNom=Mohamed
```

### 5. 💔 Retirer un programme des favoris d’un utilisateur
**Méthode :** `DELETE`  
**URL :** `/api/tv/favori`  
**Query Parameters :**
- `prNom=SBOU3I`
- `usrNom=Ali`  
  **Exemple complet :**
```
/api/tv/favori?prNom=SBOU3I&usrNom=Ali
```

### 6. 🔍 Filtrer les utilisateurs par profession, date d'inscription et thématique
**Méthode :** `GET`  
**URL :** `/api/tv/utilisateurs`  
**Query Parameters :**
- `p=ETUDIANT`
- `d=2020-01-01`
- `t=SPORT`  
  **Exemple complet :**
```
/api/tv/utilisateurs?p=ETUDIANT&d=2020-01-01&t=SPORT
```

### 7. ⏰ Ordonnancement automatique (Scheduler)
**Fréquence :** Toutes les 20 secondes  
**Fonction :** Affiche dans la console les chaînes les plus choisies comme favoris.  
**Exemple console :**
```
Chaine : BEIN | Favoris : 2
Chaine : TV2 | Favoris : 1
```

---

### ✅ En résumé

| Action                                | Méthode | Endpoint                                 | Paramètres / Body                        |
|---------------------------------------|---------|------------------------------------------|------------------------------------------|
| Ajouter utilisateur                   | POST    | `/api/tv/utilisateur`                    | JSON body                                 |
| Ajouter programme + chaîne            | POST    | `/api/tv/programme-chaine`               | JSON body                                 |
| Ajouter programme à chaîne existante  | POST    | `/api/tv/programme-chaine/{chId}`        | JSON body                                 |
| Affecter programme favori             | POST    | `/api/tv/favori`                         | `prNom`, `usrNom` en query params         |
| Désaffecter programme favori          | DELETE  | `/api/tv/favori`                         | `prNom`, `usrNom` en query params         |
| Lister utilisateurs filtrés           | GET     | `/api/tv/utilisateurs`                   | `p`, `d`, `t` en query params             |
| Logs chaînes populaires (console)     | SCHEDUL | Console (auto)                           | Chaque 20 sec, aucune requête             |
