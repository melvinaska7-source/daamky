package daamky.client;

import net.minecraft.entity.LivingEntity;
import daamky.client.ModeSetting;
import daamky.client.iiIIiIIIi_Class402;
import daamky.client.iiIIiIIii_Class404;
import daamky.client.iiIIiIiII_Class405;
import daamky.client.iiIIiIiIi_Class406;
import daamky.client.iiIIiIiiI_Class407;
import daamky.client.iiiIIIIi_Class226;
import ua.mintantileak.spk.Compile;

public class iiiIIIiI_Class227
extends iiiIIIIi_Class226 {
    public iiiIIIiI_Class227(ModeSetting iIiiiiiII_Class125) {
        super(iIiiiiiII_Class125, "modules.settings.aura.simpleRotation");
    }

    @Override
    @Compile(obfuscation=1)
    public void rotate(iiIIiIiII_Class405 iiIIiIiII_Class4052, float f, boolean bl, boolean bl2, iiIIiIIIi_Class402 iiIIiIIIi_Class4022, LivingEntity livingEntity) {
        iiIIiIIii_Class404 iiIIiIIii_Class4042 = iiIIiIiIi_Class406.I_method_a2bb2af5(livingEntity, this.aura());
        iiIIiIiII_Class4052.I_method_1acbf705(iiIIiIIii_Class4042, iiIIiIIIi_Class4022, 180.0f, 180.0f, 180.0f, iiIIiIiiI_Class407.II_field_32efc66c);
    }
}

