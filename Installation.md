# Installation.md

# Installation und Einrichtung

## Monitoring Demo mit Spring Boot, Prometheus und Grafana

---

# Inhaltsverzeichnis

1. Voraussetzungen
2. Java Installation
3. Docker Installation
4. Spring Initializr
5. Projektimport
6. Projektstruktur
7. Spring Boot Konfiguration
8. build.gradle
9. application.yml
10. Prometheus Konfiguration
11. Docker Compose
12. Spring Boot starten
13. Prometheus testen
14. Grafana einrichten
15. Dashboard erstellen
16. Troubleshooting

---

# 1. Voraussetzungen

Für die Ausführung des Projekts werden folgende Komponenten benötigt.

## Windows

| Software                | Version |
| ----------------------- | ------- |
| Windows 10 / 11         | aktuell |
| Java JDK                | 21      |
| Docker Desktop          | aktuell |
| IntelliJ IDEA Community | aktuell |
| Git                     | aktuell |

---

## Linux

| Software           | Version |
| ------------------ | ------- |
| Linux Distribution | aktuell |
| OpenJDK            | 21      |
| Docker Engine      | aktuell |
| Docker Compose     | aktuell |
| Git                | aktuell |

---

# 2. Java Installation

## Windows

Java herunterladen:

```text
https://adoptium.net/
```

Empfohlen:

```text
Eclipse Temurin JDK 21
```

Installation durchführen.

---

## Java prüfen

PowerShell öffnen:

```powershell
java -version
```

Erwartete Ausgabe:

```text
openjdk version "21"
```

---

## Linux

Ubuntu / Debian:

```bash
sudo apt update
sudo apt install openjdk-21-jdk
```

Java prüfen:

```bash
java -version
```

---

## Screenshot

![java-version.png](Images/java-version.png)

---

# 3. Docker Installation

## Windows

Docker Desktop herunterladen:

```text
https://www.docker.com/products/docker-desktop/
```

Installation durchführen.

Danach Docker Desktop starten.

---

## Docker prüfen

PowerShell:

```powershell
docker version
```

Erwartung:

```text
Client:
Server:
```

---

## Linux

Ubuntu:

```bash
sudo apt install docker.io
```

Docker Compose:

```bash
sudo apt install docker-compose-plugin
```

Prüfen:

```bash
docker version
docker compose version
```

---

# 4. Spring Initializr

Projekt erstellen über:

```text
https://start.spring.io
```

---

## Einstellungen

```text
Project: Gradle
Language: Java
Spring Boot: 3.5.x

Group: at.fhj
Artifact: monitoring-demo

Java: 21
```

---

## Dependencies

Folgende Dependencies hinzufügen:

```text
Spring Web
Spring Boot Actuator
Prometheus
```

---

## Screenshot

![spring-initializr.png](Images/spring-initializr.png)

---

# 5. Projektimport

Projekt entpacken.

Danach IntelliJ öffnen:

```text
File
→ Open
```

Projektordner auswählen.

---

## Wichtiger Hinweis

Der Projektordner muss folgende Dateien enthalten:

```text
build.gradle
settings.gradle
gradlew
gradlew.bat
```

---

## Screenshot

![project-structure.png](Images/project-structure.png)

---

# 6. Projektstruktur

Die finale Projektstruktur sieht wie folgt aus:

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
        │               ├── exception
        │               ├── service
        │               └── MonitoringDemoApplication
        │
        └── resources
            └── application.yml
```

---

## Wichtige Anmerkung

Die Package-Struktur muss echte Ordner verwenden:

```text
at/fhj/monitoringdemo
```

Nicht:

```text
at.fhj.monitoringdemo
```

Dies führte während der Entwicklung zu einem Fehler:

```text
ClassNotFoundException
```

---

# 7. Spring Boot Konfiguration

## MonitoringDemoApplication.java

Die Hauptklasse befindet sich unter:

```text
src/main/java/at/fhj/monitoringdemo
```

---

## Screenshot

![monitoring-demo-application.png](Images/monitoring-demo-application.png)

---

# 8. build.gradle

Folgende Dependencies werden benötigt:

```gradle
dependencies {

    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation 'org.springframework.boot:spring-boot-starter-actuator'

    implementation 'io.micrometer:micrometer-registry-prometheus'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

---

## Erklärung

### Spring Web

REST-Endpunkte.

### Spring Boot Actuator

Health Checks und Metriken.

### Micrometer

Prometheus Integration.

---

# 9. application.yml

Datei:

```text
src/main/resources/application.yml
```

```yaml
server:
  port: 8080

spring:
  application:
    name: monitoring-demo

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus,mappings

  endpoint:
    health:
      show-details: always

  prometheus:
    metrics:
      export:
        enabled: true
```

---

## Erklärung

### Health Endpoint

```text
/actuator/health
```

### Prometheus Endpoint

```text
/actuator/prometheus
```

### Mappings Endpoint

```text
/actuator/mappings
```

---

# 10. Prometheus Konfiguration

Datei:

```text
prometheus.yml
```

```yaml
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: "spring-boot"

    metrics_path: "/actuator/prometheus"

    static_configs:
      - targets:
          - "host.docker.internal:8080"
```



---

## Erklärung

Prometheus liest alle 5 Sekunden die Metriken der Spring-Boot-Anwendung aus.

---

## Speicherort der Datei

Die Datei `prometheus.yml` muss im Projektroot gespeichert werden.

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
```

Prometheus verwendet diese Datei als zentrale Konfiguration und liest daraus die zu überwachenden Systeme.

---

## Erklärung der Konfiguration

```yaml
global:
  scrape_interval: 5s
```

Legt fest, dass Prometheus alle 5 Sekunden neue Metriken von der Anwendung abruft.

---

```yaml
metrics_path: "/actuator/prometheus"
```

Definiert den Endpoint der Spring-Boot-Anwendung, von dem die Metriken gelesen werden.

---

```yaml
targets:
  - "host.docker.internal:8080"
```

Legt fest, auf welchem Host und Port die Spring-Boot-Anwendung erreichbar ist.

Da Prometheus innerhalb eines Docker-Containers läuft, kann nicht direkt auf `localhost` zugegriffen werden. Deshalb wird `host.docker.internal` verwendet.

---

# 11. Docker Compose

Datei:

```text
docker-compose.yml
```

```yaml
services:

  prometheus:
    image: prom/prometheus

    container_name: monitoring-prometheus

    ports:
      - "9090:9090"

    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml

    extra_hosts:
      - "host.docker.internal:host-gateway"

  grafana:
    image: grafana/grafana

    container_name: monitoring-grafana

    ports:
      - "3000:3000"

    depends_on:
      - prometheus
```

## Was ist Docker Compose?

Docker Compose ermöglicht die Verwaltung mehrerer Container über eine einzige Konfigurationsdatei.

In diesem Projekt werden zwei Container benötigt:

- Prometheus
- Grafana

Ohne Docker Compose müssten beide Container manuell gestartet werden.

Mit Docker Compose genügt ein einzelner Befehl:

```bash
docker compose up -d
```

---

## Speicherort der Datei

Die Datei `docker-compose.yml` muss im Projektroot gespeichert werden.

```text
monitoring-demo
│
├── docker-compose.yml
├── prometheus.yml
├── src
│
└── ...
```

---

## Erklärung der Konfiguration

### image

```yaml
image: prom/prometheus
```

Legt fest, welches Docker Image verwendet werden soll.

Docker lädt das Image automatisch aus Docker Hub herunter.

---

### container_name

```yaml
container_name: monitoring-prometheus
```

Definiert einen festen Namen für den Container.

Dadurch ist der Container später leichter identifizierbar.

---

### ports

```yaml
ports:
  - "9090:9090"
```

Bedeutung:

```text
HOST:CONTAINER
```

Dadurch wird der interne Port des Containers nach außen freigegeben.

Prometheus ist anschließend unter

```text
http://localhost:9090
```

erreichbar.

---

### volumes

```yaml
volumes:
  - ./prometheus.yml:/etc/prometheus/prometheus.yml
```

Bindet die lokale Konfigurationsdatei in den Container ein.

Änderungen an der lokalen Datei werden dadurch direkt vom Container verwendet.

---

### extra_hosts

```yaml
extra_hosts:
  - "host.docker.internal:host-gateway"
```

Ermöglicht dem Container die Kommunikation mit dem Host-System.

Dies ist insbesondere unter Linux wichtig.

---

### depends_on

```yaml
depends_on:
  - prometheus
```

Grafana wird erst gestartet, nachdem Prometheus gestartet wurde.

---

# Neuer Abschnitt – Docker Container starten

## Container starten

Im Projektverzeichnis:

### Windows

```powershell
docker compose up -d
```

### Linux

```bash
docker compose up -d
```

---

## Container prüfen

```bash
docker ps
```

Erwartete Ausgabe:

```text
monitoring-prometheus
monitoring-grafana
```

---

## Container stoppen

```bash
docker compose down
```

---

## Screenshot

![docker_container.png](Images/docker_container.png)
---

---

# 12. Spring Boot starten

## IntelliJ

Datei öffnen:

```text
MonitoringDemoApplication.java
```

Dann:

```text
Run → MonitoringDemoApplication
```

---

## Windows

```powershell
.\gradlew.bat bootRun
```

---

## Linux

```bash
./gradlew bootRun
```

---

## Test

```text
http://localhost:8080/api/hello
```

---

## Screenshot

![api-endpoints-hello.png](Images/api-endpoints-hello.png)

---

# 13. Prometheus testen

Prometheus öffnen:

```text
http://localhost:9090
```

---

## Targets

```text
http://localhost:9090/targets
```

Status:

```text
UP
```

---

## Screenshot

![prometheus-targets.png](Images/prometheus-targets.png)

---

## Query testen

```promql
demo_hello_requests_total
```

---

## Screenshot

![prometheus-query-demo-hello.png](Images/prometheus-query-demo-hello.png)

---

# 14. Grafana einrichten

Grafana öffnen:

```text
http://localhost:3000
```

---

## Login

```text
admin
admin
```

---

## Data Source hinzufügen

```text
Connections
→ Data Sources
→ Add Data Source
→ Prometheus
```

URL:

```text
http://prometheus:9090
```

---

## Screenshot

![grafana-datasource.png](Images/grafana-datasource.png)

---

# 15. Dashboard erstellen

## Hello Requests

Query:

```promql
demo_hello_requests_total
```

Visualisierung:

```text
Stat
```

---

## Error Requests

```promql
demo_error_requests_total
```

Visualisierung:

```text
Stat
```

---

## JVM Memory

```promql
jvm_memory_used_bytes
```

Visualisierung:

```text
Time Series
```

---

## HTTP Requests

```promql
http_server_requests_seconds_count
```

Visualisierung:

```text
Time Series
```

---

## Business Votes

```promql
demo_votes_total
```

Visualisierung:

```text
Gauge
```

Empfohlene Einstellungen:

```text
Min: 0
Max: 100
```

---

## Dashboard Screenshot

![grafana-dashboard.png](Images/grafana-dashboard.png)

---

# 16. Troubleshooting

## Docker Desktop läuft nicht

Fehler:

```text
dockerDesktopLinuxEngine
```

Lösung:

Docker Desktop starten.

Prüfen:

```powershell
docker version
```

---

## JAVA_HOME nicht gesetzt

Fehler:

```text
ERROR: JAVA_HOME is not set
```

Lösung:

PowerShell:

```powershell
$env:JAVA_HOME="C:\Users\<Benutzer>\.jdks\ms-21.0.11"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
```

Prüfen:

```powershell
java -version
```

---

## Spring Boot findet Hauptklasse nicht

Fehler:

```text
ClassNotFoundException:
at.fhj.monitoringdemo.MonitoringDemoApplication
```

Ursache:

Falsche Package-Struktur.

Richtig:

```text
at/fhj/monitoringdemo
```

---

## /api/vote oder /api/status liefert 404

Lösung:

* Projekt neu bauen
* Anwendung neu starten
* IntelliJ Cache löschen

```text
File
→ Invalidate Caches
→ Restart
```

---

## Prometheus Target DOWN

Prüfen:

```text
http://localhost:8080/actuator/prometheus
```

Wenn keine Metriken erscheinen:

* Spring Boot läuft nicht
* Port falsch
* prometheus.yml falsch konfiguriert

---

## Grafana verbindet sich nicht mit Prometheus

Falsch:

```text
http://localhost:9090
```

Richtig:

```text
http://prometheus:9090
```

Da Grafana innerhalb von Docker läuft, muss der Docker-Service-Name verwendet werden.
