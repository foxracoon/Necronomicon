package noelle.necro.entity;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import noelle.necro.Necronomicon;

public class ModModelLayers {

    public static final EntityModelLayer MAIL_GHOST =
            new EntityModelLayer(
                    new Identifier(Necronomicon.MOD_ID, "mail_ghost"),
                    "main"
            );
}
