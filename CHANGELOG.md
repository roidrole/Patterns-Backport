Unreleased : 

**Added** : 
- Loom
- Recipe to apply a pattern from a shape only
- Config to remove crafting table and/or loom recipes (pattern creation, application, bypassing)
- Config to fall back to old pattern texture
- Config to always use fallback texture
- Config for PatternApply to consume pattern
- Config to show crafting table or loom in HEI

**Fixed** : 
- Light gray and light blue dye not applicable
- Off-by-one error on max_banner_layer

**Changed** : 
- Improvements to recipe registry
- General code cleanup
- Optimized JEI category

.

1.1.1 :

**Fixed** :

- entryName of injected entry in Bastion
- Mod not loading

.

1.1.0 :

**Added** :
- Allows setting a custom pattern item via the uses parameter
- Add config to remove vanilla pattern applying recipe via mixin

**Changed** : 
- Set crafting table as catalyst of the HEI category
- Rewrote config handling :
    - Move mixins, mappings and general to separate files
    - **There is no script to move them over, you will have to update manually**
    - Should be more robust, report any bugs encountered

**Fixed** :
- Make PatternApply not show in recipe book
- Make all banners have a black background (#2)
- Fix max_banner_layers

.

1.0.0 : 
First release