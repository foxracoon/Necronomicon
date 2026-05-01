package noelle.necro.item.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.math.Vec3d;

public class TheReapersHandMaiden extends AxeItem {
    public TheReapersHandMaiden(ToolMaterials toolMaterials, int i, float v, FabricItemSettings fabricItemSettings) {
        super(toolMaterials, i, v, fabricItemSettings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isOnGround()) {
            Vec3d velocity = target.getVelocity();
            target.setVelocity(velocity.x, -1.5D, velocity.z);
            target.velocityModified = true;
        }
        if (attacker instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this, 400);
        }
        return super.postHit(stack, target, attacker);
    }

}

