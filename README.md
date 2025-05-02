Aims to backport banner patterns from 1.14+
Supports all mods adding custom banner patterns with a blank pattern texture and procedurally-generated name.

If using resource loader, you can also add custom banner patterns via config by : 
1. Adding a custom pattern hash in patternbanners.cfg : general/custom_pattern_hashes
2. Regenerating the configs with /patternbanners:regenconfig
3. (optional) set a custom name and crafting by scrolling down the list, finding your custom pattern and changing name
4. (optional) set a crafting item (with the item=mod:name:meta or shape, where # -> dye and spaces to nothing, prefixed and suffixed by .)
5. Set a custom banner shape in both assets/minecraft/texture/entity/banner/[name].png and assets/minecraft/texture/entity/shield/[name].png
6. (optional) Set a custom pattern item model in assets/patternbanners/models/item/pattern/[name].json linking to your preferred texture
7. (optional) Set a custom localized name in patternbanners.pattern.[name].name
