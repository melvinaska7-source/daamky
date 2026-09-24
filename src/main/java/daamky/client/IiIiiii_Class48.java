package daamky.client;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import daamky.client.IIIIIii_Class4;
import daamky.client.IiIiiiI_Class47;
import daamky.client.IiiIIiI_Class51;
import daamky.client.IiiiIi_Class30;
import daamky.client.iIIIiI_Class35;
import daamky.client.iIiiIIi_Class90;
import daamky.client.iiIIiII_Class101;

public class IiIiiii_Class48
extends IIIIIii_Class4
implements IiiIIiI_Class51<IiIiiiI_Class47> {
    public IiIiiii_Class48(iiIIiII_Class101 iiIIiII_Class1012) throws MalformedURLException {
        super("https://api.minecraftservices.com/launcher/login");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("platform", "PC_LAUNCHER");
        jsonObject.addProperty("xtoken", iiIIiII_Class1012.I_method_4fc45e01());
        this.I_method_ef32890a(new iIiiIIi_Class90(jsonObject));
    }

    @Override
    public IiIiiiI_Class47 I_method_b7becc11(iIIIiI_Class35 iIIIiI_Class352, IiiiIi_Class30 iiiiIi_Class30) throws IOException {
        return new IiIiiiI_Class47(System.currentTimeMillis() + (long)iiiiIi_Class30.i_method_5239fcc9("expires_in") * 1000L, iiiiIi_Class30.i_method_7caa7e8a("token_type"), iiiiIi_Class30.i_method_7caa7e8a("access_token"));
    }
}

