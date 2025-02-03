package net.memezforbeanz.starminerodyssey.tags;
import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public final class ModFluidTags {
    public static final TagKey<Fluid> HELIUM = tag("helium");

    private static TagKey<Fluid> tag(String name) {
        return TagKey.create(Registries.FLUID, new ResourceLocation(StarminerAdditions.MOD_ID, name));
    }
}