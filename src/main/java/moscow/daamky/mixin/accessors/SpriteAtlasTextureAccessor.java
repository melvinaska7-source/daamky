package moscow.daamky.mixin.accessors;

import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SpriteAtlasTexture.class})
public interface SpriteAtlasTextureAccessor {
    @Accessor(value="width")
    public int daamky$getWidth();

    @Accessor(value="height")
    public int daamky$getHeight();
}

