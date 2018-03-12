# Limestone

A fast, customizable and compatible open source Minecraft client based on the [Glowstone](https://github.com/GlowstoneMC/Glowstone) project.

## Introduction

**NOTICE: Limestone is under extensive heavy-development. There is very little likely hood that any of this applies just yet. Please stay tuned!**

Limestone is a lightweight, from scratch, open source
[Minecraft](http://minecraft.net) client written in Java.

The main goal of the project is to completely recreate the internal logic of
Minecraft in an open source environment to make modding easier than ever by
removing the need for mods to compile against obfuscated code or an API that
keeps changing with each release. With each Minecraft version, so much changes
there is still no such thing as a cross-compatible mod, with the exception of
Bukkit - which is why we looked to the Glowstone project for our starting point.

Still have questions? Check out our [FAQ](https://github.com/TheAmeliaDeWitt/Limestone/wiki/Frequently-Asked-Questions).

## Features

Limestone has a few key differences from its parent projects:
 * Glowstone
   * Much like Glowstone, Limestone is much the same **100% open source**.
   However, with the **minimal** reliance of decompiled Minecraft code that
   is produced at runtime for both the client, server, and development, and
   the only code relied upon ordains to graphics rendering and resources.
 * Forge
   * Patching of Minecraft is handled on the client side and only needs to
   happen once. And since the resulting code is cached, loads are extremely
   fast and the focus can be put on loading the mods.
 * Vanilla
   * Limestone is intended to be seen as a fork of Vanilla Minecraft, meaning
   that there are plans for new and original features to be added with time.
   * Because Limestone relies on so little Minecraft Code, it implement its
   very own networking code and modding API.
   * *WIP* Mods/Addons/Plugins are restricted to the server-side with the
   exception of client-side scripts (written in Groovy) made for handling
   a strict set of tasks, such as the GUI. Resources and scripts are transmitted
   to the player upon joining a remote server.
   * *WIP* As such, players never have to install mods before joining a server,
   instead new blocks, items, features, and more are loaded on-demand and for
   session.
   * *WIP* Client-side scripts are limited to what they can and can't do, so
   rogue scripts are kept to a minimal.

However, there are also some drawbacks to using Limestone and things to consider:
 * Much like Glowstone, Limestone **is not finished**. We will try to implement
   new features on our own and merge new Glowstone features as we can, however,
   the establishment of this project is our key goal.
 * Limestone is **NOT** produced or associated with Bukkit, MCP, Forge, etc,
   and more specifiably, Mojang. So please don't concern them with issues and
   feature request for Limestone. We are a small team with limited time on our
   hands but will try to get to your requests and bugs within time.
 * For the time being, you are required per the Minecraft EULA, to have purchased
   Minecraft for the own personal use. Until such that we can have zero reliance
   on decompiled code, our game will ask for your login, which is then verified with Mojang Servers.
   
For a current list of features, [check the wiki](https://github.com/TheAmeliaDeWitt/Limestone/wiki/Current-Features).

## Kiln

Say hello to Kiln, which provides all the internal logic and modding API of Limestone. And one day! It might grow
big enough to swallow Limestone alive... wahahaha!

## Disclaimer

We hold ourselves applicable to the terms of the modding section from the Minecraft EULA: https://account.mojang.com/documents/minecraft_eula.

> If you've bought the Game, you may play around with it and modify it by adding modifications, tools, or plugins, which we will refer to collectively as "Mods." By "Mods," we mean something original that you or someone else created that doesn't contain a substantial part of our copyrightable code or content. When you combine your Mod with the Minecraft software, we will call that combination a "Modded Version" of the Game. We have the final say on what constitutes a Mod and what doesn't. You may not distribute any Modded Versions of our Game or software, and we’d appreciate it if you didn’t use Mods for griefing. Basically, Mods are okay to distribute; hacked versions or Modded Versions of the Game client or server software are not okay to distribute.

We do not intend to distribute modded/hacked versions of Minecraft nor any substantial part of the copyrighted Minecraft source code.

## Downloads

There is currently no available downloads. Please compile from source for the time being and/or stay tuned!

## Development Setup and Build

### Prerequisites

 * Java 8

Limestone is tested and compiled using the [Oracle JDK](http://oracle.com/technetwork/java/javase/downloads).
For best experience, we recommend you do the same, otherwise, the [OpenJDK](http://openjdk.java.net/) should also work.
Limestone is untested with Java 9. 

### Cloning This Repository

Active Git Branches:
 1. ***master-dev*** (default) Where the most unstable and/or untested code is located, this includes new features and substantial code changes made by pull requests.
 2. ***incubating*** Where the most bleeding edge code is located before it goes to stable release, once the version is deemed stable, most changes are then backported to dev branch.
 3. ***stable*** Where the final stable source code is located. With each stable release of Limestone, a merged pull request will be made on this repository.

*Pull Requests that fix security and game breaking bugs should be made against the 'incubating' branch (if relevant). All others must be made against the 'dev' (unstable) branch.*

```bash
git clone --recursive https://github.com/TheAmeliaDeWitt/Limestone
cd Limestone
```

*Remember to fork our repository and using an appropriate branch name if you plan on making pull requests. See the 'contributing' section for information on how to get started.*

### Using Terminal

*Self explanatory for Windows users who are seasoned in the Gradle Build System.*

 1. Provision your fork for general development.

This command will clone the appropriate git submodules (Presently Kiln and AmeliaLib) and apply some configuration, such as git hooks.
Lastly, the Minecraft code will be decompiled and our patches applied, using the ForgeGradle plugin. (Looking for alternatives, if anyone has any suggestions.) 

```bash
./gradlew setup
```

 2. TO BE CONTINUED
 
 3. Build
 
 ```bash
 ./grablew build
 ```
 
 The final jar will be placed in `build/dist/` named `limestone.jar`.

### Using IntelliJ IDEA

If using IntelliJ IDEA for development, importing our project is made ultra easy. 

 1. For this, simply click File -> Open, then navigate to your fork’s clone and select the build.gradle file. If a dialog pops up, select “Open as Project”.
 2. In the wizard that follows, make sure that “Create separate module per source set” is checked and that the “Use default gradle wrapper” option is active. Confirm the dialog.
 3. After IDEA is done importing the project and indexing the files, open the Gradle sidebar on the right hand side of your screen.
 4. Open the “LimestoneWrapper” project tree, select “Tasks”, then “other” and right click the "setupDev" task.
 5. Choose the “Create 'Limestone [setupDev]'” option.
 6. Once the configuration dialog shows up, edit the “tasks” field to contain clean setup and add -Xmx3G -Xms3G to “VM Options”. The latter option ensures that the resource intensive decompilation process has enough memory.
 7. Click “Okay” and run your newly created run configuration. This may take a while.
 8. After the setup task has completed, go once again to the Gradle sidebar and click the “Attach Gradle project” button (the plus icon) at the top.
 9. Navigate to your clone’s directory, then open the "projects/LimestoneClient" directory and double click the build.gradle file in there. Select “Use gradle wrapper task configuration” in the following dialog and confirm it.
 10. Repeat the last step for the "projects/LimestoneServer" directory as well. (If applicable)
 14. IDEA should recognize run configurations added by a previous step, complete the following steps for each.
 15. Change the configuration’s module to <LimestoneClient>_main where <LimestoneClient> is the first part of the configuration’s name
 16. Change the run directory to <clone>/projects/gamedata

## Running

Running Limestone is simple because its dependencies are both shaded into the output
jar at compile time and downloaded upon first run. Simply execute `java -jar limestone.jar` along with any
extra JVM options desired (we recommend using `java -Xms1G -Xmx1G -XX:+UseG1GC -jar limestone.jar`) and you
will be presented with the client interface. Instructions for how to launch the server are coming soon.

## Contributing

First of all, thank you for your interest in advancing Limestone! We always love to see new developers work on the project! You can find all of our resources on how to get started on our [wiki](https://github.com/TheAmeliaDeWitt/Limestone/wiki#contributing).

## Goals and Planned Features

Limestone targets one specific version of Minecraft (presently 1.12.2) and we strive to give a universal gaming experience
the community as a whole. Based on circumstances, new entities and blocks will be backported by either imcorporating our
own variations or by changing our targeted version. (The former being more likely)

We also strive to keep mod/addon compatibility with each new release. This might only change between major version numbers
and would be indicated on the download page what's the minimum compatible Kiln API version.

Modpacks will be a thing of the past, as game play will be more focused on the server and the removal of needing to have
mods installed locally. While it would still be possible to create your own combination of mods to play with friends,
server game play won't be affected. Mods are also decentralized and downloaded on demand from our repository. Updates can
by automatic and easily customized while the game is running.

Licensing will be a thing of the past; along with our elimination of modpacks, there is also no longer a concern that a
developers allows their mod to be included in a modpack or not, as long as their mod was rightfully uploaded to our
repository, it's essentially free game. Also some terms a developer imposes will can be handled by the server, such as
a donation shoutout or a list of mod credits included in the help page.

### Additional Modding Features (For Developers)

Most code is automatically limited to the server side and client interactions, such as the GUI will have built-in
networking code that automatically delivers button presses and results to the server side. This of it like developing
a web UI using "Bootstrap" and "JQuery" runs on the server to handle the presentation. There are cases where lite
operations can be moved to the client using a groovy script but it's beyond the scope of this README.
