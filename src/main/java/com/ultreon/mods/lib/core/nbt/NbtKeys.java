package com.ultreon.mods.lib.core.nbt;

@SuppressWarnings("unused")
public final class NbtKeys {

    //Ones that also are used for interacting with forge/vanilla
    public static final String BASE = "Base";
    public static final String BLOCK_ENTITY_TAG = "BlockEntityTag";
    public static final String CHUNK_DATA_LEVEL = "Level";
    public static final String ENTITY_TAG = "EntityTag";
    public static final String ID = "id";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";
    //Server to Client specific sync NBT tags
    public static final String ACTIVE = "active";
    public static final String HAS_STRUCTURE = "hasStructure";
    public static final String HEIGHT = "height";
    public static final String INVENTORY_ID = "inventoryID";
    public static final String NETWORK = "network";
    public static final String OWNER_NAME = "ownerName";
    public static final String ROTATION = "rotation";
    public static final String SCALE = "scale";
    public static final String VOLUME = "volume";
    //Generic constants
    public static final String AMOUNT = "amount";
    public static final String BLOCK_STATE = "blockState";
    public static final String BURNING = "burning";
    public static final String BURN_TIME = "burnTime";
    public static final String CACHE = "cache";
    public static final String COLOR = "color";
    public static final String CONFIG = "config";
    public static final String CONNECTION = "connection";
    public static final String CONTAINER = "Container";
    public static final String DATA = "data";
    public static final String DATA_TYPE = "dataType";
    public static final String DIMENSION = "dimension";
    public static final String EDIT_MODE = "editMode";
    public static final String ENERGY = "Energy";
    public static final String ENERGY_STORED = "energy";
    public static final String ENERGY_USAGE = "energyUsage";
    public static final String FILTER = "filter";
    public static final String FILTERS = "filters";
    public static final String FINISHED = "finished";
    public static final String FLUID_STORED = "fluid";
    public static final String FLUID_TANKS = "FluidTanks";
    public static final String FREQUENCY = "frequency";
    public static final String FROM_RECIPE = "fromRecipe";
    public static final String INVALID = "invalid";
    public static final String ITEM = "Item";
    public static final String ITEMS = "Items";
    public static final String LEVEL = "level";
    public static final String MAIN = "main";
    public static final String MAX = "max";
    public static final String MAX_BURN_TIME = "maxBurnTime";
    public static final String MIN = "min";
    public static final String MODE = "mode";
    public static final String MODID = "modID";
    public static final String NAME = "name";
    public static final String OWNER_UUID = "owner";
    public static final String POSITION = "position";
    public static final String PROCESSED = "processed";
    public static final String PROGRESS = "progress";
    public static final String RADIATION = "radiation";
    public static final String RADIATION_LIST = "radList";
    public static final String RADIUS = "radius";
    public static final String REDSTONE = "redstone";
    public static final String REDSTONE_MODE = "redstoneMode";
    public static final String REVERSED = "reversed";
    public static final String RUNNING = "running";
    public static final String SECURITY_MODE = "securityMode";
    public static final String SELECTED = "selected";
    public static final String SIDE = "side";
    public static final String SLOT = "Slot";
    public static final String STATE = "state";
    public static final String STORED = "stored";
    public static final String TAG_NAME = "tagName";
    public static final String TANK = "Tank";
    public static final String TEMPERATURE = "temperature";
    public static final String TIME = "time";
    public static final String TRUSTED = "trusted";
    public static final String TYPE = "type";
    public static final String UPGRADES = "upgrades";

    private NbtKeys() {
        throw new UnsupportedOperationException("This is a utility class that cannot be instantiated");
    }
}