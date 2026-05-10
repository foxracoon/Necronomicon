package noelle.necro.entity.Entities;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MailGhostEntity extends TameableEntity {

    private final SimpleInventory inventory = new SimpleInventory(1);

    public MailGhostEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);

        this.setNoGravity(true);
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(1,
                new FollowOwnerGoal(this, 1.0D, 3.0F, 1.0F, false));
    }

    @Override
    public void tick() {
        super.tick();

        this.setNoGravity(true);

        if (!this.getWorld().isClient) {

            ItemStack stack = inventory.getStack(0);

            if (!stack.isEmpty() && stack.getItem() instanceof BundleItem) {

                if (stack.hasCustomName()) {

                    String targetName = stack.getName().getString();

                    ServerPlayerEntity target =
                            ((ServerWorld)this.getWorld())
                                    .getServer()
                                    .getPlayerManager()
                                    .getPlayer(targetName);

                    if (target != null) {


                        this.teleport(
                                target.getX(),
                                target.getY() + 1,
                                target.getZ()
                        );


                        this.dropStack(stack.copy());

                        inventory.setStack(0, ItemStack.EMPTY);
                    }
                }
            }
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack held = player.getStackInHand(hand);


        if (held.getItem() instanceof BundleItem) {

            if (inventory.getStack(0).isEmpty()) {

                ItemStack copy = held.copy();
                copy.setCount(1);

                inventory.setStack(0, copy);

                held.decrement(1);

                return ActionResult.SUCCESS;
            }
        }

        if (held.isEmpty()) {

            ItemStack stored = inventory.getStack(0);

            if (!stored.isEmpty()) {

                player.giveItemStack(stored.copy());

                inventory.setStack(0, ItemStack.EMPTY);

                return ActionResult.SUCCESS;
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public void onDeath(DamageSource damageSource) {

        if (!this.getWorld().isClient) {

            ItemStack stored = inventory.getStack(0);

            if (!stored.isEmpty()) {

                this.dropStack(stored);
            }
        }

        super.onDeath(damageSource);
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public EntityView method_48926() {
        return null;
    }
}
