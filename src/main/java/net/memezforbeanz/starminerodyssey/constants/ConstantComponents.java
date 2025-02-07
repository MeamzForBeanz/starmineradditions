package net.memezforbeanz.starminerodyssey.constants;


import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class ConstantComponents {
    public static final Component STELLAR_GRAVITY_INFO;

    public ConstantComponents() {
    }

    static {

        STELLAR_GRAVITY_INFO = Component.translatable("info.starminer-additions.stellar_gravity").withStyle(ChatFormatting.GRAY);
    }
}
