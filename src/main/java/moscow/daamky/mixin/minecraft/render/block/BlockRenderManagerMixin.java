package moscow.daamky.mixin.minecraft.render.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import daamky.client.ViewModelModule;
import daamky.client.IiiiIiii_Class120;
import daamky.client.IiiiiIii_Class124;
import daamky.client.IiiiiiiI_Class127;
import daamky.client.Iiiiiiii_Class128;
import daamky.client.iIIIIIII_Class129;
import daamky.client.iIIIIIiI_Class131;

@Mixin({BlockRenderManager.class})
public abstract class BlockRenderManagerMixin {
   @Unique
   private static boolean daamky$capturingHeldBlock;

   @Inject(
      method = {"renderBlockAsEntity"},
      at = {@At("HEAD")}
   )
   private void daamky$decorateHeldBlock(BlockState var1, MatrixStack var2, VertexConsumerProvider var3, int var4, int var5, CallbackInfo var6) {
      if (!daamky$capturingHeldBlock && IiiiiIii_Class124.i_field_5a && !IiiiiIii_Class124.I_field_5a && !ViewModelModule.I_field_5a) {
         if (var1 != null && daamky$isHeld(var1.getBlock())) {
            IiiiiIii_Class124.I_method_65dc8d14(var1, var5, var2);
            iIIIIIiI_Class131 var7 = IiiiiiiI_Class127.I_method_e1e653cb().I_method_b8f96026(iIIIIIiI_Class131.class);
            iIIIIIII_Class129 var8 = IiiiiiiI_Class127.I_method_e1e653cb().I_method_b8f96026(iIIIIIII_Class129.class);
            Iiiiiiii_Class128 var9 = IiiiiiiI_Class127.I_method_e1e653cb().I_method_b8f96026(Iiiiiiii_Class128.class);
            boolean var10 = var7 != null && var7.i_method_6c6ff6d1(IiiiIiii_Class120.I_field_10f66741);
            boolean var11 = var8 != null && var8.i_method_6c6ff6d1(IiiiIiii_Class120.I_field_10f66741);
            boolean var12 = var9 != null && var9.i_method_6c6ff6d1(IiiiIiii_Class120.I_field_10f66741);
            if (var10 || var11 || var12) {
               if (var3 instanceof Immediate var13) {
                  var13.draw();
               }

               BlockRenderManager var17 = (BlockRenderManager)(Object)this;
               daamky$capturingHeldBlock = true;

               try {
                  if (var10) {
                     var7.I_method_c76b816(var17, var1, var2, var5);
                  }

                  if (var11) {
                     var8.I_method_1669436(var17, var1, var2, var5);
                  }

                  if (var12) {
                     var9.I_method_e9752c76(var17, var1, var2, var5);
                  }
               } finally {
                  daamky$capturingHeldBlock = false;
               }
            }
         }
      }
   }

   @Unique
   private static boolean daamky$isHeld(Block var0) {
      ClientPlayerEntity var1 = MinecraftClient.getInstance().player;
      return var1 == null
         ? false
         : Block.getBlockFromItem(var1.getMainHandStack().getItem()) == var0 || Block.getBlockFromItem(var1.getOffHandStack().getItem()) == var0;
   }
}
