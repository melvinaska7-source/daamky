package moscow.daamky.mixin.minecraft.render.entity;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import daamky.client.iIiIiiiII_Class349;

@Mixin(value={EntityRenderState.class})
public abstract class EntityRenderStateMixin
implements iIiIiiiII_Class349 {
    @Unique
    private Entity daamky$entity;

    @Override
    @Unique
    public void daamky$setEntity(Entity entity) {
        this.daamky$entity = entity;
    }

    @Override
    @Unique
    public Entity daamky$getEntity() {
        return this.daamky$entity;
    }
}

