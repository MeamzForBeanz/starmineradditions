package net.memezforbeanz.starminerodyssey.blocks.fluid;

import earth.terrarium.botarium.common.registry.fluid.FluidData;
import earth.terrarium.botarium.common.registry.fluid.FluidProperties;
import earth.terrarium.botarium.common.registry.fluid.FluidRegistry;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.resources.ResourceLocation;


public final class ModFluidProperties {
    public static final FluidRegistry FLUID_PROPERTIES = new FluidRegistry(StarminerAdditions.MOD_ID);
    public static final FluidData HELIUM;

    public ModFluidProperties() {
    }

    static {
        HELIUM = FLUID_PROPERTIES.register("star_helium", FluidProperties.create().still(new ResourceLocation("starminer-additions:block/star_helium_still")).flowing(new ResourceLocation("starminer-additions:block/star_helium_flow")).overlay(new ResourceLocation("starminer-additions:block/star_helium_still")).screenOverlay(new ResourceLocation("textures/misc/underwater.png")).viscosity(1000).density(999).tintColor(-2431248).canConvertToSource(false).lightLevel(10).canSwim(true).temperature(1000).tickRate(30).motionScale(0.014));
    }
}