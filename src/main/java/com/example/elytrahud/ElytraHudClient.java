package com.example.elytrahud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ElytraHudClient implements ClientModInitializer {
    private static final Identifier ICON = Identifier.of("elytrahud", "textures/gui/elytra_icon.png");

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(ElytraHudClient::render);
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null || mc.options.hudHidden) return;
        if (!mc.options.getPerspective().isFirstPerson()) return;
        if (!mc.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)) return;

        int x = context.getScaledWindowWidth() / 2 - 8;
        int y = context.getScaledWindowHeight() / 2 + 8;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ICON, x, y, 0f, 0f, 16, 16, 32, 32, 32, 32);
    }
}
