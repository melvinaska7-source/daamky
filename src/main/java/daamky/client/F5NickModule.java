package daamky.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import pydaamky.events.render.PreHudRenderEvent;
import pydaamky.utility.render.ColorRGBA;
import pydaamky.utility.render.CustomDrawContext;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
    name = "F5Nick",
    category = ModuleCategory.VISUALS,
    III_method_a89e5834 = "modules.descriptions.f5nick"
)
public class F5NickModule extends Module {

    private final MinecraftClient I_field_3a9bda27 = MinecraftClient.getInstance();

    private BooleanSetting onlyThirdPerson;
    private BooleanSetting showRank;
    private BooleanSetting background;
    private SliderSetting scale;
    private SliderSetting heightOffset;
    private ColorSetting backgroundColor;

    private final IiIIIiII_Class69<PreHudRenderEvent> onHud = event -> {
        PlayerEntity player = I_field_3a9bda27.player;
        if (player == null || I_field_3a9bda27.world == null) {
            return;
        }
        if (I_field_3a9bda27.options.hudHidden) {
            return;
        }
        if (onlyThirdPerson.i_method_9b12da03() && I_field_3a9bda27.options.getPerspective().isFirstPerson()) {
            return;
        }
        if (player.isSpectator() || player.isInvisible()) {
            return;
        }

        float tickDelta = event.getTickDelta();
        // как у ванильных ников: высота модели + 0.5, а не высота глаз —
        // иначе на голову заходит, что и было раньше.
        Vec3d pos = interpolatedPos(player, tickDelta)
            .add(0.0, player.getHeight() + 0.5 + heightOffset.Ii_method_a20abcd2(), 0.0);

        Vec2f screen = iIiiiiIII_Class377.I_method_211fc242(pos);
        if (screen == null) {
            return;
        }

        Text label = showRank.i_method_9b12da03() ? resolveLabel(player) : Text.literal(player.getGameProfile().getName());
        drawLabel(event.getContext(), label, screen.x, screen.y);
    };

    /**
     * Привилегия/префикс ранга приходит с сервера — либо через scoreboard-команду
     * (тогда её несёт {@link PlayerEntity#getDisplayName()}), либо через кастомное
     * имя в таб-листе (многие плагины рангов красят только его). Пробуем оба
     * источника: если сервер вообще не выдаёт префикс — покажется просто ник,
     * это нормально и не баг клиента, добавить с нуля несуществующую привилегию нельзя.
     */
    private Text resolveLabel(PlayerEntity player) {
        PlayerListEntry entry = I_field_3a9bda27.getNetworkHandler() != null
            ? I_field_3a9bda27.getNetworkHandler().getPlayerListEntry(player.getUuid())
            : null;
        if (entry != null && entry.getDisplayName() != null) {
            return entry.getDisplayName();
        }
        return player.getDisplayName();
    }

    public F5NickModule() {
        initSettings();
    }

    @Compile(obfuscation = 4)
    private void initSettings() {
        onlyThirdPerson = new BooleanSetting(this, "modules.settings.f5nick.only_third_person")
            .I_method_decd82b5(); // default true — виден только в виде от 3-го лица (F5)

        showRank = new BooleanSetting(this, "modules.settings.f5nick.show_rank")
            .I_method_decd82b5(); // default true — включает префикс/привилегию сервера

        background = new BooleanSetting(this, "modules.settings.f5nick.background")
            .I_method_decd82b5(); // default true

        backgroundColor = new ColorSetting(this, "modules.settings.f5nick.background_color", background::i_method_9b12da03)
            .I_method_a62f5dd6(new ColorRGBA(0.0f, 0.0f, 0.0f, 120.0f));

        scale = new SliderSetting(this, "modules.settings.f5nick.scale")
            .I_method_c8c9a7d7(0.5f)
            .i_method_65e2aff7(2.0f)
            .II_method_b0f56334(0.05f)
            .Ii_method_4e0e6b54(1.0f);

        heightOffset = new SliderSetting(this, "modules.settings.f5nick.height")
            .I_method_c8c9a7d7(-0.5f)
            .i_method_65e2aff7(1.0f)
            .II_method_b0f56334(0.05f)
            .Ii_method_4e0e6b54(0.0f); // 0 = ровно как у ванильных ников (height + 0.5)
    }

    private void drawLabel(CustomDrawContext context, Text label, float x, float y) {
        TextRenderer font = I_field_3a9bda27.textRenderer;
        float s = scale.Ii_method_a20abcd2();
        int width = font.getWidth(label);

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(x, y, 0.0f);
        matrices.scale(s, s, 1.0f);

        if (background.i_method_9b12da03()) {
            ColorRGBA bg = backgroundColor.I_method_bde5f08e();
            int argb = (Math.round(MathHelper.clamp(bg.getAlpha(), 0.0f, 255.0f)) << 24)
                | (Math.round(MathHelper.clamp(bg.getRed(), 0.0f, 255.0f)) << 16)
                | (Math.round(MathHelper.clamp(bg.getGreen(), 0.0f, 255.0f)) << 8)
                | Math.round(MathHelper.clamp(bg.getBlue(), 0.0f, 255.0f));
            context.fill(-width / 2 - 3, -2, width / 2 + 3, 11, argb);
        }

        context.drawCenteredTextWithShadow(font, label, 0, 0, 0xFFFFFF);
        matrices.pop();
    }

    private static Vec3d interpolatedPos(PlayerEntity player, float tickDelta) {
        return new Vec3d(
            MathHelper.lerp(tickDelta, player.prevX, player.getX()),
            MathHelper.lerp(tickDelta, player.prevY, player.getY()),
            MathHelper.lerp(tickDelta, player.prevZ, player.getZ())
        );
    }
}
