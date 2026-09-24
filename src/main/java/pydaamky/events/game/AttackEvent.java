package pydaamky.events.game;

import lombok.Generated;
import net.minecraft.entity.Entity;
import pydaamky.events.EventCancellable;

public class AttackEvent
extends EventCancellable {
    private final Entity entity;

    @Generated
    public Entity getEntity() {
        return this.entity;
    }

    @Generated
    public AttackEvent(Entity entity) {
        this.entity = entity;
    }
}

