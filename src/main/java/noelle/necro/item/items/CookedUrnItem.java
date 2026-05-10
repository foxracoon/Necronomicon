package noelle.necro.item.items;


import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.server.world.ServerWorld;

import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;

import net.minecraft.world.World;
import noelle.necro.entity.Entities.MailGhostEntity;
import noelle.necro.entity.ModEntities;

public class CookedUrnItem extends Item {

    public CookedUrnItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {

            MailGhostEntity ghost =
                    ModEntities.MAIL_GHOST.create(world);

            if (ghost != null) {

                ghost.refreshPositionAndAngles(
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        0,
                        0
                );

                ghost.setOwner(user);
                ghost.setTamed(true);

                ghost.setPersistent();

                world.spawnEntity(ghost);

                if (!user.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
            }
        }

        return TypedActionResult.success(
                stack,
                world.isClient()
        );
    }
}