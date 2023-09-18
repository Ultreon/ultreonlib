# Ultreon Mod Library [![CircleCI](https://dl.circleci.com/status-badge/img/gh/Ultreon/ultreonlib/tree/1.19.4.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/Ultreon/ultreonlib/tree/1.19.4)
MC Modding Library for mod(s) made by Ultreon Team and XyperCode.
   
## Download
From [CurseForge](https://curseforge.com/minecraft/mc-mods/ultreonlib) or [Modrinth](https://modrinth.com/mod/ultreonlib)
  
## Mods that uses this library:
 * ~~Random Thingz: [CurseForge](https://curseforge.com/minecraft/mc-mods/random-thingz)~~ `Abandoned`
 * Advanced Debug: [CurseForge](https://curseforge.com/minecraft/mc-mods/advanced-debug), [Modrinth](https://modrinth.com/mod/advanced-debug)

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
2) Add the dependencies (version shown here could be inaccurate with tag/release)
   ForgeGradle:  
   ```gradle
   dependencies {
       // ...

       implementation fg.deobf("com.github.Ultreon.ultreonlib:ultreonlib-forge:1.4.0")
   }X
   ```
   
   Fabric/Quilt Loom:  
   ```gradle
   dependencies {
       // ...

       modImplementation("com.github.Ultreon.ultreonlib:ultreonlib-fabric:1.4.0")
   }
   ```
   
   Architectury Common:  
   ```gradle
   dependencies {
       // ...

       modImplementation("com.github.Ultreon.ultreonlib:ultreonlib:1.4.0")
   }
   ```
3) Reload gradle.
4) You're done
