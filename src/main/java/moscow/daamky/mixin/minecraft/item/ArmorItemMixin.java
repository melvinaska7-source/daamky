package moscow.daamky.mixin.minecraft.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import daamky.client.iIiIiiIIi_Class346;

@Mixin(value={ArmorItem.class})
public abstract class ArmorItemMixin
implements iIiIiiIIi_Class346 {
    @Unique
    private EquipmentType daamky$type;
    @Unique
    private ArmorMaterial daamky$material;

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    public void saveArgs(ArmorMaterial armorMaterial, EquipmentType equipmentType, Item.Settings settings, CallbackInfo callbackInfo) {
        this.daamky$type = equipmentType;
        this.daamky$material = armorMaterial;
    }

    @Override
    public ArmorMaterial daamky$getMaterial() {
        return this.daamky$material;
    }

    @Override
    public EquipmentType daamky$getType() {
        return this.daamky$type;
    }
}

