package noelle.necro.item.items;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BloomingLanceItem extends Item {

    public BloomingLanceItem(Settings settings) {
        super(settings);
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

            world.playSound(
                    null,
                    user.getBlockPos(),
                    SoundEvents.BLOCK_SAND_FALL,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            world.playSound(
                    null,
                    user.getBlockPos(),
                    SoundEvents.BLOCK_SAND_PLACE,
                    SoundCategory.PLAYERS,
                    1.0F,
                    1.0F
            );

            user.getItemCooldownManager().set(this, 40);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
