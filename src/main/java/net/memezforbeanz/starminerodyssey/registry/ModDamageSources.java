package net.memezforbeanz.starminerodyssey.registry;

import net.memezforbeanz.starminerodyssey.StarminerAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
public final class ModDamageSources {
    public static final ResourceKey<DamageType> HELIUM;


    public ModDamageSources() {
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
    static {
        HELIUM = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(StarminerAdditions.MOD_ID, "star_helium"));
    }
}
