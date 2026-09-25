package daamky.client;

import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import pydaamky.events.game.EntityDeathEvent;
import pydaamky.events.network.ReceivePacketEvent;
import ua.mintantileak.spk.Compile;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(
   name = "Sounds",
   category = ModuleCategory.OTHER,
   iI_method_476ab839 = true,
   III_method_a89e5834 = "modules.descriptions.sounds"
)
public class SoundsModule extends Module {
   private SliderSetting I_field_73178e8c;
   private MultiSelectSetting I_field_bbe3ba6c;
   private MultiSelectSetting.Nested1_42856060 I_field_51de8227;
   private MultiSelectSetting.Nested1_42856060 i_field_51de8227;
   private MultiSelectSetting.Nested1_42856060 II_field_51de8227;
   private MultiSelectSetting.Nested1_42856060 Ii_field_51de8227;
   private MultiSelectSetting.Nested1_42856060 iI_field_51de8227;
   private LivingEntity I_field_966247a3;
   private boolean I_field_welcomePlayed;

   private final IiIIIiII_Class69<ReceivePacketEvent> I_field_3d936f41 = var1 -> {
      if (var1.getPacket() instanceof GameMessageS2CPacket var2) {
         String var5 = var2.content().getString();
         if (var5.contains("Вы успешно купили") || var5.contains("отправлено игроку")) {
            play("notification", 1.0F);
         }
      }

      if (var1.getPacket() instanceof EntityStatusS2CPacket var4 && I_field_3a9bda27.world != null && I_field_3a9bda27.player != null) {
         Entity var7 = var4.getEntity(I_field_3a9bda27.world);
         if (var7 instanceof LivingEntity && var4.getStatus() == 35
               && (var7 == I_field_3a9bda27.player || I_field_3a9bda27.player.distanceTo(var7) < 6.0F)
               && this.i_field_51de8227.isSelected()) {
            play("forestmorn/totem", 1.0F);
         }
      }
   };

   private final IiIIIiII_Class69<EntityDeathEvent> i_field_3d936f41 = var1 -> {
      LivingEntity dead = var1.getEntity();
      if (dead == null || I_field_3a9bda27.player == null) {
         return;
      }

      // The local player died: player-killed death gets sinshluhi, everything else gets nespr.
      if (dead == I_field_3a9bda27.player) {
         if (this.iI_field_51de8227.isSelected()) {
            play(var1.getKillerEntity() instanceof PlayerEntity ? "forestmorn/sinshluhi" : "forestmorn/nespr", 1.0F);
         }
         this.I_field_966247a3 = null;
         return;
      }

      // A nearby living entity died: this is a kill made by the local player.
      if (this.I_field_966247a3 == dead && this.I_field_51de8227.isSelected()) {
         playRandomKillSound();
         this.I_field_966247a3 = null;
      }
   };

   public SoundsModule() {
      this.IiI_method_6bdf3e7f();
   }

   @Compile(obfuscation = 4)
   private void IiI_method_6bdf3e7f() {
      this.I_field_73178e8c = new SliderSetting(this, "modules.settings.sounds.volume")
         .II_method_b0f56334(5.0F)
         .I_method_c8c9a7d7(10.0F)
         .i_method_65e2aff7(100.0F)
         .Ii_method_4e0e6b54(80.0F)
         .I_method_d41e7abf("%");
      this.I_field_bbe3ba6c = new MultiSelectSetting(this, "modules.settings.sounds.voice");
      this.I_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.sounds.voice.kill");
      this.i_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.sounds.voice.totem");
      this.II_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.sounds.voice.start");
      this.Ii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.sounds.voice.leave");
      this.iI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.sounds.voice.death");
   }

   @Override
   public void II_method_6642fd22() {
      if (I_field_3a9bda27.player == null) {
         this.I_field_966247a3 = null;
         return;
      }

      // Play welcome once after the client/player are ready.
      if (!this.I_field_welcomePlayed && this.II_field_51de8227.isSelected()) {
         play("forestmorn/welcome", 1.0F);
         this.I_field_welcomePlayed = true;
      }

      if (I_field_3a9bda27.player.isDead()) {
         this.I_field_966247a3 = null;
         return;
      }

      if (this.I_field_966247a3 != null) {
         if (I_field_3a9bda27.player.distanceTo(this.I_field_966247a3) > 6.0F) {
            this.I_field_966247a3 = null;
            return;
         }

         if (!this.I_field_966247a3.isAlive() || !I_field_3a9bda27.world.hasEntity(this.I_field_966247a3)) {
            this.I_field_966247a3 = null;
         }
      }

      LivingEntity target = DaamkyClient.getInstance().I_method_9a638462().I_method_9c10f815() instanceof LivingEntity living ? living : null;
      if (target != null && target != I_field_3a9bda27.player) {
         this.I_field_966247a3 = target;
      }
   }

   @Override
   public void onDisable() {
      this.I_field_966247a3 = null;
      this.I_field_welcomePlayed = false;
   }

   private void playRandomKillSound() {
      int sound = ThreadLocalRandom.current().nextInt(3);
      play(sound == 0 ? "forestmorn/ezz" : sound == 1 ? "forestmorn/tuda" : "forestmorn/daamkyboost", 1.0F);
   }

   private void play(String sound, float pitch) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client == null || client.getSoundManager() == null) {
         return;
      }
      client.getSoundManager().play(new PositionedSoundInstance(
         Identifier.of("daamky", sound),
         SoundCategory.MASTER,
         this.I_method_45ee5ef(),
         pitch,
         SoundInstance.createRandom(),
         false,
         0,
         SoundInstance.AttenuationType.NONE,
         0.0,
         0.0,
         0.0,
         true
      ));
   }

   // Kept with the original names because the rest of the client calls these methods.
   public float I_method_45ee5ef() {
      return this.I_field_73178e8c.Ii_method_a20abcd2() / 100.0F;
   }

   @Generated
   public MultiSelectSetting I_method_41426ed5() { return this.I_field_bbe3ba6c; }
   @Generated
   public MultiSelectSetting.Nested1_42856060 I_method_fa61c050() { return this.I_field_51de8227; }
   @Generated
   public MultiSelectSetting.Nested1_42856060 i_method_69bc30() { return this.i_field_51de8227; }
   @Generated
   public MultiSelectSetting.Nested1_42856060 II_method_4eb9f913() { return this.II_field_51de8227; }
   @Generated
   public MultiSelectSetting.Nested1_42856060 Ii_method_54c1f4f3() { return this.Ii_field_51de8227; }
   @Generated
   public MultiSelectSetting.Nested1_42856060 iI_method_9b17933() { return this.iI_field_51de8227; }
   @Generated
   public LivingEntity I_method_cbde830c() { return this.I_field_966247a3; }
   @Generated
   public IiIIIiII_Class69<ReceivePacketEvent> I_method_dec809b8() { return this.I_field_3d936f41; }
   @Generated
   public IiIIIiII_Class69<EntityDeathEvent> i_method_8c499d8() { return this.i_field_3d936f41; }
}
