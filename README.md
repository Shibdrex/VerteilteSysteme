# VerteilteSysteme
Repository für das Projekt von Verteilte Systeme
Ralf Kunath, David Bauer

This project was created for an educational purpose and is not meant to be used commercially

No guarantees that this project works

# Requirements

* docker/podman + compose

# Projekt starten

Um die Anwendung zu starten reicht ein
```
docker compose up
# oder
podman compose up
```
zu empfehlen ist
```
docker compose up --build --detach
# oder
podman compose up --build --detach
```
Damit wird sichergestellt das die Images neu gebaut werden und das Terminal dannach wieder frei ist, es laufen sehr viele Logs von kafka durch
#### Der Befehl muss auf root Ebene des Projekts ausgeführt werden damit die Kontext Pfade zu den Dockerfiles korrekt sind

Es sollten keine weiteren Schritte benötigt werden

Das erste Starten kann mehrere Minuten brauchen, da alle Images, Maven Dependencies und Node Packages heruntergeladen werden müssen

Nachdem alle Container gestartet sind lässt sich das Frontend auf
<a href=http://localhost:5500>http://localhost:5500</a> abrufen

Dort muss eine Anmeldung erfolgen, dannach kann man seine Listen dort erstellen, bearbeiten und abhaken
