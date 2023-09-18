# Ultreon Mod Library [![CircleCI](https://dl.circleci.com/status-badge/img/gh/Ultreon/ultreonlib/tree/1.19.3.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/Ultreon/ultreonlib/tree/1.19.3)
MC Modding Library for Qboi123's Mod(s).
   
## CurseForge
https://www.curseforge.com/minecraft/mc-mods/qmodlib  
  
## Mods that uses this library:
 * [Random Thingz](https://www.curseforge.com/minecraft/mc-mods/random-thingz)
 * [Advanced Debug](https://www.curseforge.com/minecraft/mc-mods/advanced-debug)

## Using as API
1) Add the repository (`https://jitpack.io/`)
   ```gradle
   repositories {
       maven {
           name = "JitPack"
           url = uri("https://jitpack.io/")
       }
   }
   ```
2) Add the dependencies (check [JitPack](https://jitpack.io/#Ultreon/ultreonlib) for latest versions)
   ForgeGradle:  
   ```gradle
   dependencies {
       // ...

       implementation fg.deobf("com.github.Ultreon.ultreonlib:ultreonlib-forge:1.3.3")
   }X
   ```
   
   Fabric/Quilt Loom:  
   ```gradle
   dependencies {
       // ...

       modImplementation("com.github.Ultreon.ultreonlib:ultreonlib-fabric:1.3.3")
   }
   ```
   
   Architectury Common:  
   ```gradle
   dependencies {
       // ...

       modImplementation("com.github.Ultreon.ultreonlib:ultreonlib:1.3.3")
   }
   ```
3) Reload gradle.
4) You're done
