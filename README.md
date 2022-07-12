# Dungeon Adventure

### Overview

- Dungeon Adventure project

----

[![GitHub issues by-label](https://img.shields.io/github/issues/TCSS360A/DungeonAdventure)](https://github.com/TCSS360A/DungeonAdventure/issues)

___
### Support
- DungeonAdventure is created by [Griffin Ryan][griffinryan-github], [Yudong Lin][yudonglin-github], and [Elijah Amian][elijahamian-github].

- Found a problem? Pull requests are welcome.

- If you have ideas, questions or suggestions - **Welcome to [discussions](https://github.com/TCSS360A/DungeonAdventure/discussions)**. 😊
___


## Get started

> Clone the DungeonAdventure project.

    git clone https://github.com/TCSS360A/DungeonAdventure.git
    cd DungeonAdventure/

> The `lwjgl` branch features the DungeonAdventure project built on LWJGL.

> To run this version of DungeonAdventure, just `cd` into the project directory and use `gradle`

    cd DungeonAdventure/
    gradle build
    gradle run

> The `glryan` branch currently features a JavaFX application.

> M1 Apple computers don't play nicely with JavaFX at the moment, so JDK 16 is required for macOS systems (not JDK 17/18)

> If developing on macOS, make sure the project and Gradle build script is configured to use JDK 16

> If executing using the Gradle wrapper on macOS, use the following command to configure the JDK for Gradle to use:

    cd DungeonAdventure/
    gradle build -Dorg.gradle.java.home=/PATH_TO_JDK16
    gradle run -Dorg.gradle.java.home=/PATH_TO_JDK16

[griffinryan-github]: https://github.com/griffinryan/
[yudonglin-github]: https://github.com/yudonglin
[elijahamian-github]: https://github.com/Elijah1368
