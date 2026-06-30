# Hnefatafl ♟️

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)

Implementación en Java puro (sin frameworks) del **Hnefatafl**, un juego de tablero vikingo asimétrico anterior al ajedrez: un bando defiende al rey en el centro de un tablero 11x11 y debe sacarlo a una esquina, mientras el otro bando, con más piezas pero más débiles, intenta atraparlo antes de que escape.

---

## Características

- **Tablero 11x11** con representación por consola y colores ANSI.
- **Dos modos de juego**: humano contra humano, humano contra máquina o máquina contra máquina.
- **IA con búsqueda a 2 niveles**: para cada movimiento posible, simula la mejor respuesta del rival y elige el que deja la mejor posición tras esa respuesta (minimax simplificado). La posición se valora con una heurística de material (piezas restantes de cada bando) y la distancia del rey a la esquina más cercana.
- **Reglas completas**: movimiento ortogonal, captura por flanqueo (rodear una pieza rival entre dos propias), condición de victoria del rey al alcanzar una esquina.
- **Tests unitarios** con JUnit 5 (18 tests parametrizados) cubriendo movimientos, capturas y condiciones de victoria.

---

## Arquitectura

```
main/      ← Hnefatafl (punto de entrada), ConsoleInput (lectura validada por consola)
game/      ← Game (bucle de turnos), Player (abstracta), Human, Machine (IA)
model/     ← Board, Movement, Piece, Position, Square, Color
enums/     ← PieceType, Directions
test/      ← HnefataflTest (JUnit 5)
```

`Player` es una clase abstracta con dos implementaciones: `Human` (lee la jugada por consola) y `Machine` (decide la jugada con la búsqueda descrita arriba). `Game` no conoce la diferencia entre ambas, solo invoca `decideMove()`.

---

## Cómo ejecutarlo

Proyecto sin Maven/Gradle (configurado originalmente como proyecto Eclipse). Para ejecutarlo desde línea de comandos:

```bash
git clone https://github.com/rafamartinez99/Hnefatafl.git
cd Hnefatafl
```

**Opción A — desde un IDE (recomendado):** importa la carpeta como proyecto existente en Eclipse o IntelliJ (ya incluye `.classpath`/`.project`) y ejecuta `main/Hnefatafl.java`.

**Opción B — desde terminal:**

```bash
# Linux / macOS / Git Bash en Windows
javac -d bin -cp src $(find src -name "*.java" -not -path "*/test/*")
java -cp bin main.Hnefatafl
```

```powershell
# Windows PowerShell
javac -d bin -cp src (Get-ChildItem -Recurse -Path src -Filter *.java | Where-Object { $_.FullName -notmatch '\\test\\' } | ForEach-Object FullName)
java -cp bin main.Hnefatafl
```

> [!NOTE]
> Los colores ANSI del tablero requieren una terminal que los soporte (Windows Terminal, PowerShell moderno, la terminal de la mayoría de IDEs, o cualquier terminal Linux/macOS). En `cmd.exe` clásico pueden no renderizarse correctamente.

Para ejecutar los tests (requiere el JAR incluido en `lib/`):

```bash
java -jar lib/junit-platform-console-standalone-1.10.2.jar -cp bin --select-package test
```

---

## Decisiones técnicas

- **IA por simulación, no por fuerza bruta total**: en vez de explorar todo el árbol de jugadas, `Machine` simula sus movimientos candidatos y, para cada uno, la mejor respuesta del rival usando la misma heurística — suficiente para jugar de forma razonable sin necesitar poda alfa-beta ni profundidad mayor.
- **`Board.copy()` para simulación segura**: la IA nunca modifica el tablero real; cada candidato se evalúa sobre una copia profunda del tablero.
- **`Movement` separa legalidad silenciosa de legalidad explicada**: `isLegal()` se usa internamente por la IA al explorar decenas de movimientos sin imprimir nada por consola; `getIllegalReason()` se usa solo cuando un humano introduce una jugada inválida, para explicarle por qué.
- **Player como abstracción única**: `Game` trata a `Human` y `Machine` exactamente igual a través de `Player.decideMove()`, sin ramas de código distintas según el tipo de jugador.

---

## Documentación adicional

- [`doc/`](doc/index.html) — Javadoc completo de todas las clases.
- [`diagrams/`](diagrams) — diagrama de casos de uso y diagrama de clases.
- [`blackbox/`](blackbox) — documento de pruebas de caja negra.

---

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el fichero [LICENSE](LICENSE) para más información.
