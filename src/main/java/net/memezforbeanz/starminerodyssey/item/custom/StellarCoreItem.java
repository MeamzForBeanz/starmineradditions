package net.memezforbeanz.starminerodyssey.item.custom;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.memezforbeanz.starminerodyssey.item.client.StellarCoreRenderer;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StellarCoreItem extends Item implements GeoItem, SimpleEnergyItem {
    private final long capacity;
    private final long maxInput;
    private final long maxOutput;
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public StellarCoreItem(Settings settings, long capacity, long maxInput, long maxOutput) {
        super(settings);
        this.capacity = capacity;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
    }

    @Override
    public boolean isDamageable() {
        return true; // Makes the item appear damageable
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        // Show the bar if there's any energy stored or capacity is not zero
        return getStoredEnergy(stack) > 0;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        long stored = getStoredEnergy(stack);
        return Math.round(13.0F * stored / capacity); // Scale to 13 steps (Minecraft's standard)
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0x00FF00; // Green color for the bar, use hex for other colors
    }


    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long stored = getStoredEnergy(stack);
        float percentage = (float) stored / capacity * 100;
        tooltip.add(Text.translatable("item.starminerodyssey.stellar_core.info"));

        tooltip.add(Text.translatable("item.starminerodyssey.stellar_core.energy",
                String.format("%,d", stored),
                String.format("%,d", capacity)));

        tooltip.add(Text.translatable("item.starminerodyssey.stellar_core.percentage",
                String.format("%.1f", percentage)));

        tooltip.add(Text.translatable("item.starminerodyssey.stellar_core.transfer_rate",
                String.format("%,d", maxOutput)));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;

        // Check if the entity has an inventory
        if (!(entity instanceof Inventory inventory)) return;

        // Try all directions since we don't know which side the energy should go to
        for (Direction direction : Direction.values()) {
            BlockPos pos = entity.getBlockPos();
            EnergyStorage targetStorage = EnergyStorage.SIDED.find(world, pos, direction);

            if (targetStorage != null) {
                ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
                EnergyStorage itemStorage = EnergyStorage.ITEM.find(stack, itemContext);

                if (itemStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        long transferred = EnergyStorageUtil.move(
                                itemStorage,
                                targetStorage,
                                maxOutput,
                                transaction
                        );

                        if (transferred > 0) {
                            transaction.commit();
                            spawnEnergyTransferEffects(world, pos);
                            break; // Exit after successful transfer
                        }
                    }
                }
            }
        }
    }

    private void spawnEnergyTransferEffects(World world, BlockPos pos) {
        if (world.isClient) {
            return;
        }
        world.playSound(
                null,
                pos,
                SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE,
                SoundCategory.BLOCKS,
                0.3f,
                1.0f
        );
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().isClient) return ActionResult.SUCCESS;

        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Direction side = context.getSide();

        EnergyStorage targetStorage = EnergyStorage.SIDED.find(world, pos, side);
        if (targetStorage != null) {
            ItemStack stack = context.getStack();
            ContainerItemContext itemContext = ContainerItemContext.withConstant(stack);
            EnergyStorage itemStorage = EnergyStorage.ITEM.find(stack, itemContext);

            if (itemStorage != null) {
                try (Transaction transaction = Transaction.openOuter()) {
                    long transferred = EnergyStorageUtil.move(
                            itemStorage,
                            targetStorage,
                            maxOutput,
                            transaction
                    );

                    if (transferred > 0) {
                        transaction.commit();
                        spawnEnergyTransferEffects(world, pos);
                        return ActionResult.SUCCESS;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    public long getEnergyCapacity(ItemStack stack) {
        return capacity;
    }

    @Override
    public long getEnergyMaxInput(ItemStack stack) {
        return maxInput;
    }

    @Override
    public long getEnergyMaxOutput(ItemStack stack) {
        return maxOutput;
    }



    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final StellarCoreRenderer renderer = new StellarCoreRenderer();
            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }

    @Override
    public double getTick(Object itemStack) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        tAnimationState.getController().setAnimation(RawAnimation.begin().then("star_core.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}