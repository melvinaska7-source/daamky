package daamky.client;

import java.util.Random;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import pydaamky.events.game.BreakTotemEvent;
import pydaamky.events.game.EntityDeathEvent;
import pydaamky.events.game.GameTickEvent;
import ua.mintantileak.spk.Compile;

/**
 * Кастомный звуковой пак "Forestmorn": ezz / tuda / daamkyboost / totem /
 * sinshluhi / nespr / welcome, лежат в assets/daamky/sounds/forestmorn/ (ogg)
 * и зарегистрированы в assets/daamky/sounds.json как forestmorn/&lt;имя&gt;.
 *
 * Отдельный класс (не SoundsModule) специально: SoundsModule уже занят
 * встроенным войс-паком (kill, totem, start, leave — iiIIiiiIi_Class414
 * и iiIiIIIII_Class417), и на него завязаны внешние классы (IiiIIiIii_Class204,
 * RocknetMenu, GlobalsMenuModule и т.д.) — переписывать его сломало бы сборку.
 */
@ModuleInfo(
    name = "Forestmorn",
    category = ModuleCategory.OTHER,
    III_method_a89e5834 = "modules.descriptions.forestmorn"
)
public class ForestMornModule extends Module {

    private final MinecraftClient I_field_3a9bda27 = MinecraftClient.getInstance();
    private final Random random = new Random();
    private boolean welcomePlayed = false;

    private SliderSetting volume;
    private BooleanSetting killSounds;
    private BooleanSetting totemSound;
    private SliderSetting totemRange;
    private BooleanSetting deathSounds;
    private BooleanSetting welcomeSound;

    // --- смерть/убийство ---------------------------------------------------
    private final IiIIIiII_Class69<EntityDeathEvent> onDeath = event -> {
        LivingEntity entity = event.getEntity();
        if (entity == null || I_field_3a9bda27.player == null) {
            return;
        }

        if (entity == I_field_3a9bda27.player) {
            // умер сам игрок
            if (!deathSounds.i_method_9b12da03()) {
                return;
            }
            DamageSource source = event.getSource();
            boolean killedByPlayer = source != null
                && source.getAttacker() instanceof PlayerEntity attacker
                && attacker != I_field_3a9bda27.player;
            play(killedByPlayer ? "sinshluhi" : "nespr");
            return;
        }

        // кто-то другой (моб/игрок) убит — проверяем, что это сделали мы
        if (!killSounds.i_method_9b12da03()) {
            return;
        }
        DamageSource source = event.getSource();
        boolean killedByUs = (source != null && source.getAttacker() == I_field_3a9bda27.player)
            || event.getKillerEntity() == I_field_3a9bda27.player;
        if (!killedByUs) {
            return;
        }

        // ezz / tuda / daamkyboost — только один из трёх, никогда вместе
        String[] killVariants = {"ezz", "tuda", "daamkyboost"};
        play(killVariants[random.nextInt(killVariants.length)]);
    };

    // --- тотем: и свой, и вражеский — один и тот же звук --------------------
    private final IiIIIiII_Class69<BreakTotemEvent> onTotem = event -> {
        if (!totemSound.i_method_9b12da03() || I_field_3a9bda27.player == null || event.getEntity() == null) {
            return;
        }
        float range = totemRange.Ii_method_a20abcd2();
        if (range > 0.0f && I_field_3a9bda27.player.distanceTo(event.getEntity()) > range) {
            return;
        }
        play("totem");
    };

    // --- запуск игры: один раз за сессию, на первом же тике ----------------
    private final IiIIIiII_Class69<GameTickEvent> onTick = event -> {
        if (welcomePlayed || !welcomeSound.i_method_9b12da03()) {
            return;
        }
        welcomePlayed = true;
        play("welcome");
    };

    public ForestMornModule() {
        initSettings();
    }

    @Compile(obfuscation = 4)
    private void initSettings() {
        volume = new SliderSetting(this, "modules.settings.forestmorn.volume")
            .I_method_c8c9a7d7(0.0f)
            .i_method_65e2aff7(100.0f)
            .II_method_b0f56334(5.0f)
            .Ii_method_4e0e6b54(80.0f)
            .I_method_d41e7abf("%");

        killSounds = new BooleanSetting(this, "modules.settings.forestmorn.kill")
            .I_method_decd82b5(); // default true

        totemSound = new BooleanSetting(this, "modules.settings.forestmorn.totem")
            .I_method_decd82b5(); // default true

        totemRange = new SliderSetting(this, "modules.settings.forestmorn.totem_range")
            .I_method_c8c9a7d7(0.0f)
            .i_method_65e2aff7(64.0f)
            .II_method_b0f56334(1.0f)
            .Ii_method_4e0e6b54(32.0f)
            .I_method_d41e7abf("блоков"); // 0 = без ограничения по дистанции

        deathSounds = new BooleanSetting(this, "modules.settings.forestmorn.death")
            .I_method_decd82b5(); // default true

        welcomeSound = new BooleanSetting(this, "modules.settings.forestmorn.welcome")
            .I_method_decd82b5(); // default true
    }

    private void play(String name) {
        try {
            new iiIIiiiii_Class416("forestmorn/" + name, volume.Ii_method_a20abcd2() / 100.0f)
                .I_method_87d2e181(volume.Ii_method_a20abcd2() / 100.0f);
        } catch (Throwable t) {
            System.err.println("[Forestmorn] не удалось проиграть '" + name + "': " + t.getMessage());
        }
    }
}
