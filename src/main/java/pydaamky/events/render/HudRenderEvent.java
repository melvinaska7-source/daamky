package pydaamky.events.render;

import lombok.Generated;
import pydaamky.utility.render.CustomDrawContext;
import daamky.client.IiIIIIIi_Class66;
import daamky.client.IiIIIIiI_Class67;

@IiIIIIiI_Class67(I_method_80b3cd54="render_2d")
public class HudRenderEvent
extends IiIIIIIi_Class66 {
    private final CustomDrawContext context;
    private final float tickDelta;

    @Generated
    public CustomDrawContext getContext() {
        return this.context;
    }

    @Generated
    public float getTickDelta() {
        return this.tickDelta;
    }

    @Generated
    public HudRenderEvent(CustomDrawContext customDrawContext, float f) {
        this.context = customDrawContext;
        this.tickDelta = f;
    }
}

