package net.memezforbeanz.starminerodyssey.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import earth.terrarium.botarium.common.registry.fluid.BotariumSourceFluid;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.memezforbeanz.starminerodyssey.blocks.fluid.HeliumLiquidBlock;
import net.memezforbeanz.starminerodyssey.blocks.fluid.ModFluidProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;

public final class ModFluids {
    public static final ResourcefulRegistry<Fluid> FLUIDS;

    public static final RegistryEntry<FlowingFluid> HELIUM;
    public static final RegistryEntry<FlowingFluid> FLOWING_HELIUM;

    public ModFluids() {
    }

    static {
        FLUIDS = ResourcefulRegistries.create(BuiltInRegistries.FLUID, StarminerAdditions.MOD_ID);
        HELIUM = FLUIDS.register("star_helium", () -> new BotariumSourceFluid(net.memezforbeanz.starminerodyssey.blocks.fluid.ModFluidProperties.HELIUM));
        FLOWING_HELIUM = FLUIDS.register("flowing_star_helium", () -> new BotariumFlowingFluid(ModFluidProperties.HELIUM));
    }
}
