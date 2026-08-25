# 📚 Sistema de Gestión de Bibliotecas — Hito 4: Backend con Spring Boot

Microservicio backend para la gestión de una biblioteca (libros, usuarios y préstamos),
construido con **Java 17**, **Spring Boot 3**, **PostgreSQL** y **Docker**, y documentado
con **OpenAPI / Swagger-UI**.

Este Hito consolida el paso del backend desacoplado hacia un microservicio productivo,
persistente y documentado, exponiendo los casos de uso mediante APIs REST semánticas,
persistiendo los datos en una base de datos relacional contenedorizada y generando
contratos técnicos seguros con OpenAPI.

## 🧱 Stack tecnológico

- **Java 17**
- **Spring Boot 3.3** (Web, Data JPA, Validation)
- **PostgreSQL 16** (contenedorizado con Docker)
- **springdoc-openapi** (Swagger-UI)
- **Lombok**
- **Maven**

## 🏗️ Arquitectura

El proyecto sigue una separación por capas (dominio / aplicación / infraestructura):

```
com.biblioteca
├── domain
│   ├── model            -> Modelos de dominio puros (sin anotaciones JPA)
│   └── exception         -> Excepciones de negocio
├── application
│   └── service            -> Casos de uso / lógica de negocio
├── infrastructure
│   ├── web
│   │   ├── controller     -> @RestController + @RestControllerAdvice
│   │   └── dto             -> DTOs de entrada/salida
│   └── persistence
│       ├── entity          -> Entidades @Entity (JPA)
│       ├── repository        -> Interfaces JpaRepository
│       └── mapper             -> Conversión Entity <-> Dominio
└── config                 -> Configuración OpenAPI
```

## ✅ Requerimientos técnicos cubiertos

1. **Endpoints REST y Manejo de Errores** — Controladores bajo `/api/v1/...` con verbos
   HTTP correctos (`GET`, `POST`, `PUT`, `PATCH`, `DELETE`) y un `GlobalExceptionHandler`
   anotado con `@RestControllerAdvice` que centraliza el manejo de errores y devuelve
   siempre un JSON unificado (`ErrorResponse`), nunca un stacktrace nativo del servidor.
2. **Virtualización y Persistencia Real** — `docker-compose.yml` que levanta PostgreSQL
   con volumen persistente. Entidades JPA en `infrastructure.persistence.entity` y
   repositorios que heredan de `JpaRepository`.
3. **Documentación OpenAPI y Perfiles** — Swagger-UI totalmente operativo, activo solo
   bajo el perfil `dev` (`application-dev.yml`) y bloqueado en el perfil `prod`
   (`application-prod.yml`).

## 🚀 Cómo levantar el proyecto

### 1. Levantar la base de datos (PostgreSQL vía Docker)

```bash
docker compose up -d
```

Esto crea el contenedor `biblioteca-postgres-db` con la base `biblioteca_db`,
usuario `dev_user` y contraseña `SecureDevPassword123`, persistiendo los datos en
un volumen Docker.

### 2. Ejecutar la aplicación en modo desarrollo

Requiere Maven 3.9+ y JDK 17 instalados localmente:

```bash
mvn spring-boot:run
```

Por defecto el perfil activo es `dev` (definido en `application.yml`). Para forzarlo
explícitamente:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Para ejecutar en modo productivo (Swagger deshabilitado):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

> Tip: si prefieres no tener Maven instalado localmente, puedes generar el wrapper con
> `mvn -N wrapper:wrapper` y luego usar `./mvnw` en su lugar.

## 📑 Documentación y pruebas de contratos

Con el perfil `dev` activo:

- **Swagger-UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/api-docs

En el perfil `prod`, ambas rutas quedan deshabilitadas (`404`).

## 🔌 Endpoints principales

| Recurso    | Método | Ruta                                | Descripción                          |
|------------|--------|--------------------------------------|---------------------------------------|
| Libros     | GET    | `/api/v1/libros`                    | Listar libros                        |
| Libros     | GET    | `/api/v1/libros/{id}`               | Obtener libro por id                 |
| Libros     | POST   | `/api/v1/libros`                    | Crear libro (201)                    |
| Libros     | PUT    | `/api/v1/libros/{id}`               | Actualizar libro                     |
| Libros     | DELETE | `/api/v1/libros/{id}`               | Eliminar libro (204)                 |
| Usuarios   | GET    | `/api/v1/usuarios`                  | Listar usuarios                      |
| Usuarios   | GET    | `/api/v1/usuarios/{id}`             | Obtener usuario por id               |
| Usuarios   | POST   | `/api/v1/usuarios`                  | Crear usuario (201)                  |
| Usuarios   | PUT    | `/api/v1/usuarios/{id}`             | Actualizar usuario                   |
| Usuarios   | DELETE | `/api/v1/usuarios/{id}`             | Eliminar usuario (204)                |
| Préstamos  | GET    | `/api/v1/prestamos`                 | Listar préstamos                     |
| Préstamos  | GET    | `/api/v1/prestamos/{id}`            | Obtener préstamo por id              |
| Préstamos  | POST   | `/api/v1/prestamos`                 | Registrar préstamo (201, valida stock)|
| Préstamos  | PATCH  | `/api/v1/prestamos/{id}/devolucion` | Registrar devolución                 |

### Manejo de errores

Todas las excepciones se traducen a un JSON unificado, por ejemplo al intentar
prestar un libro sin stock disponible:

```json
{
  "mensaje": "El libro con id [3] no tiene stock disponible para prestamo",
  "codigo": "BUSINESS_RULE_VIOLATION",
  "status": 422,
  "timestamp": "2026-08-23T10:15:30"
}
```

## 🧪 Colección de pruebas

Se recomienda auditar los endpoints con **Bruno** o **Postman** contra
`http://localhost:8080/api/v1`, o directamente mediante el botón *"Try it out"*
de Swagger-UI en modo `dev`.

## 📂 Estructura del repositorio

```
library_manager_backend_spring/
├── docker-compose.yml
├── pom.xml
├── README.md
└── src/main/java/com/biblioteca/...
```

Desarrollado por Ancayos Dev.