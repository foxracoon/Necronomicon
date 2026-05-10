package noelle.necro.item.items;


import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class BloomingLanceItem extends Item {
    public BloomingLanceItem(ToolMaterials toolMaterials, int i, float v, FabricItemSettings fabricItemSettings) {
        super(fabricItemSettings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {

            Vec3d look = user.getRotationVec(1.0F).normalize();
            double dashStrength = 2.5;
            Vec3d dash = look.multiply(dashStrength);

            user.addVelocity(dash.x, dash.y, dash.z);
            user.velocityModified = true;

            Box hitBox = user.getBoundingBox().stretch(look.multiply(2.0)).expand(1.0);
            List<Entity> entities = world.getOtherEntities(user, hitBox);

            for (Entity entity : entities) {
                if (entity instanceof LivingEntity target) {

                    Vec3d vel = user.getVelocity();
                    double horizontalSpeed = Math.sqrt(vel.x * vel.x + vel.z * vel.z);

                    float baseDamage = 4.0F;
                    float scaling = 6.0F;

                    float damage = (float) (baseDamage + horizontalSpeed * scaling);

                    damage = Math.min(damage, 20.0F);

                    target.damage(
                            world.getDamageSources().playerAttack(user),
                            damage
                    );

                    Vec3d knockback = look.multiply(1.5);
                    target.addVelocity(knockback.x, 0.3, knockback.z);
                    target.velocityModified = true;
                }
            }

            world.playSound(null, user.getBlockPos(),
                    SoundEvents.BLOCK_SAND_FALL,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);

            world.playSound(null, user.getBlockPos(),
                    SoundEvents.BLOCK_SAND_PLACE,
                    SoundCategory.PLAYERS, 1.0F, 1.0F);

            user.getItemCooldownManager().set(this, 40);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
