# Monitoring Demo mit Spring Boot, Prometheus und Grafana

## Projektbeschreibung

Dieses Projekt demonstriert die Umsetzung moderner Monitoring-Konzepte in einer Spring-Boot-Anwendung.

Im Rahmen der Lehrveranstaltung **Software Architektur** wurde eine Beispielanwendung entwickelt, welche technische sowie fachliche Metriken erzeugt und visualisiert.

Die Anwendung stellt mehrere REST-Endpunkte bereit und nutzt Spring Boot Actuator sowie Micrometer zur Erfassung von Monitoring-Daten. Diese werden anschließend von Prometheus gesammelt und in Grafana über Dashboards visualisiert.

Ziel des Projekts ist es, die grundlegenden Konzepte des Monitorings in modernen Software-Architekturen praktisch zu demonstrieren und die Unterschiede zwischen technischem Monitoring und Business Monitoring aufzuzeigen.

---

## Verwendete Technologien

### Backend

* Java 21
* Spring Boot
* Spring Boot Actuator
* Micrometer

### Monitoring

* Prometheus
* Grafana

### Infrastruktur

* Docker
* Docker Compose

### Entwicklung

* IntelliJ IDEA Community Edition
* Git

---

## Monitoring-Konzepte

Im Projekt werden folgende Monitoring-Konzepte umgesetzt:

### Health Monitoring

Überprüfung des Systemzustands über:

```text
/actuator/health
```

### Request Monitoring

Überwachung von:

* Anzahl der Requests
* HTTP Traffic
* Antwortzeiten

### Error Monitoring

Überwachung von:

* Exceptions
* HTTP 500 Fehlern
* Fehlgeschlagenen Requests

### Resource Monitoring

Überwachung von:

* JVM Speicherverbrauch
* Ressourcenverbrauch der Anwendung

### Business Monitoring

Überwachung fachlicher Kennzahlen.

Im Rahmen der Demo wird dies über einen Vote-Counter simuliert:

```text
demo_votes_total
```

---

## Architekturübersicht

```text
                    +------------------+
                    |     Benutzer     |
                    +--------+---------+
                             |
                             |
                             v
                    +------------------+
                    |   Spring Boot    |
                    |   Application    |
                    +--------+---------+
                             |
                  Metrics via Actuator
                             |
                             v
                    +------------------+
                    |    Prometheus    |
                    +--------+---------+
                             |
                             |
                             v
                    +------------------+
                    |     Grafana      |
                    +------------------+
```

### Architekturdiagramm

![architecture-overview.png](Images/architecture-overview.png)

---

## Projektstruktur

```text
monitoring-demo
│
├── docker-compose.yml
├── prometheus.yml
├── README.md
├── Installation.md
├── Dokumentation.md
│
└── src
    └── main
        ├── java
        │   └── at
        │       └── fhj
        │           └── monitoringdemo
        │               ├── controller
        │               ├── service
        │               ├── exception
        │               └── MonitoringDemoApplication
        │
        └── resources
            └── application.yml
```

### Projektstruktur Screenshot

![project-structure.png](Images/project-structure.png)

---

## Schnellstart

### 1. Spring Boot starten

Windows

```powershell
.\gradlew.bat bootRun
```

Linux

```bash
./gradlew bootRun
```

---

### 2. Docker Container starten

```bash
docker compose up -d
```

---

### 3. Anwendungen öffnen

#### Spring Boot

```text
http://localhost:8080
```

#### Prometheus

```text
http://localhost:9090
```

#### Grafana

```text
http://localhost:3000
```

---

## Wichtige Endpunkte

| Endpoint               | Beschreibung          |
| ---------------------- | --------------------- |
| `/api/hello`           | Erfolgreicher Request |
| `/api/slow`            | Langsame Antwort      |
| `/api/error`           | Fehler simulieren     |
| `/api/vote`            | Business Event        |
| `/api/status`          | Statusabfrage         |
| `/actuator/health`     | Health Check          |
| `/actuator/prometheus` | Prometheus Metriken   |

---

## Dashboard Vorschau

![grafana-dashboard.png](Images/grafana-dashboard.png)

---

## Dokumentation

Für detaillierte Informationen stehen folgende Dokumente zur Verfügung:

### Installation

Vollständige Installations- und Einrichtungsanleitung:

[Installation](Installation.md)

### Projektdokumentation

Fachliche Dokumentation mit:

* Motivation
* Architektur
* Monitoring-Konzepte
* Demonstration
* Code-Erklärung

[Dokumentation](Dokumentation.md)

---

### Repository Link

[FH_Repo](https://git-iit.fh-joanneum.at/SWD24W/monitoring_demo_software_architectures_swd24)
[Github_Repo](https://github.com/Aleskhiin/SWD24_Monitoring_Demo_Software_Architectures)
---

## Autoren

* Florian Fuchs
* Dominik Bliem-Zupansky

---

## Lehrveranstaltung

Software Architektur – SWD2024
FH Joanneum

