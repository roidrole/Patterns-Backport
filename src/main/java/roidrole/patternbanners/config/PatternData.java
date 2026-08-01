package roidrole.patternbanners.config;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.GameData;
import roidrole.patternbanners.Tags;
import roidrole.patternbanners.Utils;
import roidrole.patternbanners.recipe.PatternFromShape;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;

import static roidrole.patternbanners.PatternBanners.pattern;

//An object representing a pattern's configuration. Using the vanilla object is quite cumbersome for this.
public abstract class PatternData {
	public final String hash;

	//This is -1 if the pattern has no associated pattern item (such as uses)
	public final int meta;

	//This is null on the server.
	public final @Nullable String name;

	private PatternData(BannerPattern pattern, int meta) {
		this.hash = pattern.getHashname();
		this.meta = meta;
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			this.name = pattern.getFileName();
		} else {
			//Just so IntelliJ shuts up about it
			this.name = null;
		}
	}

	private PatternData(String hash, int meta, @Nullable String name){
		this.hash = hash;
		this.meta = meta;
		this.name = name;
	}

	//Factory method for creating PatternData
	public static void genMappingFor(BannerPattern pattern){
		int meta = PatternRegistry.mappingsMap.size();
		do{
			meta++;
		} while(PatternRegistry.mappingsMap.containsKey(meta));

		PatternData patternData;
		if(pattern.hasPatternItem()){
			patternData = new PatternItem(pattern, meta);
		} else if (pattern.hasPattern()){
			patternData = new PatternShape(pattern, meta);
		} else {
			return;
		}
		PatternRegistry.mappingsMap.put(meta, patternData);
	}

	/**
	 * Creates a PatternData from reading a Json Object
	 * <p>
	 * <bold>Warning: </bold> If this method errors, the JSON will be left in a bad state.
	 * To avoid this, include the following code in your <code>catch</code> block <p> <code>
	 *     do{reader.skipValue();}while(reader.peek() != JsonToken.END_OBJECT);
	 * </code>
	 * @param reader the reader the data is read from
	 * @return the read PatternData
	 * @throws IOException on malformed JSON or invalid PatternData
	 */
	public static PatternData readMapping(JsonReader reader) throws IOException {
		reader.beginObject();
		String hash = null;
		String name = null;
		int meta = -1;
		String shape = null;
		ItemStack stack = null;
		ItemStack uses = null;

		//Reads attribute as standard
		while(reader.peek() != JsonToken.END_OBJECT){
			switch (reader.nextName()){
				case "hash":{
					hash = reader.nextString();
					break;
				}
				case "meta":{
					meta = reader.nextInt();
					break;
				}
				case "name":{
					name = reader.nextString();
					break;
				}
				case PatternShape.JSON_KEY:{
					shape = reader.nextString();
					break;
				}
				case PatternItem.JSON_KEY:{
					stack = Utils.parseItemStack(reader.nextString());
					break;
				}
				case PatternUses.JSON_KEY:{
					uses = Utils.parseItemStack(reader.nextString());
					break;
				}
			}
		}

		//Validation that the mapping is valid
		if(hash == null){
			throw new IOException("The hash has to be set for every mapping. ");
		} else if (name == null && FMLCommonHandler.instance().getSide() == Side.CLIENT){
			throw new IOException("Every mapping must have a name on the client-side (name is ignored on the server side)");
		}

		//Build the pattern
		PatternData pattern;
		if(uses != null){
			pattern = new PatternUses(hash, uses, name);
		} else if (meta == -1){
			throw new IOException("The meta of every mapping (except uses mappings) must be specified. The mapping for " + hash + " doesn't have one");
		} else if (shape != null){
			pattern = new PatternShape(hash, meta, name, shape);
		} else if (stack != null){
			pattern = new PatternItem(hash, meta, name, stack);
		} else {
			pattern = new PatternRecipeLess(hash, meta, name);
		}

		reader.endObject();
		return pattern;
	}

	///Idem, but with a ConfigCategory for migration
	public static PatternData readMapping(ConfigCategory category) throws IOException {
		String hash = category.get("hash").getString();
		int meta = category.get("meta").getInt();
		String name = Optional.ofNullable(category.get("name")).map(Property::getString).orElse(null);
		if(category.containsKey("item")){
			return new PatternItem(hash, meta, name, Utils.parseItemStack(category.get("item").getString()));
		} else if(category.containsKey("shap")){
			return new PatternShape(hash, meta, name, category.get("shap").getString().substring(1, 10));
		} else if(category.containsKey("uses")){
			return new PatternUses(hash, Utils.parseItemStack(category.get("uses").getString()), name);
		} else {
			return new PatternRecipeLess(hash, meta, name);
		}
	}
	//Writes this mapping as Json to the provided JsonWriter
	public final void writeMapping(JsonWriter writer) throws IOException {
		writer.beginObject();
		writer.name("hash");
		writer.value(hash);
		if(meta != -1) {
			writer.name("meta");
			writer.value(meta);
		}
		if(name != null) {
			writer.name("name");
			writer.value(name);
		}
		writeMappingSpecific(writer);
		writer.endObject();
	}
	protected abstract void writeMappingSpecific(JsonWriter writer) throws IOException;

	public ItemStack getPatternStack() {
		return new ItemStack(pattern, 1, meta);
	}

	public ResourceLocation getRecipeLocation() {
		return new ResourceLocation(Tags.MOD_ID, "recipes/pattern_create/" + this.hash);
	}

	public abstract void registerRecipe();

	//---Implementations of the class---

	//TODO: Consider making shap a String[] instead of a String
	public static class PatternShape extends PatternData {
		public final String shape;
		public static final String JSON_KEY = "shap";

		private PatternShape(BannerPattern pattern, int meta) {
			super(pattern, meta);
			this.shape = String.join("", pattern.getPatterns());
		}

		@Override
		protected void writeMappingSpecific(JsonWriter writer) throws IOException {
			writer.name(JSON_KEY);
			writer.value(shape);
		}

		private PatternShape(String hash, int meta, @Nullable String name, String shape){
			super(hash, meta, name);
			this.shape = shape;
		}

		@Override
		public void registerRecipe() {
			IRecipe recipe = new PatternFromShape(this.shape, this.getPatternStack());
			recipe.setRegistryName(this.getRecipeLocation());
			GameData.register_impl(recipe);
		}
	}

	public static class PatternItem extends PatternData {
		public final Item item;
		public final int itemDamage;
		public static final String JSON_KEY = "item";

		private PatternItem(BannerPattern pattern, int meta) {
			super(pattern, meta);
			ItemStack stack = pattern.getPatternItem();
			this.item = stack.getItem();
			this.itemDamage = stack.getItemDamage();
		}

		@Override
		protected void writeMappingSpecific(JsonWriter writer) throws IOException {
			writer.name(JSON_KEY);
			writer.value(item.delegate.name().toString() + ":" + itemDamage);
		}

		private PatternItem(String hash, int meta, @Nullable String name, ItemStack stack){
			super(hash, meta, name);
			this.item = stack.getItem();
			this.itemDamage = stack.getItemDamage();
		}

		@Override
		public void registerRecipe() {
			ItemStack patternStack = new ItemStack(pattern, 1, this.meta);
			GameRegistry.addShapelessRecipe(this.getRecipeLocation(), null, patternStack,
				Ingredient.fromItem(Items.PAPER),
				Ingredient.fromStacks(new ItemStack(item, 1, itemDamage))
			);
		}
	}

	public static class PatternUses extends PatternData {
		public final ItemStack uses;

		public static final String JSON_KEY = "uses";

		private PatternUses(String hash, ItemStack uses, @Nullable String name) {
			super(hash, -1, name);
			this.uses = uses;
		}

		@Override
		protected void writeMappingSpecific(JsonWriter writer) throws IOException {
			writer.name(JSON_KEY);
			writer.value(Utils.writeItemStack(uses));
		}

		@Override
		public ItemStack getPatternStack() {
			return uses;
		}

		@Override
		public void registerRecipe() {
			PatternRegistry.extraPatternItems.add(uses);
		}
	}

	public static class PatternRecipeLess extends PatternData {

		private PatternRecipeLess(String hash, int meta, @Nullable String name) {
			super(hash, meta, name);
		}

		@Override
		protected void writeMappingSpecific(JsonWriter writer) {
			// NO-OP
		}

		@Override
		public void registerRecipe() {
			// NO-OP
		}
	}
}
