package moscow.daamky.mixin.minecraft.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import daamky.client.WardenHelperModule;
import daamky.client.DaamkyClient;
import daamky.client.F5NickModule;
import daamky.client.IiiiiiiI_Class127;
import daamky.client.iIIIIiII_Class133;

@Mixin({EntityRenderer.class})
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

    @Inject(
        method = {"getDisplayName"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private void onRenderLabel(T var1, CallbackInfoReturnable<Text> var2) {
        if (var1 instanceof ArmorStandEntity var3) {
            WardenHelperModule var4 = DaamkyClient.getInstance().getModuleManager().getModule(WardenHelperModule.class);
            if (var4 != null && var4.I_method_fa108cf1(var3)) {
                var2.setReturnValue(null);
                return;
            }
        }

        if (var1 instanceof PlayerEntity var5) {
            iIIIIiII_Class133 var6 = IiiiiiiI_Class127.I_method_e1e653cb().I_method_b8f96026(iIIIIiII_Class133.class);
            if (var6 != null) {
                if (var6.I_method_bfc7acbe(var5)) {
                    var2.setReturnValue(null);
                }
            }
        }
    }

    // ===== F5Nick =====
    @Inject(
        method = {"hasLabel"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private void daamky$forceF5Nick(T entity, double squaredDistanceToCamera, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof PlayerEntity)) return;

        F5NickModule module = DaamkyClient.getInstance().getModuleManager().getModule(F5NickModule.class);
        if (module == null || !module.isEnabled()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (entity == client.player && !client.options.getPerspective().isFirstPerson()) {
            cir.setReturnValue(true);
        }
    }
}