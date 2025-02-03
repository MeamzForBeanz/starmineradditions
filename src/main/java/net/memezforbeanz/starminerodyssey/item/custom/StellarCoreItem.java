package net.memezforbeanz.starminerodyssey.item.custom;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.client.StellarCoreRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyItem;
import team.reborn.energy.api.EnergyStorageUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StellarCoreItem extends Item implements GeoItem, SimpleEnergyItem {
    private final long capacity;
    private final long maxInput;
    private final long maxOutput;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public StellarCoreItem(Properties properties, long capacity, long maxInput, long maxOutput) {
        super(properties);
        this.capacity = capacity;
        this.maxInput = maxInput;
        this.maxOutput = maxOutput;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getStoredEnergy(stack) > 0;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        long stored = getStoredEnergy(stack);
        return Math.round(13.0F * stored / capacity);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x00FF00;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        long stored = getStoredEnergy(stack);

        float percentage = (float) stored / capacity * 100;
        tooltip.add(Component.translatable("item.starminerodyssey.stellar_core.info"));

        if (!Screen.hasShiftDown()) return;

        tooltip.add(Component.translatable("item.starminerodyssey.stellar_core.energy",
                String.format("%,d", stored),
                String.format("%,d", capacity)));

        tooltip.add(Component.translatable("item.starminerodyssey.stellar_core.percentage",
                String.format("%.1f", percentage)));

        tooltip.add(Component.translatable("item.starminerodyssey.stellar_core.transfer_rate",
                String.format("%,d", maxOutput)));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (level.isClientSide) return;

        // Check if the entity has an inventory
        if (!(entity instanceof Player player)) return;
        Inventory inventory = player.getInventory();

        // Try all directions since we don't know which side the energy should go to
        for (Direction direction : Direction.values()) {
            BlockPos pos = entity.blockPosition();
            EnergyStorage targetStorage = EnergyStorage.SIDED.find(level, pos, direction);

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
                            spawnEnergyTransferEffects(level, pos);
                            break; // Exit after successful transfer
                        }
                    }
                }
            }
        }
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        BlockPos targetPos = context.getClickedPos();
        Direction clickedFace = context.getClickedFace();

        // Combined null check and storage lookup
        EnergyStorage targetStorage = EnergyStorage.SIDED.find(level, targetPos, clickedFace);

        // Fallback check using stream API
        if (targetStorage == null) {
            targetStorage = Direction.stream()
                    .map(dir -> EnergyStorage.SIDED.find(level, targetPos, dir))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }

        if (targetStorage == null || !targetStorage.supportsInsertion()) {
            return InteractionResult.FAIL;
        }

        ItemStack stack = context.getItemInHand();
        EnergyStorage itemStorage = EnergyStorage.ITEM.find(stack,
                ContainerItemContext.ofPlayerHand(context.getPlayer(), context.getHand()));

        if (itemStorage == null || itemStorage.getAmount() <= 0) {
            return InteractionResult.FAIL;
        }

        try (Transaction transaction = Transaction.openOuter()) {
            long maxTransfer = Math.min(this.maxOutput, targetStorage.getCapacity());
            long moved = EnergyStorageUtil.move(
                    itemStorage,
                    targetStorage,
                    maxTransfer,
                    transaction
            );

            if (moved > 0) {
                transaction.commit();
                spawnEnergyTransferEffects(level, targetPos);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.FAIL;
    }

    private void spawnEnergyTransferEffects(Level level, BlockPos pos) {
        if (!level.isClientSide) {
            level.playSound(null, pos, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 0.3f, 1.0f);
        }
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
            public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer() {
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
