package pydaamky.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import pydaamky.classes.PyCommand;
import daamky.client.IIIiiIIi_Class26;
import daamky.client.IIIiiiIi_Class30;
import daamky.client.DaamkyClient;

public class PyCommands {
    private final List<PyCommand> mine = new ArrayList<PyCommand>();

    public PyCommand create(String string) {
        PyCommand pyCommand = new PyCommand(string);
        this.mine.add(pyCommand);
        return pyCommand;
    }

    public boolean exists(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        for (IIIiiIIi_Class26 iIIiiIDaamkyClient6 : PyCommands.registry().I_method_cafa303b()) {
            for (String string2 : iIIiiIDaamkyClient6.I_method_84c7081b()) {
                if (!string2.equalsIgnoreCase(string)) continue;
                return true;
            }
        }
        return false;
    }

    public List<String> names() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (IIIiiIIi_Class26 iIIiiIDaamkyClient6 : PyCommands.registry().I_method_cafa303b()) {
            if (iIIiiIDaamkyClient6.I_method_84c7081b().isEmpty()) continue;
            arrayList.add(iIIiiIDaamkyClient6.I_method_84c7081b().getFirst());
        }
        return arrayList;
    }

    public boolean remove(String string) {
        if (string == null) {
            return false;
        }
        String string2 = string.toLowerCase(Locale.ROOT);
        for (PyCommand pyCommand : new ArrayList<PyCommand>(this.mine)) {
            if (!pyCommand.name().equals(string2)) continue;
            this.mine.remove(pyCommand);
            return pyCommand.remove();
        }
        return false;
    }

    public boolean run(String string) {
        if (string == null || string.isBlank()) {
            return false;
        }
        String string2 = PyCommands.registry().I_method_80ea594();
        String string3 = string.startsWith(string2) ? string : string2 + string;
        try {
            return PyCommands.registry().I_method_9e86975a(string3);
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public String prefix() {
        return PyCommands.registry().I_method_80ea594();
    }

    private static IIIiiiIi_Class30 registry() {
        return DaamkyClient.getInstance().I_method_b4d046cb();
    }
}

