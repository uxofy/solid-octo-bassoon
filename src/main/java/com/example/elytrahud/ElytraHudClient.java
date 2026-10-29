package com.example.elytrahud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ElytraHudClient implements ClientModInitializer {
    private static final Identifier ICON = Identifier.of("elytrahud", "textures/gui/elytra_icon.png");
    private static final int TEX_SIZE = 32;   // размер png
    private static final int DRAW_SIZE = 16;  // размер на экране
    private static final int OFFSET_Y = 8;    // отступ вниз от прицела

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(ElytraHudClient::render);
    }

    private static void render(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;
        if (!mc.options.getPerspective().isFirstPerson()) return;

        ItemStack chest = mc.player.getEquippedStack(EquipmentSlot.CHEST);
        if (!chest.isOf(Items.ELYTRA)) return;

        int x = context.getScaledWindowWidth() / 2 - DRAW_SIZE / 2;
        int y = context.getScaledWindowHeight() / 2 + OFFSET_Y;

        // сломанные элытры (1 прочность) - красным
        boolean broken = chest.getMaxDamage() - chest.getDamage() <= 1;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (broken) context.setShaderColor(1f, 0.25f, 0.25f, 1f);
        context.drawTexture(ICON, x, y, DRAW_SIZE, DRAW_SIZE, 0, 0, TEX_SIZE, TEX_SIZE, TEX_SIZE, TEX_SIZE);
        context.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
    }
}
