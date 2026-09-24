package daamky.client;

import net.minecraft.entity.LivingEntity;
import daamky.client.ModeSetting;
import daamky.client.DaamkyClient;
import daamky.client.iIIiIIiIi_Class294;
import daamky.client.iiIIiIIIi_Class402;
import daamky.client.iiIIiIiII_Class405;
import daamky.client.AuraModule;

public abstract class iiiIIIIi_Class226
extends ModeSetting.Nested1_42765c60
implements iIIiIIiIi_Class294 {
    public iiiIIIIi_Class226(ModeSetting iIiiiiiII_Class125, String string) {
        super(iIiiiiiII_Class125, string);
    }

    public abstract void rotate(iiIIiIiII_Class405 var1, float var2, boolean var3, boolean var4, iiIIiIIIi_Class402 var5, LivingEntity var6);

    public AuraModule aura() {
        return DaamkyClient.getInstance().getModuleManager().getModule(AuraModule.class);
    }

    public void attack() {
    }

    public void targetNull() {
    }

    public void enabled() {
    }

    public void update() {
    }

    public boolean canAttack() {
        return true;
    }
}

