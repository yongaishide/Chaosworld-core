package com.yongaishide.chaosworld.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfirmQuitScreen extends Screen {
    private static final Component PROMPT = Component.literal("\u786E\u5B9A\u8981\u9000\u51FA\u6E38\u620F\u5417\uFF1F");
    private static final Component QUIT = Component.literal("\u9000\u51FA\u6E38\u620F");
    private static final Component QUIT_HOVER = Component.literal("\u4E0D\uFF01\u4E0D\u8981\u79BB\u5F00");
    private static final Component CANCEL = Component.literal("\u7EE7\u7EED\u6E38\u73A9");

    private Button quitButton;

    public ConfirmQuitScreen() {
        super(Component.literal("\u786E\u8BA4\u9000\u51FA"));
    }

    @Override
    protected void init() {
        int cx = width / 2;
        int cy = height / 2;

        quitButton = addRenderableWidget(Button.builder(QUIT, btn -> {
            Minecraft.getInstance().stop();
        }).bounds(cx - 110, cy - 10, 100, 20).build());

        addRenderableWidget(Button.builder(CANCEL, btn -> {
            onClose();
        }).bounds(cx + 10, cy - 10, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(font, PROMPT, width / 2, height / 2 - 40, 0xFFFFFF);
        if (quitButton.isHoveredOrFocused()) {
            quitButton.setMessage(QUIT_HOVER);
        } else {
            quitButton.setMessage(QUIT);
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
