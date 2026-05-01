package noelle.necro.item;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import noelle.necro.Necronomicon;
import noelle.necro.item.items.StopWatchItem;
import noelle.necro.item.items.TheReapersHandMaiden;

public class ModItems {
    public static Item THE_REAPERS_HANDMAIDEN = registerItem("the_reapers_handmaiden",
            new TheReapersHandMaiden(ToolMaterials.NETHERITE, 1,-2.5f,
                    new FabricItemSettings().fireproof().maxCount(1)));

    public static Item BLOOMING_LANCE = registerItem("blooming_lance",
            new TheReapersHandMaiden(ToolMaterials.NETHERITE, 1,-2.5f,
                    new FabricItemSettings().fireproof().maxCount(1)));

    public static Item THE_NECRONOMICON =registerItem("necornomicon",
            new Item(new FabricItemSettings().maxCount(1)));

    public static Item STOPWATCH =registerItem("stopwatch",
            new StopWatchItem(new FabricItemSettings().maxCount(1)));












    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Necronomicon.MOD_ID, name), item);
    }


    public static void registerModItems() {
        Necronomicon.LOGGER.debug("Registering Mod Items for " + Necronomicon.MOD_ID);
    }

}
