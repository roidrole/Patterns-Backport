Unreleased : 

**Added** : 
- Loom
- Config to remove crafting table and/or loom recipes

**Fixed** : 
- entryName of injected entry in Bastion

**Changed** : 
- Improvements to recipe registry
- General code cleanup

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


1.0.0 : 
First release