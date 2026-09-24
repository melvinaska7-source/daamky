package moscow.daamky.mixin.minecraft.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pydaamky.events.game.BreakTotemEvent;
import pydaamky.events.game.EntityDeathEvent;
import pydaamky.events.game.EntityJumpEvent;
import pydaamky.events.player.EventOnTravelPost;
import daamky.client.NoDelayModule;
import daamky.client.NoPushModule;
import daamky.client.SwingAnimationModule;
import daamky.client.DaamkyClient;
import daamky.client.IiiiiiIii_Class252;
import daamky.client.iiIIiIiII_Class405;
import daamky.client.iiIIiiIIi_Class410;
import daamky.client.ElytraTargetModule;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
   @Shadow
   private int field_6228;

   @Shadow
   public abstract void method_5650(RemovalReason var1);

   @Shadow
   public abstract ItemStack method_6047();

   @ModifyReturnValue(
      method = {"getHandSwingDuration"},
      at = {@At("RETURN")}
   )
   public int replaceSwingSpeed(int var1) {
      SwingAnimationModule var2 = DaamkyClient.getInstance().getModuleManager().getModule(SwingAnimationModule.class);
      return var2.isEnabled() && var2.I_method_5f90eae5(this.method_6047())
         ? (int)(var1 * DaamkyClient.getInstance().I_method_11732eb().I_method_78c80362().Ii_method_a20abcd2())
         : var1;
   }

   @Inject(
      method = {"jump"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void triggerJumpEvent(CallbackInfo var1) {
      LivingEntity var2 = (LivingEntity)(Object)this;
      EntityJumpEvent var3 = new EntityJumpEvent(var2);
      DaamkyClient.getInstance().I_method_7897deab().I_method_e7f802ad(var3);
      if (var3.isCancelled()) {
         var1.cancel();
      }
   }

   @ModifyExpressionValue(
      method = {"jump"},
      at = {@At(
         value = "NEW",
         target = "(DDD)Lnet/minecraft/util/math/Vec3d;"
      )}
   )
   public Vec3d movementCorrection(Vec3d var1) {
      iiIIiIiII_Class405 var2 = DaamkyClient.I_field_502d1741.I_method_58785402();
      iiIIiiIIi_Class410 var3 = var2.I_method_fcdff2d5();
      if ((Object)this != MinecraftClient.getInstance().player) {
         return var1;
      } else if (var3 != null && var3.I_method_384472d5().i_method_779a71e3()) {
         float var4 = var2.II_method_f098f858().I_method_14534e0f() * (float) (Math.PI / 180.0);
         return new Vec3d(-MathHelper.sin(var4) * 0.2F, 0.0, MathHelper.cos(var4) * 0.2F);
      } else {
         return var1;
      }
   }

   @Inject(
      method = {"tickMovement"},
      at = {@At("HEAD")}
   )
   public void removeJumpDelay(CallbackInfo var1) {
      NoDelayModule var2 = DaamkyClient.getInstance().getModuleManager().getModule(NoDelayModule.class);
      if (var2.isEnabled() && var2.I_method_46ab5f15().i_method_9b12da03()) {
         this.field_6228 = 0;
      }
   }

   @Inject(
      method = {"isPushable"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void removePushFromEntity(CallbackInfoReturnable<Boolean> var1) {
      NoPushModule var2 = DaamkyClient.getInstance().getModuleManager().getModule(NoPushModule.class);
      LivingEntity var3 = (LivingEntity)(Object)this;
      if (var3 instanceof ClientPlayerEntity && var2.isEnabled() && var2.I_method_652784b0().isSelected()) {
         var1.setReturnValue(false);
      }
   }

   @Inject(
      method = {"onDeath"},
      at = {@At("TAIL")}
   )
   public void triggerEntityDeathEvent(DamageSource var1, CallbackInfo var2) {
      LivingEntity var3 = (LivingEntity)(Object)this;
      if (IiiiiiIii_Class252.i_method_e51d8c2b(var3)) {
         DaamkyClient.getInstance().I_method_7897deab().I_method_e7f802ad(new EntityDeathEvent(var3, var1));
      }
   }

   @Redirect(
      method = {"calcGlidingVelocity(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getPitch()F"
      )
   )
   private float redirectGetPitch(LivingEntity var1) {
      return daamky$shouldCorrectMovement(var1)
         ? DaamkyClient.getInstance().I_method_58785402().II_method_f098f858().i_method_1461d9ef()
         : var1.getPitch();
   }

   @Redirect(
      method = {"calcGlidingVelocity(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"
      )
   )
   private Vec3d redirectGetRotationVector(LivingEntity var1) {
      return daamky$shouldCorrectMovement(var1)
         ? DaamkyClient.getInstance().I_method_58785402().II_method_f098f858().I_method_cbcdd559()
         : var1.getRotationVector();
   }

   @Unique
   private static boolean daamky$shouldCorrectMovement(LivingEntity var0) {
      if (var0 != MinecraftClient.getInstance().player) {
         return false;
      } else {
         iiIIiIiII_Class405 var1 = DaamkyClient.getInstance().I_method_58785402();
         iiIIiiIIi_Class410 var2 = var1.I_method_fcdff2d5();
         return !var1.I_method_3d166e03() && var2 != null && var2.I_method_384472d5().I_method_778be603();
      }
   }

   @Inject(
      method = {"calcGlidingVelocity(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void daamky$onCalcGlidingVelocity(Vec3d var1, CallbackInfoReturnable<Vec3d> var2) {
      LivingEntity var3 = (LivingEntity)(Object)this;
      if (var3 == MinecraftClient.getInstance().player) {
         EventOnTravelPost var4 = new EventOnTravelPost((Vec3d)var2.getReturnValue());
         DaamkyClient.getInstance().I_method_7897deab().I_method_e7f802ad(var4);
         var2.setReturnValue(var4.getOldVelocity());
      }
   }

   @Inject(
      method = {"tryUseDeathProtector"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/item/ItemStack;decrement(I)V"
      )}
   )
   private void onTotemUse(DamageSource var1, CallbackInfoReturnable<Boolean> var2) {
      LivingEntity var3 = (LivingEntity)(Object)this;
      if (var3 instanceof PlayerEntity) {
         for (Hand var7 : Hand.values()) {
            ItemStack var8 = var3.getStackInHand(var7);
            DaamkyClient.getInstance().I_method_7897deab().I_method_e7f802ad(new BreakTotemEvent(var3, var8));
            if (var8.contains(DataComponentTypes.DEATH_PROTECTION)) {
               return;
            }
         }
      }
   }

   @Inject(
      method = {"travel"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelElytraMovement(Vec3d var1, CallbackInfo var2) {
      LivingEntity var3 = (LivingEntity)(Object)this;
      if (var3 == MinecraftClient.getInstance().player) {
         ElytraTargetModule var4 = DaamkyClient.getInstance().getModuleManager().getModule(ElytraTargetModule.class);
         boolean var5 = var4.isEnabled()
            && var4.II_method_89c5594b().i_method_9b12da03()
            && var4.I_method_7953ea99() != null
            && var3.isGliding()
            && var3.distanceTo(var4.I_method_7953ea99()) <= var4.iI_method_dfd525ab().Ii_method_a20abcd2();
         if (var5) {
            var3.setVelocity(Vec3d.ZERO);
            var2.cancel();
         }
      }
   }
}
