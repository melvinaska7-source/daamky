package moscow.daamky.mixin.minecraft.client.gui.overlay;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import daamky.client.BeautifullyModule;
import daamky.client.IiiiIiiII_Class237;

@Mixin(value={ChatHud.class})
public abstract class ChatHudMixin {
    @Unique
    private static final float DAAMKY_FADE_TICKS = 4.0f;
    @Unique
    private static final float DAAMKY_SHIFT = 12.0f;
    @Unique
    private static final float DAAMKY_BAR_HEIGHT = 16.0f;
    @Shadow
    @Final
    private List<ChatHudLine.Visible> field_2064;
    @Shadow
    private int field_2066;
    @Unique
    private static boolean daamky$animate;
    @Unique
    private static float daamky$tick;
    @Unique
    private static boolean daamky$focused;
    @Unique
    private static boolean daamky$wasFocused;
    @Unique
    private static long daamky$openStart;
    @Unique
    private static long daamky$closeStart;
    @Unique
    private static int daamky$focusedLines;
    @Unique
    private static float daamky$lineOpacity;
    @Unique
    private static float daamky$lineAlpha;
    @Unique
    private static float daamky$lineShift;
    @Unique
    private static int daamky$linesBefore;
    @Unique
    private static float daamky$slideLines;
    @Unique
    private static long daamky$slideStart;
    @Unique
    private static boolean daamky$shifted;

    @Shadow
    public abstract int method_44752();

    @Shadow
    public abstract double method_1814();

    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="HEAD")})
    private void daamky$rememberLineCount(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
        daamky$linesBefore = this.field_2064.size();
    }

    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="TAIL")})
    private void daamky$startSlide(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
        if (!BeautifullyModule.IiI_method_b163c683() || this.field_2066 > 0) {
            return;
        }
        int n = this.field_2064.size() - daamky$linesBefore;
        if (n <= 0) {
            return;
        }
        daamky$slideLines = Math.min(4, n);
        daamky$slideStart = System.currentTimeMillis();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void daamky$beginRender(DrawContext drawContext, int n, int n2, int n3, boolean bl, CallbackInfo callbackInfo) {
        daamky$animate = BeautifullyModule.IiI_method_b163c683();
        daamky$tick = (float)n + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
        daamky$focused = bl;
        daamky$lineOpacity = 1.0f;
        daamky$lineAlpha = 1.0f;
        daamky$lineShift = 0.0f;
        daamky$shifted = false;
        if (bl != daamky$wasFocused) {
            daamky$wasFocused = bl;
            if (bl) {
                daamky$openStart = System.currentTimeMillis();
            } else {
                daamky$closeStart = System.currentTimeMillis();
            }
        }
        if (!daamky$animate || daamky$slideStart == Long.MIN_VALUE) {
            return;
        }
        float f = (float)(System.currentTimeMillis() - daamky$slideStart) / 200.0f;
        if (f >= 1.0f || f < 0.0f) {
            return;
        }
        float f2 = 1.0f - IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
        float f3 = f2 * daamky$slideLines * this.method_44752() * (float)this.method_1814();
        if (f3 <= 0.05f) {
            return;
        }
        drawContext.enableScissor(0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight() - 40);
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(0.0f, f3, 0.0f);
        daamky$shifted = true;
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void daamky$endRender(DrawContext drawContext, int n, int n2, int n3, boolean bl, CallbackInfo callbackInfo) {
        if (daamky$shifted) {
            daamky$shifted = false;
            drawContext.getMatrices().pop();
            drawContext.disableScissor();
        }
        this.daamky$drawClosingInput(drawContext);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Ljava/util/List;get(I)Ljava/lang/Object;", ordinal=0))
    private Object daamky$captureLine(List<ChatHudLine.Visible> list, int n) {
        float f;
        ChatHudLine.Visible visible = list.get(n);
        daamky$lineOpacity = 1.0f;
        daamky$lineAlpha = 1.0f;
        daamky$lineShift = 0.0f;
        if (!daamky$animate || !(visible instanceof ChatHudLine.Visible)) {
            return visible;
        }
        ChatHudLine.Visible visible2 = visible;
        float f2 = ChatHudMixin.daamky$life(daamky$tick - (float)visible2.addedTime());
        if (daamky$focused) {
            daamky$lineAlpha = f = MathHelper.lerp((float)ChatHudMixin.daamky$openProgress(), (float)f2, (float)1.0f);
        } else {
            daamky$lineOpacity = f = Math.max(f2, ChatHudMixin.daamky$closeFade());
        }
        daamky$lineShift = (1.0f - f) * 12.0f;
        return visible;
    }

    @ModifyConstant(method={"render"}, constant={@Constant(intValue=200)})
    private int daamky$keepClosingLines(int n) {
        if (!daamky$animate || daamky$focused || ChatHudMixin.daamky$closeFade() <= 0.0f) {
            return n;
        }
        return Integer.MAX_VALUE;
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHud;getMessageOpacityMultiplier(I)D"))
    private static double daamky$messageOpacity(int n) {
        if (!daamky$animate) {
            return ChatHudMixin.daamky$life(n);
        }
        return daamky$lineOpacity;
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHud;getVisibleLineCount()I"))
    private int daamky$visibleLineCount(ChatHud chatHud) {
        int n = chatHud.getVisibleLineCount();
        if (daamky$focused) {
            daamky$focusedLines = n;
            return n;
        }
        if (!daamky$animate || ChatHudMixin.daamky$closeFade() <= 0.0f) {
            return n;
        }
        return Math.max(n, daamky$focusedLines);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal=0))
    private void daamky$fillLine(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        this.daamky$fillAnimated(drawContext, n, n2, n3, n4, n5);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal=1))
    private void daamky$fillIndicator(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        this.daamky$fillAnimated(drawContext, n, n2, n3, n4, n5);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal=0))
    private int daamky$drawLine(DrawContext drawContext, TextRenderer textRenderer, OrderedText orderedText, int n, int n2, int n3) {
        if (!ChatHudMixin.daamky$lineAnimated()) {
            return drawContext.drawTextWithShadow(textRenderer, orderedText, n, n2, n3);
        }
        int n4 = ChatHudMixin.daamky$fade(n3);
        if (ChatHudMixin.daamky$invisible(n4)) {
            return 0;
        }
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.translate(-daamky$lineShift, 0.0f, 0.0f);
        int n5 = drawContext.drawTextWithShadow(textRenderer, orderedText, n, n2, n4);
        matrixStack.translate(daamky$lineShift, 0.0f, 0.0f);
        return n5;
    }

    @Unique
    private void daamky$fillAnimated(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        if (!ChatHudMixin.daamky$lineAnimated()) {
            drawContext.fill(n, n2, n3, n4, n5);
            return;
        }
        int n6 = ChatHudMixin.daamky$fade(n5);
        if (ChatHudMixin.daamky$invisible(n6)) {
            return;
        }
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.translate(-daamky$lineShift, 0.0f, 0.0f);
        drawContext.fill(n, n2, n3, n4, n6);
        matrixStack.translate(daamky$lineShift, 0.0f, 0.0f);
    }

    @Unique
    private void daamky$drawClosingInput(DrawContext drawContext) {
        if (!daamky$animate || daamky$focused) {
            return;
        }
        float f = ChatHudMixin.daamky$closeFade();
        if (f <= 0.0f) {
            return;
        }
        int n = drawContext.getScaledWindowWidth();
        int n2 = drawContext.getScaledWindowHeight();
        int n3 = MinecraftClient.getInstance().options.getTextBackgroundColor(Integer.MIN_VALUE);
        int n4 = MathHelper.clamp((int)((int)((float)(n3 >>> 24) * f)), (int)0, (int)255);
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.push();
        matrixStack.translate(0.0f, (1.0f - f) * 16.0f, 0.0f);
        drawContext.fill(2, n2 - 14, n - 2, n2 - 2, n3 & 0xFFFFFF | n4 << 24);
        matrixStack.pop();
    }

    @Unique
    private static boolean daamky$lineAnimated() {
        return daamky$lineShift != 0.0f || daamky$lineAlpha < 1.0f;
    }

    @Unique
    private static float daamky$life(float f) {
        float f2 = MathHelper.clamp((float)((200.0f - f) / 4.0f), (float)0.0f, (float)1.0f);
        return f2 * f2;
    }

    @Unique
    private static float daamky$openProgress() {
        if (daamky$openStart == Long.MIN_VALUE) {
            return 1.0f;
        }
        float f = (float)(System.currentTimeMillis() - daamky$openStart) / 200.0f;
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        return IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
    }

    @Unique
    private static float daamky$closeFade() {
        if (daamky$closeStart == Long.MIN_VALUE) {
            return 0.0f;
        }
        float f = (float)(System.currentTimeMillis() - daamky$closeStart) / 200.0f;
        if (f <= 0.0f) {
            return 1.0f;
        }
        if (f >= 1.0f) {
            return 0.0f;
        }
        return 1.0f - IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
    }

    @Unique
    private static boolean daamky$invisible(int n) {
        return (n & 0xFC000000) == 0;
    }

    @Unique
    private static int daamky$fade(int n) {
        if (daamky$lineAlpha >= 1.0f) {
            return n;
        }
        int n2 = MathHelper.clamp((int)((int)((float)(n >>> 24) * daamky$lineAlpha)), (int)0, (int)255);
        return n & 0xFFFFFF | n2 << 24;
    }

    static {
        daamky$openStart = Long.MIN_VALUE;
        daamky$closeStart = Long.MIN_VALUE;
        daamky$lineOpacity = 1.0f;
        daamky$lineAlpha = 1.0f;
        daamky$slideStart = Long.MIN_VALUE;
    }
}
