package daamky.client;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import daamky.client.iIIIiiIii_Class284;
import daamky.client.iIIIiiiII_Class285;
import daamky.client.iIIiIIii_Class148;
import daamky.client.iIiIIIii_Class164;

public class iIiiiiIi_Class190
extends iIiIIIii_Class164 {
    public iIiiiiIi_Class190() {
        super("modules.settings.assist.trapka", Items.NETHERITE_SCRAP.getDefaultStack(), iIIiIIii_Class148.i_field_88b781);
    }

    @Override
    public ItemStack I_method_db7f702c() {
        if (iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.Ii_field_ac164e6c) && !iIIIiiiII_Class285.I_method_dbbac60d("holytime")) {
            return Items.POPPED_CHORUS_FRUIT.getDefaultStack();
        }
        return Items.NETHERITE_SCRAP.getDefaultStack();
    }

    @Override
    public boolean I_method_75fee910() {
        return true;
    }
}

