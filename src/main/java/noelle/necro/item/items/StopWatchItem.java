package noelle.necro.item.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class StopWatchItem extends Item {

    public StopWatchItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            NbtCompound nbt = stack.getOrCreateNbt();

            if (!nbt.contains("savedPos")) {
                NbtCompound pos = new NbtCompound();
                pos.putDouble("x", user.getX());
                pos.putDouble("y", user.getY());
                pos.putDouble("z", user.getZ());
                pos.putString("dimension", world.getRegistryKey().getValue().toString());

                nbt.put("savedPos", pos);


                user.getItemCooldownManager().set(this, 30);
            } else {
                NbtCompound pos = nbt.getCompound("savedPos");

                double x = pos.getDouble("x");
                double y = pos.getDouble("y");
                double z = pos.getDouble("z");

                if (user instanceof ServerPlayerEntity player) {
                    player.teleport(
                            player.getServerWorld(),
                            x, y, z,
                            player.getYaw(),
                            player.getPitch()
                    );
                }

                nbt.remove("savedPos");

                user.getItemCooldownManager().set(this, 300);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
