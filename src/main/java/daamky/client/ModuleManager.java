package daamky.client;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import pydaamky.events.game.GameTickEvent;
import pydaamky.events.player.ClientPlayerTickEvent;
import pydaamky.events.render.HudRenderEvent;
import pydaamky.events.window.KeyPressEvent;
import pydaamky.events.window.MouseEvent;
import daamky.client.AntiAimModule;
import daamky.client.AssistModule;
import daamky.client.AuctionModule;
import daamky.client.F5NickModule;
import daamky.client.AutoAcceptModule;
import daamky.client.AutoAuthModule;
import daamky.client.AutoBuyModule;
import daamky.client.AutoDuelsModule;
import daamky.client.AutoJoinModule;
import daamky.client.AutoResellModule;
import daamky.client.BaseFinderModule;
import daamky.client.DeathCordsModule;
import daamky.client.EffectRemoverModule;
import daamky.client.FastItemUseModule;
import daamky.client.GlobalsMenuModule;
import daamky.client.InventoryBuilderModule;
import daamky.client.InventoryCleanerModule;
import daamky.client.ItemPickupModule;
import daamky.client.KtLeaveModule;
import daamky.client.NameProtectModule;
import daamky.client.PanicModule;
import daamky.client.RussianRouletteModule;
import daamky.client.SoundsModule;
import daamky.client.TestModule;
import daamky.client.WebUtilsModule;
import daamky.client.AutoEatModule;
import daamky.client.AutoFarmModule;
import daamky.client.AutoInvisibleModule;
import daamky.client.AutoLeaveModule;
import daamky.client.AutoShulkerModule;
import daamky.client.AutoSwapModule;
import daamky.client.BlinkModule;
import daamky.client.BootsSwapModule;
import daamky.client.ClanUpgradeModule;
import daamky.client.ClickThroughModule;
import daamky.client.ElytraUtilsModule;
import daamky.client.FreeCameraModule;
import daamky.client.GuiMoveModule;
import daamky.client.InventoryUtilsModule;
import daamky.client.MiddleClickModule;
import daamky.client.MineHelperModule;
import daamky.client.NoDelayModule;
import daamky.client.NoFallModule;
import daamky.client.NoInteractModule;
import daamky.client.NoPushModule;
import daamky.client.NoRotateModule;
import daamky.client.NukerModule;
import daamky.client.PlayerUtilsModule;
import daamky.client.ScaffoldModule;
import daamky.client.StealerModule;
import daamky.client.TargetPearlModule;
import daamky.client.TrackerModule;
import daamky.client.AmbienceModule;
import daamky.client.AntiInvisibleModule;
import daamky.client.BeautifullyModule;
import daamky.client.CustomFogModule;
import daamky.client.DonateEffectsModule;
import daamky.client.EspModule;
import daamky.client.InterfaceModule;
import daamky.client.KillEffectsModule;
import daamky.client.MenuModule;
import daamky.client.ObjectInfoModule;
import daamky.client.PredictionModule;
import daamky.client.RemovalsModule;
import daamky.client.SoundEspModule;
import daamky.client.StorageEspModule;
import daamky.client.SwingAnimationModule;
import daamky.client.TntTimerModule;
import daamky.client.TargetEspModule;
import daamky.client.ViewModelModule;
import daamky.client.WardenHelperModule;
import daamky.client.WaypointsModule;
import daamky.client.WorldModule;
import daamky.client.XRayModule;
import daamky.client.IiIIIiII_Class69;
import daamky.client.ModuleEntry;
import daamky.client.DaamkyClient;
import daamky.client.iIIIIIIII_Class257;
import daamky.client.iiIIiIiI_Class203;
import daamky.client.Module;
import daamky.client.AimAssistModule;
import daamky.client.AimBotModule;
import daamky.client.AntiBotModule;
import daamky.client.AuraModule;
import daamky.client.AutoAnchorModule;
import daamky.client.AutoArmorModule;
import daamky.client.AutoExplosionModule;
import daamky.client.AutoPotionModule;
import daamky.client.AutoSoupModule;
import daamky.client.AutoThrowModule;
import daamky.client.AutoTotemModule;
import daamky.client.BackTrackModule;
import daamky.client.CriticalsModule;
import daamky.client.ElytraTargetModule;
import daamky.client.HitboxesModule;
import daamky.client.KnockbackTweaksModule;
import daamky.client.TriggerBotModule;
import daamky.client.TrainerModule;
import daamky.client.VelocityModule;
import daamky.client.AirStuckModule;
import daamky.client.AutoSprintModule;
import daamky.client.ElytraStrafeModule;
import daamky.client.FlightModule;
import daamky.client.GrimGlideModule;
import daamky.client.HighJumpModule;
import daamky.client.NoSlowModule;
import daamky.client.SpeedModule;
import daamky.client.SpiderModule;
import daamky.client.StrafeModule;
import daamky.client.SuperFireworkModule;
import daamky.client.TimerModule;
import daamky.client.WaterSpeedModule;
import daamky.client.CrystalBlockerModule;
import daamky.client.WindHopModule;
import daamky.client.AdminskyModule;
import ua.mintantileak.spk.Compile;

public class ModuleManager {
    private final Map<Class<? extends ModuleEntry>, ModuleEntry> I_field_a567c40b = new IdentityHashMap<Class<? extends ModuleEntry>, ModuleEntry>();
    private final List<ModuleEntry> I_field_7865b31 = new ArrayList<ModuleEntry>();
    private static int I_field_49;
    private final IiIIIiII_Class69<ClientPlayerTickEvent> I_field_3d936f41;
    private final IiIIIiII_Class69<HudRenderEvent> i_field_3d936f41;
    private final IiIIIiII_Class69<KeyPressEvent> II_field_3d936f41 = keyPressEvent -> {
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        if (keyPressEvent.getAction() != 1) {
            return;
        }
        int n = iIIIIIIII_Class257.I_method_e761de12();
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isAvailable() || !iIIIIIIII_Class257.I_method_37b59828(iiIiiIii_Class92.getKeybind(), keyPressEvent.getKey(), n)) continue;
            iiIiiIii_Class92.toggle();
        }
    };
    private final IiIIIiII_Class69<MouseEvent> Ii_field_3d936f41 = mouseEvent -> {
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        if (mouseEvent.getAction() != 1) {
            return;
        }
        int n = iIIIIIIII_Class257.I_method_e761de12();
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isAvailable() || !iIIIIIIII_Class257.I_method_37b59828(iiIiiIii_Class92.getKeybind(), mouseEvent.getButton(), n)) continue;
            iiIiiIii_Class92.toggle();
        }
    };
    private final IiIIIiII_Class69<GameTickEvent> iI_field_3d936f41 = gameTickEvent -> {
        if (!Module.ii_method_af18f3f9()) {
            return;
        }
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isEnabled() || iiIiiIii_Class92.isAvailable()) continue;
            iiIiiIii_Class92.setEnabled(false, true);
        }
    };

    public static void I_method_7056a46c() {
        ++I_field_49;
    }

    public ModuleManager(IiIIIiII_Class69<ClientPlayerTickEvent> iiIIIiII_Class69, IiIIIiII_Class69<HudRenderEvent> iiIIIiII_Class692) {
        this.I_field_3d936f41 = iiIIIiII_Class69;
        this.i_field_3d936f41 = iiIIIiII_Class692;
        DaamkyClient.getInstance().I_method_7897deab().I_method_2257cd48(this);
    }

    @Compile(obfuscation=4)
    public final void registerModules() {
        this.registerModule(new AuraModule());
        this.registerModule(new AimAssistModule());
        this.registerModule(new AutoTotemModule());
        this.registerModule(new TriggerBotModule());
        this.registerModule(new AimBotModule());
        this.registerModule(new AutoPotionModule());
        this.registerModule(new AutoThrowModule());
        this.registerModule(new AntiBotModule());
        this.registerModule(new VelocityModule());
        this.registerModule(new KnockbackTweaksModule());
        this.registerModule(new AutoArmorModule());
        this.registerModule(new AutoExplosionModule());
        this.registerModule(new AutoAnchorModule());
        this.registerModule(new BackTrackModule());
        this.registerModule(new HitboxesModule());
        this.registerModule(new ElytraTargetModule());
        this.registerModule(new CriticalsModule());
        this.registerModule(new AutoSoupModule());
        this.registerModule(new AutoSprintModule());
        this.registerModule(new SuperFireworkModule());
        this.registerModule(new WebUtilsModule());
        this.registerModule(new StrafeModule());
        this.registerModule(new FlightModule());
        this.registerModule(new GrimGlideModule());
        this.registerModule(new SpeedModule());
        this.registerModule(new TimerModule());
        this.registerModule(new NoSlowModule());
        this.registerModule(new HighJumpModule());
        this.registerModule(new WaterSpeedModule());
        this.registerModule(new AirStuckModule());
        this.registerModule(new SpiderModule());
        this.registerModule(new ElytraStrafeModule());
        this.registerModule(new MenuModule());
        this.registerModule(new EspModule());
        this.registerModule(new WaypointsModule());
        this.registerModule(new RemovalsModule());
        this.registerModule(new AmbienceModule());
        this.registerModule(new SwingAnimationModule());
        this.registerModule(new SoundEspModule());
        this.registerModule(new TntTimerModule());
        this.registerModule(new WardenHelperModule());
        this.registerModule(new BeautifullyModule());
        this.registerModule(new ViewModelModule());
        this.registerModule(new BlinkModule());
        this.registerModule(new InterfaceModule());
        this.registerModule(new F5NickModule());
        this.registerModule(new TargetEspModule());
        this.registerModule(new StorageEspModule());
        this.registerModule(new XRayModule());
        this.registerModule(new AntiInvisibleModule());
        this.registerModule(new CustomFogModule());
        this.registerModule(new WorldModule());
        this.registerModule(new KillEffectsModule());
        this.registerModule(new PredictionModule());
        this.registerModule(new DonateEffectsModule());
        this.registerModule(new InventoryCleanerModule());
        this.registerModule(new AutoFarmModule());
        this.registerModule(new AutoInvisibleModule());
        this.registerModule(new ClickThroughModule());
        this.registerModule(new MineHelperModule());
        this.registerModule(new TargetPearlModule());
        this.registerModule(new StealerModule());
        this.registerModule(new MiddleClickModule());
        this.registerModule(new TrackerModule());
        this.registerModule(new InventoryUtilsModule());
        this.registerModule(new AutoEatModule());
        this.registerModule(new ClanUpgradeModule());
        this.registerModule(new FreeCameraModule());
        this.registerModule(new NoDelayModule());
        this.registerModule(new PlayerUtilsModule());
        this.registerModule(new NoPushModule());
        this.registerModule(new BootsSwapModule());
        this.registerModule(new ItemPickupModule());
        this.registerModule(new AutoShulkerModule());
        this.registerModule(new ScaffoldModule());
        this.registerModule(new ObjectInfoModule());
        this.registerModule(new NukerModule());
        this.registerModule(new NoRotateModule());
        this.registerModule(new NoInteractModule());
        this.registerModule(new NoFallModule());
        this.registerModule(new EffectRemoverModule());
        this.registerModule(new NameProtectModule());
        this.registerModule(new GlobalsMenuModule());
        this.registerModule(new ElytraUtilsModule());
        this.registerModule(new FastItemUseModule());
        this.registerModule(new AutoResellModule());
        this.registerModule(new BaseFinderModule()); 
        this.registerModule(new PanicModule());
        this.registerModule(new AuctionModule());
        this.registerModule(new InventoryBuilderModule());
        this.registerModule(new AutoAcceptModule());
        this.registerModule(new DeathCordsModule());
        this.registerModule(new AutoLeaveModule());
        this.registerModule(new KtLeaveModule());
        this.registerModule(new AutoSwapModule());
        this.registerModule(new RussianRouletteModule());
        this.registerModule(new AutoDuelsModule());
        this.registerModule(new AutoAuthModule());
        this.registerModule(new AutoJoinModule());
        this.registerModule(new GuiMoveModule());
        this.registerModule(new TestModule());
        this.registerModule(new AdminskyModule());
        this.registerModule(new AutoBuyModule());
        this.registerModule(new AssistModule());
        this.registerModule(new SoundsModule());
        this.registerModule(new AntiAimModule());
        this.registerModule(new CrystalBlockerModule());
        this.registerModule(new WindHopModule());
        this.snapshotModuleSettings();
    }

    @Compile(obfuscation=1)
    public final void enableDefaultModules() {
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isEnabledByDefault()) continue;
            iiIiiIii_Class92.enable();
        }
    }

    public final void registerModule(Module Module2) {
        this.I_field_a567c40b.put(Module2.getClass(), Module2);
        this.I_field_7865b31.add(Module2);
    }

    public final <T extends ModuleEntry> T getModuleByName(String string) {
        return (T)this.I_field_7865b31.stream().filter(iiIiiIii_Class92 -> iiIiiIii_Class92.getName().replace(" ", "").equalsIgnoreCase(string) || iiIiiIii_Class92.getName().equalsIgnoreCase(string)).findFirst().orElseThrow(() -> new iiIIiIiI_Class203(string));
    }

    public final <T extends ModuleEntry> T getModule(Class<T> clazz) {
        return (T)((ModuleEntry)clazz.cast(this.I_field_a567c40b.get(clazz)));
    }

    public final void snapshotModuleSettings() {
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!(iiIiiIii_Class92 instanceof Module)) continue;
            Module Module2 = (Module)iiIiiIii_Class92;
            Module2.III_method_fbb67e0c();
        }
    }

    @Generated
    public List<ModuleEntry> getModules() {
        return this.I_field_7865b31;
    }

    @Generated
    public static int I_method_7056a45f() {
        return I_field_49;
    }

    @Generated
    public IiIIIiII_Class69<ClientPlayerTickEvent> I_method_1bea6feb() {
        return this.I_field_3d936f41;
    }

    @Generated
    public IiIIIiII_Class69<HudRenderEvent> i_method_45e7000b() {
        return this.i_field_3d936f41;
    }
}

