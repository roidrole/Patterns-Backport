Backports banner patterns from 1.14+

Supports all mods adding custom banner patterns with a blank pattern texture and procedurally-generated name (overwritable by resource packs).

Out-of-the-box support for :
- Pattern textures for : 
     - Quark
     - Botania
     - Immersive Engineering
- Unseen's Nether Backport (adds piglin banner pattern to loot table)
- Deeper Depths (adds trial chamber patterns to loot table)
- Vanilla Cartographer (cartographer globe trade)

Supports adding custom pattern banners, explained [here](https://github.com/roidrole/Patterns-Backport/wiki/Custom-Patterns)

Works by scanning the registered banner patterns on first launch and adding them to the config file.
If changes are made to the loaded patterns (such as by adding/removing a mod), run the command /patternbanners:configupdate to rescan the patterns

If a modpack author adds more mod support (in the form of pattern textures and localisation), feel free to PR!
