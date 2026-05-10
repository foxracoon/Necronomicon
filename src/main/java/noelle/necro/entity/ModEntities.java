package noelle.necro.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import noelle.necro.Necronomicon;
import noelle.necro.entity.Entities.MailGhostEntity;

public class ModEntities {

    public static final EntityType<MailGhostEntity> MAIL_GHOST =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    new Identifier(Necronomicon.MOD_ID, "mail_ghost"),

                    FabricEntityTypeBuilder
                            .create(SpawnGroup.CREATURE, MailGhostEntity::new)
                            .dimensions(EntityDimensions.fixed(0.6f, 0.8f))
                            .trackRangeBlocks(8)
                            .trackedUpdateRate(3)
                            .build()
            );

    public static void registerModEntities() {
        Necronomicon.LOGGER.info("Registering Mod Entities");
    }
}
