package moscow.daamky.mixin.minecraft.client.gui.overlay;

import moscow.daamky.mixin.accessors.CameraAccessor;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pydaamky.events.render.CameraUpdateEvent;
import daamky.client.FreeCameraModule;
import daamky.client.RemovalsModule;
import daamky.client.DaamkyClient;
import daamky.client.iIIIiiiII_Class285;
import daamky.client.iiIIiIIii_Class404;
import daamky.client.AuraModule;
import daamky.client.SpeedModule;

@Mixin(value={Camera.class})
public abstract class CameraMixin {
    @Shadow
    private Entity field_18711;
    @Shadow
    private boolean field_18719;
    @Shadow
    private float field_18721;
    @Shadow
    private float field_18722;
    @Unique
    private static final int daamky$JITTER_FLIPS = 3;
    @Unique
    private static final int daamky$RESET_TICKS = 10;
    @Unique
    private float daamky$lastEyeHeight;
    @Unique
    private boolean daamky$eyeHeightKnown;
    @Unique
    private float daamky$heldEyeHeight;
    @Unique
    private int daamky$eyeHeightFlips;
    @Unique
    private int daamky$ticksSinceFlip;

    @Shadow
    public abstract void method_19322(Vec3d var1);

    @Shadow
    public abstract void method_19325(float var1, float var2);

    @Inject(method={"updateEyeHeight"}, at={@At(value="HEAD")}, cancellable=true)
    private void daamky$stabilizeEyeHeight(CallbackInfo callbackInfo) {
        if (this.field_18711 == null) {
            return;
        }
        float f = CameraMixin.daamky$eyeHeight(this.field_18711);
        if (!this.field_18711.isTouchingWater() || !CameraMixin.daamky$auraOnHolyWorld()) {
            this.daamky$eyeHeightKnown = false;
            this.daamky$eyeHeightFlips = 0;
            this.daamky$ticksSinceFlip = 0;
            this.daamky$heldEyeHeight = f;
            this.field_18722 = this.field_18721;
            this.field_18721 += (f - this.field_18721) * 0.5f;
            callbackInfo.cancel();
            return;
        }
        if (!this.daamky$eyeHeightKnown) {
            this.daamky$eyeHeightKnown = true;
            this.daamky$lastEyeHeight = f;
            this.daamky$heldEyeHeight = f;
        }
        if (Math.abs(f - this.daamky$lastEyeHeight) > 1.0E-4f) {
            this.daamky$lastEyeHeight = f;
            this.daamky$ticksSinceFlip = 0;
            ++this.daamky$eyeHeightFlips;
        } else if (++this.daamky$ticksSinceFlip >= 10) {
            this.daamky$eyeHeightFlips = 0;
        }
        this.daamky$heldEyeHeight = this.daamky$eyeHeightFlips >= 3 ? Math.max(this.daamky$heldEyeHeight, f) : f;
        this.field_18722 = this.field_18721;
        this.field_18721 += (this.daamky$heldEyeHeight - this.field_18721) * 0.5f;
        callbackInfo.cancel();
    }

    @Unique
    private static boolean daamky$auraOnHolyWorld() {
        AuraModule iiIiIIDaamkyClient102 = DaamkyClient.getInstance().getModuleManager().getModule(AuraModule.class);
        return iiIiIIDaamkyClient102 != null && iiIiIIDaamkyClient102.isEnabled() && iIIIiiiII_Class285.Ii_method_b349c526();
    }

    @Unique
    private static float daamky$eyeHeight(Entity entity) {
        if (entity == MinecraftClient.getInstance().player && entity.getPose() == EntityPose.GLIDING && SpeedModule.Iii_method_2c01c5d0()) {
            return entity.getDimensions(EntityPose.STANDING).eyeHeight();
        }
        return entity.getStandingEyeHeight();
    }

    @Inject(method={"getSubmersionType"}, at={@At(value="HEAD")}, cancellable=true)
    private void getSubmergedFluidState(CallbackInfoReturnable<CameraSubmersionType> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = DaamkyClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83 == null || !iIiIiIIiI_Class83.isEnabled()) {
            return;
        }
        if (iIiIiIIiI_Class83.IiI_method_7acc5cf0().isSelected()) {
            callbackInfoReturnable.setReturnValue(CameraSubmersionType.NONE);
            return;
        }
        if (this.field_18719 && iIiIiIIiI_Class83.ii_method_53ac6cf3().isSelected() && !this.daamky$focusedEntitySubmerged()) {
            callbackInfoReturnable.setReturnValue(CameraSubmersionType.NONE);
        }
    }

    @Unique
    private boolean daamky$focusedEntitySubmerged() {
        return this.field_18711 != null && (this.field_18711.isSubmergedInWater() || this.field_18711.isInLava() || this.field_18711.inPowderSnow);
    }

    @Inject(method={"clipToSpace"}, at={@At(value="HEAD")}, cancellable=true)
    private void onClipToSpace(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = DaamkyClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.ii_method_53ac6cf3().isSelected() && iIiIiIIiI_Class83.isEnabled()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(f));
        }
    }

    @Inject(method={"update"}, at={@At(value="TAIL")})
    private void onUpdate(BlockView blockView, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        FreeCameraModule iIIiIIiii_Class40 = DaamkyClient.getInstance().getModuleManager().getModule(FreeCameraModule.class);
        iIIiIIiii_Class40.IiI_method_460b62bf();
        if (iIIiIIiii_Class40.IiI_method_460b62c3()) {
            this.method_19322(iIIiIIiii_Class40.I_method_c6647d1b(f));
            iiIIiIIii_Class404 iiIIiIIii_Class4042 = iIIiIIiii_Class40.I_method_4e394bb7(f);
            this.method_19325(iiIIiIIii_Class4042.I_method_14534e0f(), iiIIiIIii_Class4042.i_method_1461d9ef());
            ((CameraAccessor)((Object)this)).setThirdPerson(true);
            return;
        }
        DaamkyClient.getInstance().I_method_7897deab().I_method_e7f802ad(new CameraUpdateEvent((Camera)(Object)this, entity, bl, bl2, f));
    }
}

