package daamky.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import pydaamky.events.render.Render3DEvent;
import pydaamky.utility.render.ColorRGBA;
import pydaamky.utility.render.PyShader;
import pydaamky.utility.render.PyShaders;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
    name = "Aurora",
    category = ModuleCategory.VISUALS,
    III_method_a89e5834 = "modules.descriptions.aurora"
)
public class AuroraModule extends Module {

    private final MinecraftClient I_field_3a9bda27 = MinecraftClient.getInstance();

    private SliderSetting intensity;
    private SliderSetting speed;
    private SliderSetting height;
    private SliderSetting spread;
    private SliderSetting ripple;
    private ColorSetting colorA;
    private ColorSetting colorB;
    private BooleanSetting onlyNight;
    private BooleanSetting onlyOverworld;

    private PyShader shader;
    private final PyShaders shaders = new PyShaders();

    private final IiIIIiII_Class69<Render3DEvent> onRender = event -> {
        if (I_field_3a9bda27.world == null || I_field_3a9bda27.player == null) {
            return;
        }
        ClientWorld world = I_field_3a9bda27.world;

        if (onlyOverworld.i_method_9b12da03() && !world.getDimension().hasSkyLight()) {
            return;
        }
        if (onlyNight.i_method_9b12da03()) {
            float skyAngle = world.getSkyAngle(event.getTickDelta());
            // ночь примерно 0.25 .. 0.75
            if (skyAngle < 0.22f || skyAngle > 0.78f) {
                return;
            }
        }

        ensureShader();
        if (shader == null || !shader.valid()) {
            return;
        }
        drawAurora(event);
    };

    public AuroraModule() {
        initSettings();
    }

    @Compile(obfuscation = 4)
    private void initSettings() {
        intensity = new SliderSetting(this, "modules.settings.aurora.intensity")
            .I_method_c8c9a7d7(0.0f)
            .i_method_65e2aff7(100.0f)
            .II_method_b0f56334(1.0f)
            .Ii_method_4e0e6b54(55.0f)
            .I_method_bfc3b958(v -> "%");

        speed = new SliderSetting(this, "modules.settings.aurora.speed")
            .I_method_c8c9a7d7(0.1f)
            .i_method_65e2aff7(3.0f)
            .II_method_b0f56334(0.05f)
            .Ii_method_4e0e6b54(0.7f);

        height = new SliderSetting(this, "modules.settings.aurora.height")
            .I_method_c8c9a7d7(0.05f)
            .i_method_65e2aff7(0.9f)
            .II_method_b0f56334(0.01f)
            .Ii_method_4e0e6b54(0.35f);

        spread = new SliderSetting(this, "modules.settings.aurora.spread")
            .I_method_c8c9a7d7(0.3f)
            .i_method_65e2aff7(2.5f)
            .II_method_b0f56334(0.05f)
            .Ii_method_4e0e6b54(1.0f);

        ripple = new SliderSetting(this, "modules.settings.aurora.ripple")
            .I_method_c8c9a7d7(0.0f)
            .i_method_65e2aff7(2.0f)
            .II_method_b0f56334(0.05f)
            .Ii_method_4e0e6b54(0.8f);

        colorA = new ColorSetting(this, "modules.settings.aurora.color_a")
            .I_method_a62f5dd6(new ColorRGBA(40.0f, 255.0f, 120.0f, 255.0f));

        colorB = new ColorSetting(this, "modules.settings.aurora.color_b")
            .I_method_a62f5dd6(new ColorRGBA(160.0f, 80.0f, 255.0f, 255.0f));

        onlyNight = new BooleanSetting(this, "modules.settings.aurora.only_night");
        // default false

        onlyOverworld = new BooleanSetting(this, "modules.settings.aurora.only_overworld")
            .I_method_decd82b5(); // default true
    }

    private void ensureShader() {
        if (shader != null && shader.valid()) {
            return;
        }
        try {
            // Свои vertex+fragment с #version → header.fsh НЕ подмешивается
            shader = shaders.create("aurora", AURORA_VERTEX, AURORA_FRAGMENT);
        } catch (Throwable t) {
            System.err.println("[Aurora] shader compile failed: " + t.getMessage());
            t.printStackTrace();
            shader = null;
        }
    }

    private void drawAurora(Render3DEvent event) {
        float inv = intensity.Ii_method_a20abcd2() / 100.0f;
        if (inv <= 0.001f) {
            return;
        }

        ColorRGBA ca = colorA.I_method_bde5f08e();
        ColorRGBA cb = colorB.I_method_bde5f08e();

        shader.set("Intensity", inv);
        shader.set("Speed", speed.Ii_method_a20abcd2());
        shader.set("Height", height.Ii_method_a20abcd2());
        shader.set("Spread", spread.Ii_method_a20abcd2());
        shader.set("Ripple", ripple.Ii_method_a20abcd2());
        shader.set("ColorA", ca.getRed() / 255.0f, ca.getGreen() / 255.0f, ca.getBlue() / 255.0f);
        shader.set("ColorB", cb.getRed() / 255.0f, cb.getGreen() / 255.0f, cb.getBlue() / 255.0f);

        // additive glow поверх мира
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(
            GlStateManager.SrcFactor.SRC_ALPHA,
            GlStateManager.DstFactor.ONE
        );
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        RenderSystem.disableCull();

        // fullscreen сам выставит Time/Resolution/InvViewProj/CamPos из event
        try {
            shader.fullscreen(event);
        } catch (Throwable t) {
            System.err.println("[Aurora] draw error: " + t.getMessage());
        } finally {
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
        }
    }

    @Override
    public void onDisable() {
        if (shader != null) {
            try {
                shaders.dispose(shader);
            } catch (Throwable ignored) {
            }
            shader = null;
        }
    }

    // =========================================================================
    //  VERTEX — attribute names must match PyShader bind: Position, UV
    // =========================================================================
    private static final String AURORA_VERTEX = """
        #version 150

        in vec3 Position;
        in vec2 UV;

        uniform mat4 ModelViewMat;
        uniform mat4 ProjMat;

        out vec2 vUV;

        void main() {
            vUV = UV;
            gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
        }
        """;

    // =========================================================================
    //  FRAGMENT — многослойное процедурное северное сияние (fbm-шум + волны)
    // =========================================================================
    private static final String AURORA_FRAGMENT = """
        #version 150

        in vec2 vUV;
        out vec4 fragColor;

        uniform float Time;
        uniform vec2  Resolution;
        uniform float Intensity;
        uniform float Speed;
        uniform float Height;
        uniform float Spread;
        uniform float Ripple;
        uniform vec3  ColorA;
        uniform vec3  ColorB;
        uniform mat4  InvViewProj;
        uniform vec3  CamPos;

        float hash(vec2 p) {
            p = fract(p * vec2(123.34, 456.21));
            p += dot(p, p + 45.32);
            return fract(p.x * p.y);
        }

        float noise(vec2 p) {
            vec2 i = floor(p);
            vec2 f = fract(p);
            f = f * f * (3.0 - 2.0 * f);
            float a = hash(i);
            float b = hash(i + vec2(1.0, 0.0));
            float c = hash(i + vec2(0.0, 1.0));
            float d = hash(i + vec2(1.0, 1.0));
            return mix(mix(a, b, f.x), mix(c, d, f.x), f.y);
        }

        float fbm(vec2 p) {
            float v = 0.0;
            float a = 0.5;
            for (int i = 0; i < 4; i++) {
                v += a * noise(p);
                p = p * 2.03 + vec2(17.1, 9.7);
                a *= 0.5;
            }
            return v;
        }

        vec3 viewRay(vec2 uv) {
            vec4 ndc = vec4(uv * 2.0 - 1.0, 1.0, 1.0);
            vec4 world = InvViewProj * ndc;
            world.xyz /= max(world.w, 1e-5);
            return normalize(world.xyz - CamPos);
        }

        void main() {
            vec3 rd = viewRay(vUV);
            float elev = rd.y;

            if (elev < 0.02) {
                fragColor = vec4(0.0);
                return;
            }

            float horizon = smoothstep(0.02, 0.18, elev);
            float zenith  = 1.0 - smoothstep(Height, Height + 0.45, elev);
            float skyMask = horizon * zenith;
            if (skyMask <= 0.001) {
                fragColor = vec4(0.0);
                return;
            }

            vec2 dir = normalize(rd.xz + 1e-5);
            float along = atan(dir.y, dir.x);
            float t = Time * Speed;

            // волнистые складки занавеса (реалистичные "ленты")
            float fold = sin(along * 5.0 + t * 0.6) * Ripple * 0.25
                       + sin(along * 11.0 - t * 0.9) * Ripple * 0.12;

            vec2 nUV = vec2((along + fold) * 1.6 * Spread, elev * 3.2 - t * 0.35);
            float n1 = fbm(nUV);
            float n2 = fbm(nUV * 1.7 + vec2(t * 0.15, -t * 0.08));
            float n3 = fbm(nUV * 3.1 + vec2(-t * 0.22, t * 0.05));

            float curtain = n1 * 0.55 + n2 * 0.3 + n3 * 0.15;
            curtain = pow(smoothstep(0.32, 0.85, curtain), 1.3);

            // вертикальные лучи-полосы внутри занавеса
            float rays = pow(smoothstep(0.5, 0.95, fbm(vec2((along + fold) * 9.0, elev * 1.2 - t * 0.5))), 3.0) * 0.5;

            float band2 = pow(smoothstep(0.55, 0.9, fbm(nUV * 2.3 + 10.0)), 2.0) * 0.5;
            float glow = (curtain + band2 + rays) * skyMask;

            // цвет: у основания ColorA, к вершине подмешивается ColorB + лёгкое свечение
            float mixF = clamp(elev / max(Height + 0.45, 0.01) + n2 * 0.3, 0.0, 1.0);
            vec3 col = mix(ColorA, ColorB, mixF);
            col = mix(col, vec3(1.0), rays * 0.25);

            glow *= 0.85 + 0.15 * sin(along * 4.0 + t);

            fragColor = vec4(col * glow * Intensity, glow * Intensity);
        }
        """;
}
