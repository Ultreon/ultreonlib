package com.ultreon.mods.lib.core.api;

@SuppressWarnings("unused")
/**
 * @deprecated Removed
 */
@Deprecated
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
    public static final String CLIENT_NEXT = "clientNext";
    public static final String CLIENT_PREVIOUS = "clientPrevious";
    public static final String COMPLEX = "complex";
    public static final String CURRENT_ACCEPTORS = "acceptors";
    public static final String CURRENT_CONNECTIONS = "connections";
    public static final String HAS_STRUCTURE = "hasStructure";
    public static final String HEIGHT = "height";
    public static final String HOT = "hot";
    public static final String INVENTORY_ID = "inventoryID";
    public static final String LOWER_VOLUME = "lowerVolume";
    public static final String NETWORK = "network";
    public static final String OWNER_NAME = "ownerName";
    public static final String RENDERING = "rendering";
    public static final String RENDER_LOCATION = "renderLocation";
    public static final String RENDER_Y = "renderY";
    public static final String ROTATION = "rotation";
    public static final String SCALE = "scale";
    public static final String SOUND_SCALE = "soundScale";
    public static final String VALVE = "valve";
    public static final String VOLUME = "volume";
    //Generic constants
    public static final String ACTIVE_COOLED = "activeCooled";
    public static final String ACTIVE_NODES = "activeNodes";
    public static final String ACTIVE_STATE = "activeState";
    public static final String ALLOW_DEFAULT = "allowDefault";
    public static final String AMOUNT = "amount";
    public static final String AUTO = "auto";
    public static final String BLADES = "blades";
    public static final String BLOCK_STATE = "blockState";
    public static final String BURNING = "burning";
    public static final String BURN_TIME = "burnTime";
    public static final String CACHE = "cache";
    public static final String CHANNEL = "channel";
    public static final String CHUNK_SET = "chunkSet";
    public static final String COLOR = "color";
    public static final String CONFIG = "config";
    public static final String CONNECTION = "connection";
    public static final String CONTAINER = "Container";
    public static final String CONTROL_TYPE = "controlType";
    public static final String CURRENT_REDSTONE = "currentRedstone";
    public static final String DATA = "data";
    public static final String DATA_TYPE = "dataType";
    public static final String DELAY = "delay";
    public static final String DIMENSION = "dimension";
    public static final String DRIVES = "drives";
    public static final String DUMP_LEFT = "dumpLeft";
    public static final String DUMP_MODE = "dumping";
    public static final String DUMP_RIGHT = "dumpRight";
    public static final String EDIT_MODE = "editMode";
    public static final String EJECT = "eject";
    public static final String ENERGY_CONTAINERS = "EnergyContainers";
    public static final String ENERGY_STORED = "energy";
    public static final String ENERGY_USAGE = "energyUsage";
    public static final String FILTER = "filter";
    public static final String FILTERS = "filters";
    public static final String FINISHED = "finished";
    public static final String FLUID_STORED = "fluid";
    public static final String FLUID_TANKS = "FluidTanks";
    public static final String FOLLOW = "follow";
    public static final String FREQUENCY = "frequency";
    public static final String FREQUENCY_LIST = "freqList";
    public static final String FROM_RECIPE = "fromRecipe";
    public static final String HOME_LOCATION = "homeLocation";
    public static final String INDEX = "index";
    public static final String INVALID = "invalid";
    public static final String INVERSE = "inverse";
    public static final String ITEM = "Item";
    public static final String ITEMS = "Items";
    public static final String LAST_FIRED = "lastFired";
    public static final String LAST_PROCESSED = "lastProcessed";
    public static final String LEVEL = "level";
    public static final String LOGIC_TYPE = "logicType";
    public static final String MAIN = "main";
    public static final String MAX = "max";
    public static final String MAX_BURN_TIME = "maxBurnTime";
    public static final String MIN = "min";
    public static final String MODE = "mode";
    public static final String MODID = "modID";
    public static final String MODULES = "modules";
    public static final String NAME = "name";
    public static final String ORIGINAL_LOCATION = "originalLocation";
    public static final String OUTPUT_MODE = "outputMode";
    public static final String OVERRIDE = "override";
    public static final String OWNER_UUID = "owner";
    public static final String PATH_TYPE = "pathType";
    public static final String PICKUP_DROPS = "dropPickup";
    public static final String PLASMA_TEMP = "plasmaTemp";
    public static final String POSITION = "position";
    public static final String PROCESSED = "processed";
    public static final String PROGRESS = "progress";
    public static final String PUBLIC_FREQUENCY = "publicFreq";
    public static final String PULL = "pull";
    public static final String PULSE = "pulse";
    public static final String RADIATION = "radiation";
    public static final String RADIATION_LIST = "radList";
    public static final String RADIUS = "radius";
    public static final String REDSTONE = "redstone";
    public static final String REPLACE_STACK = "replaceStack";
    public static final String REQUIRE_STACK = "requireStack";
    public static final String RUNNING = "running";
    public static final String SECURITY_MODE = "securityMode";
    public static final String SELECTED = "selected";
    public static final String SIDE = "side";
    public static final String SILK_TOUCH = "silkTouch";
    public static final String SINGLE_ITEM = "singleItem";
    public static final String SIZE_MODE = "sizeMode";
    public static final String SIZE_OVERRIDE = "SizeOverride";
    public static final String SLOT = "Slot";
    public static final String SORTING = "sorting";
    public static final String STATE = "state";
    public static final String STOCK_CONTROL = "stockControl";
    public static final String STORED = "stored";
    public static final String STRICT_INPUT = "strictInput";
    public static final String SUCKED_LAST_OPERATION = "suckedLastOperation";
    public static final String TAG_NAME = "tagName";
    public static final String TANK = "Tank";
    public static final String TEMPERATURE = "temperature";
    public static final String TILE_TAG = "tileTag";
    public static final String TIME = "time";
    public static final String TRUSTED = "trusted";
    public static final String TYPE = "type";
    public static final String UPDATE_DELAY = "updateDelay";
    public static final String UPGRADES = "upgrades";
    public static final String WORLD_GEN_VERSION = "worldGenVersion";

    private NbtKeys() {
        throw new UnsupportedOperationException("This is a utility class that cannot be instantiated");
    }
}