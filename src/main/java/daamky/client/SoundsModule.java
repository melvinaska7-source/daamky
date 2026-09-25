package daamky.client;

import java.util.Random;
import lombok.Generated;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import pydaamky.events.game.BreakTotemEvent;
import pydaamky.events.game.EntityDeathEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
   name = "Sounds",
   category = ModuleCategory.OTHER,
   iI_method_476ab839 = true,
   III_method_a89e5834 = "modules.descriptions.sounds"
)
public class SoundsModule extends Module {
   private final Random random = new Random();

   private SliderSetting volume;

   private LivingEntity trackedTarget;
   private boolean welcomePlayed;

   private final IiIIIiII_Class69<EntityDeathEvent> deathEvent = event -> {
      if (I_field_3a9bda27.player == null) {
         return;
      }

      LivingEntity entity = event.getEntity();

      // We killed a mob/player: play exactly one of the three kill sounds.
      if (entity != I_field_3a9bda27.player) {
         LivingEntity killer = event.getKillerEntity();
         if (killer == I_field_3a9bda27.player || entity == this.trackedTarget) {
            this.playKillSound();
            this.trackedTarget = null;
         }
         return;
      }

      // We died: player death -> sinshluhi, everything else -> nespr.
      LivingEntity killer = event.getKillerEntity();
      if (killer instanceof PlayerEntity) {
         this.play("sounds/forestmorn/sinshluhi");
      } else {
         this.play("sounds/forestmorn/nespr");
      }

      this.trackedTarget = null;
   };

   private final IiIIIiII_Class69<BreakTotemEvent> totemEvent = event -> {
      if (I_field_3a9bda27.player != null) {
         this.play("sounds/forestmorn/totem");
      }
   };

   public SoundsModule() {
      this.setupSettings();
   }

   @Compile(obfuscation = 4)
   private void setupSettings() {
      this.volume = new SliderSetting(this, "modules.settings.sounds.volume")
         .II_method_b0f56334(5.0F)
         .I_method_c8c9a7d7(10.0F)
         .i_method_65e2aff7(100.0F)
         .Ii_method_4e0e6b54(80.0F)
         .I_method_d41e7abf("%");
   }

   @Override
   public void II_method_6642fd22() {
      if (I_field_3a9bda27.player == null) {
         this.trackedTarget = null;
         return;
      }

      // Play the startup sound once, after the client/player is ready.
      if (!this.welcomePlayed) {
         this.play("sounds/forestmorn/welcome");
         this.welcomePlayed = true;
      }

      LivingEntity target = DaamkyClient.getInstance()
         .I_method_9a638462()
         .I_method_9c10f815() instanceof LivingEntity living ? living : null;

      if (target != null && target != I_field_3a9bda27.player) {
         this.trackedTarget = target;
      } else if (this.trackedTarget != null) {
         if (this.trackedTarget.isAlive()
            && I_field_3a9bda27.world != null
            && I_field_3a9bda27.world.hasEntity(this.trackedTarget)) {
            // Keep the target while it is alive.
         } else {
            this.trackedTarget = null;
         }
      }
   }

   private void playKillSound() {
      switch (this.random.nextInt(3)) {
         case 0 -> this.play("sounds/forestmorn/ezz");
         case 1 -> this.play("sounds/forestmorn/tuda");
         default -> this.play("sounds/forestmorn/daamkyboost");
      }
   }

   private void play(String sound) {
      iiIIiiiii_Class416 instance = new iiIIiiiii_Class416(sound, this.getVolume());
      instance.I_method_87d2e181(this.getVolume());
   }

   public float getVolume() {
      return this.volume.Ii_method_a20abcd2() / 100.0F;
   }

   @Generated
   public SliderSetting getVolumeSetting() {
      return this.volume;
   }

   @Generated
   public LivingEntity getTrackedTarget() {
      return this.trackedTarget;
   }
}
