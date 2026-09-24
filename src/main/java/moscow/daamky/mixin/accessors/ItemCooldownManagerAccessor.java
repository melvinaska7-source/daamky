package moscow.daamky.mixin.accessors;

import java.util.Map;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={ItemCooldownManager.class})
public interface ItemCooldownManagerAccessor {
    @Accessor(value="entries")
    public Map<Identifier, Object> daamky$getEntries();

    @Accessor(value="tick")
    public int daamky$getTick();

    @Invoker(value="getGroup")
    public Identifier daamky$getGroup(ItemStack var1);
}

