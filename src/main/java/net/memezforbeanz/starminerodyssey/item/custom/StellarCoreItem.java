package net.memezforbeanz.starminerodyssey.item.custom;

import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.utils.DistributionMode;
import earth.terrarium.adastra.common.utils.TooltipUtils;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyItem;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer;
import earth.terrarium.botarium.common.energy.impl.WrappedItemEnergyContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.item.client.StellarCoreRenderer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

public class StellarCoreItem extends Item implements GeoItem, BotariumEnergyItem<WrappedItemEnergyContainer> {
    public static final String ACTIVE_TAG = "Active";
    public static final String MODE_TAG = "Mode";
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

    public static boolean active(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains("Active") ? tag.getBoolean("Active") : true;
    }

    public static boolean toggleActive(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        boolean active = active(stack);
        tag.putBoolean("Active", !active);
        return !active;
    }

    public static DistributionMode mode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return tag.contains("Mode") ? DistributionMode.values()[tag.getByte("Mode")] : DistributionMode.SEQUENTIAL;
    }

    public static DistributionMode toggleMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        DistributionMode mode = mode(stack);
        DistributionMode toggled = mode == DistributionMode.SEQUENTIAL ? DistributionMode.ROUND_ROBIN : DistributionMode.SEQUENTIAL;
        tag.putByte("Mode", (byte)toggled.ordinal());
        return toggled;
    }

    public WrappedItemEnergyContainer getEnergyStorage(ItemStack holder) {
        return new WrappedItemEnergyContainer(holder, new SimpleEnergyContainer(this.capacity,this.maxOutput, this.maxInput) {
            public long maxInsert() {
                return maxInput;
            }

            public long maxExtract() {
                return maxOutput;
            }
        });
    }

    public boolean isBarVisible(@NotNull ItemStack stack) {
        return this.getEnergyStorage(stack).getStoredEnergy() > 0L;
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        WrappedItemEnergyContainer energyStorage = this.getEnergyStorage(stack);
        return (int)((double)energyStorage.getStoredEnergy() / (double)energyStorage.getMaxCapacity() * (double)13.0F);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return 6544578;
    }

    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        WrappedItemEnergyContainer energy = this.getEnergyStorage(stack);
        tooltipComponents.add(TooltipUtils.getEnergyComponent(energy.getStoredEnergy(), energy.getMaxCapacity()));
        tooltipComponents.add(TooltipUtils.getActiveInactiveComponent(active(stack)));
        tooltipComponents.add(TooltipUtils.getDistributionModeComponent(mode(stack)));
        tooltipComponents.add(TooltipUtils.getMaxEnergyInComponent(energy.maxInsert()));
        tooltipComponents.add(TooltipUtils.getMaxEnergyOutComponent(energy.maxExtract()));
        TooltipUtils.addDescriptionComponent(tooltipComponents, net.memezforbeanz.starminerodyssey.constants.ConstantComponents.STELLAR_CORE_INFO);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide()) {
            return InteractionResultHolder.pass(player.getItemInHand(usedHand));
        } else {
            ItemStack stack = player.getItemInHand(usedHand);
            if (player.isShiftKeyDown()) {
                DistributionMode mode = toggleMode(stack);
                player.displayClientMessage(mode == DistributionMode.SEQUENTIAL ? ConstantComponents.CHANGE_MODE_SEQUENTIAL : ConstantComponents.CHANGE_MODE_ROUND_ROBIN, true);
            } else {
                boolean active = toggleActive(stack);
                player.displayClientMessage(active ? ConstantComponents.CAPACITOR_ENABLED : ConstantComponents.CAPACITOR_DISABLED, true);
            }

            return InteractionResultHolder.pass(stack);
        }
    }

    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide()) {
            if (entity.tickCount % 5 != 0) {
                if (active(stack)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        Inventory inventory = player.getInventory();
                        WrappedItemEnergyContainer container = this.getEnergyStorage(stack);
                        if (container.getStoredEnergy() != 0L) {
                            ItemStackHolder from = new ItemStackHolder(stack);
                            switch (mode(stack)) {
                                case SEQUENTIAL -> this.distributeSequential(from, container.maxExtract() * 5L, inventory);
                                case ROUND_ROBIN -> this.distributeRoundRobin(from, container.maxExtract() * 5L, inventory);
                            }

                            inventory.setItem(slotId, from.getStack());
                        }
                    }
                }
            }
        }
    }

    public void distributeSequential(ItemStackHolder from, long maxExtract, Inventory inventory) {
        for(int i = inventory.getContainerSize() - 1; i >= 0; --i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && !stack.is(this)) {
                ItemStackHolder to = new ItemStackHolder(stack);
                long moved = EnergyApi.moveEnergy(from, to, maxExtract, false);
                inventory.setItem(i, to.getStack());
                if (moved > 0L) {
                    return;
                }
            }
        }

    }

    public void distributeRoundRobin(ItemStackHolder from, long maxExtract, Inventory inventory) {
        int energyItems = 0;

        for(int i = 0; i < inventory.getContainerSize(); ++i) {
            if (EnergyContainer.holdsEnergy(inventory.getItem(i)) && !inventory.getItem(i).is(this)) {
                ++energyItems;
            }
        }

        if (energyItems != 0) {
            for(int i = 0; i < inventory.getContainerSize(); ++i) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.is(this) && !stack.isEmpty() && !stack.is(this)) {
                    ItemStackHolder to = new ItemStackHolder(stack);
                    EnergyApi.moveEnergy(from, to, maxExtract / (long)energyItems, false);
                    inventory.setItem(i, to.getStack());
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
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
}
