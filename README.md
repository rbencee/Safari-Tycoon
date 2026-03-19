🦁 Safari Tycoon – 2D Park Simulator
<img width="1913" height="960" alt="image" src="https://github.com/user-attachments/assets/ab34298c-0185-435e-9b56-4ddc750452cc" />


Safari Tycoon is a real-time tycoon game built with Java and the LibGDX framework. The project focuses on complex game engine logic, automated quality assurance, and agile development methodologies. This game was developed in a group of 3.

🎮 Overview

The player takes the role of a park director managing an African safari park. The goal is to create a sustainable ecosystem by planting vegetation, introducing wildlife, and building infrastructure such as roads and jeeps to attract tourists.

Animals move in groups and must regularly eat and drink while remembering important locations. The park contains both herbivores and predators. Poachers may appear and hunt animals, but players can hire rangers to protect them.

The player must keep the animals alive and attract enough tourists to win the game, while being able to adjust the game speed.


<img width="1913" height="1010" alt="image" src="https://github.com/user-attachments/assets/a7825bd1-01d0-40d6-b00f-5c52f93a5895" />

🚀 Advanced Features

Beyond the core mechanics, the following modules were implemented to increase technical complexity:

    🗺️ Minimap: Navigation system for a game world significantly larger than the viewport.

    🌙 Day/Night Cycle: Dynamic lighting system. At night, a "Fog of War" effect limits visibility to the surrounding areas of built infrastructure.

    🏹 Poachers: Hostile entities that attempt to kill or capture the park's wildlife for profit.

    🛡️ Park Rangers: Specialized units tasked with protecting animals from poachers and managing predator populations.

🛠️ Tech Stack & Architecture

    Language: Java (OpenJDK 17+).

    Framework: LibGDX with LWJGL3 backend.

    Build Tool: Gradle.

    CI/CD: Automated Unit testing integrated into a GitLab CI pipeline.

    Management: Agile workflow using Issue boards, Merge Requests, and a structured branching strategy.
    
    👥 Collaboration: Developed in a **team of 3** using Agile methodologies, Issue boards, and structured Merge Requests.

<img width="1913" height="1010" alt="image" src="https://github.com/user-attachments/assets/8af9e496-3b37-4b97-9ec0-5a7d75629639" />

<img width="1920" height="1004" alt="image" src="https://github.com/user-attachments/assets/d36ceb18-50af-4826-8ceb-0887b0eda41b" />

💻 How to Run

To run the game locally, ensure you have Java 17 or higher installed.

    1. Clone the repository:
        git clone https://github.com/abel-softengineer/Safari-Tycoon.git

    2. Enter the project directory
        cd Safari-Tycoon

    3. Launch the game
        The project uses the Gradle Wrapper, so you don't need to have Gradle installed globally

    On Linux or macOS:
        ./gradlew lwjgl3:run

    On Windows (CMD or PowerShell):
        gradlew lwjgl3:run

📂 Project Struct

To help you navigate the codebase, here are the key locations:

    Core Game Logic: lwjgl3/src/main/java/io/github/safari/lwjgl3

        This is where all the game mechanics, entities (animals, rangers, poachers), and UI logic reside.

    Assets: assets/

        Contains the textures, sounds, and map files used by the game.

    Automated Tests: lwjgl3/src/test/java/io.github.safari.lwjgl3

        Includes the Unit Tests integrated into the CI/CD pipeline, covering ecosystem simulations and entity behavior.

    Launcher: lwjgl3/src/main/java/io/github/safari/lwjgl3/Lwjgl3Launcher.java

        Contains the desktop-specific startup logic.


