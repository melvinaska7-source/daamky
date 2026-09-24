package moscow.daamky.mixin.accessors;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={RenderLayer.MultiPhaseParameters.class})
public interface MultiPhaseParametersAccessor {
    @Accessor(value="texture")
    public RenderPhase.TextureBase daamky$getTexture();

    @Accessor(value="transparency")
    public RenderPhase.Transparency daamky$getTransparency();
}

