package moscow.daamky.mixin.minecraft.client.gui.overlay;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import daamky.client.RemovalsModule;
import daamky.client.IiIiiIIII_Class177;
import daamky.client.DaamkyClient;
import daamky.client.IiiIIIIii_Class196;
import daamky.client.iIIIiiIii_Class284;
import daamky.client.iIIIiiiII_Class285;
import daamky.client.iIIiIIiIi_Class294;

@Mixin(value={BossBarHud.class})
public class BossBarHudMixin
implements iIIiIIiIi_Class294 {
    @Shadow
    @Final
    private Map<UUID, ClientBossBar> field_2060;
    @Unique
    private static final Pattern PVP_TIME_PATTERN = Pattern.compile("(\\d+)\\s*(?:[\u0441c][\u0435e][\u043ak]\\.?|[\u0441c][\u0435e][\u043ak][\u0443y]?[\u043dnh][\u0434d]?)(?=$|\\s|\\p{Punct})", 322);
    @Unique
    private static final Pattern DAAMKY_PVP_TIME_LOOSE = Pattern.compile("(\\d{1,4})[^\\d]{0,4}?[\u0441c][\u0435e][\u043ak]", 66);
    @Unique
    private static final Pattern DAAMKY_NUMBER = Pattern.compile("\\d{1,4}");
    @Unique
    private static final String DAAMKY_CYRILLIC = "\u0430\u0441\u0435\u043e\u0440\u0445\u0443\u043a\u043d\u0432\u0442\u043c";
    @Unique
    private static final String DAAMKY_LATIN = "aceopxykhbtm";
    private static final String FILTERED_TEXT = "\ub445\ua223\ua203\ub444\ua223\ua205";

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void onRenderHead(DrawContext drawContext, CallbackInfo callbackInfo) {
        int n = 0;
        for (ClientBossBar object : this.field_2060.values()) {
            int n2;
            String bl;
            if (object.getName() == null || !BossBarHudMixin.daamky$isPvpBar(bl = BossBarHudMixin.daamky$plain(object.getName().getString())) || (n2 = BossBarHudMixin.daamky$seconds(bl)) <= n) continue;
            n = n2;
        }
        iIIIiiiII_Class285.I_method_b32cf4b5(n > 0);
        iIIIiiiII_Class285.I_method_b32cb4e4(n);
        RemovalsModule iIiIiIIiI_Class83 = DaamkyClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected()) {
            return;
        }
        if (!(DaamkyClient.getInstance().i_method_e8604970() || !DaamkyClient.getInstance().I_method_35687482().I_method_35128395().isShowing() || this.field_2060.isEmpty() || iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected() || iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.iI_field_ac164e6c))) {
            boolean bl;
            IiIiiIIII_Class177 iiIiiIIII_Class177 = DaamkyClient.getInstance().I_method_35687482().I_method_35128395();
            boolean bl2 = bl = iiIiiIIII_Class177.isShowing() && iiIiiIIII_Class177.i_method_c0baaa28().stream().anyMatch(iiIiiIiii_Class184 -> iiIiiIiii_Class184 instanceof IiiIIIIii_Class196);
            if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected() || iIIIiiiII_Class285.I_field_5a && bl) {
                return;
            }
            drawContext.getMatrices().push();
            drawContext.getMatrices().translate(0.0f, DaamkyClient.getInstance().I_method_35687482().I_method_35128395().I_method_fc091775().i_field_46 + 7.0f, 0.0f);
        }
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void render(CallbackInfo callbackInfo) {
        boolean bl;
        RemovalsModule iIiIiIIiI_Class83 = DaamkyClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        IiIiiIIII_Class177 iiIiiIIII_Class177 = DaamkyClient.getInstance().I_method_35687482().I_method_35128395();
        boolean bl2 = bl = iiIiiIIII_Class177.isShowing() && iiIiiIIII_Class177.i_method_c0baaa28().stream().anyMatch(iiIiiIiii_Class184 -> iiIiiIiii_Class184 instanceof IiiIIIIii_Class196);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected() || iIIIiiiII_Class285.I_field_5a && bl) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void onRenderReturn(DrawContext drawContext, CallbackInfo callbackInfo) {
        int n = 19 * this.field_2060.size();
        RemovalsModule iIiIiIIiI_Class83 = DaamkyClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected()) {
            return;
        }
        if (!(DaamkyClient.getInstance().i_method_e8604970() || !DaamkyClient.getInstance().I_method_35687482().I_method_35128395().isShowing() || this.field_2060.isEmpty() || iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.II_method_92acf0f3().isSelected() || iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.iI_field_ac164e6c))) {
            drawContext.getMatrices().pop();
        }
    }

    @Unique
    private static String daamky$plain(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c >= '\ue000' && c <= '\uf8ff' || Character.getType(c) == 16) continue;
            stringBuilder.append(Character.isSpaceChar(c) ? (char)' ' : (char)c);
        }
        return stringBuilder.toString();
    }

    @Unique
    private static boolean daamky$isPvpBar(String string) {
        String string2 = string.toLowerCase(Locale.ROOT);
        if (string2.contains("\u0431\u043e\u0439") || string2.contains("\u0431\u043e\u044e") || string2.contains("\u043f\u0432\u043f")) {
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            int n = DAAMKY_CYRILLIC.indexOf(c);
            stringBuilder.append(n < 0 ? c : DAAMKY_LATIN.charAt(n));
        }
        return stringBuilder.indexOf("pvp") >= 0;
    }

    @Unique
    private static int daamky$seconds(String string) {
        Matcher matcher = PVP_TIME_PATTERN.matcher(string);
        if (matcher.find()) {
            return BossBarHudMixin.daamky$parse(matcher.group(1));
        }
        Matcher matcher2 = DAAMKY_PVP_TIME_LOOSE.matcher(string);
        if (matcher2.find()) {
            return BossBarHudMixin.daamky$parse(matcher2.group(1));
        }
        Matcher matcher3 = DAAMKY_NUMBER.matcher(string);
        if (!matcher3.find()) {
            return -1;
        }
        String string2 = matcher3.group();
        return matcher3.find() ? -1 : BossBarHudMixin.daamky$parse(string2);
    }

    @Unique
    private static int daamky$parse(String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            return -1;
        }
    }
}

