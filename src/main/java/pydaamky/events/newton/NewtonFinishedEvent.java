package pydaamky.events.newton;

import lombok.Generated;
import daamky.client.IiIIIIIi_Class66;
import daamky.client.IiIIIIiI_Class67;

@IiIIIIiI_Class67(I_method_80b3cd54="newton_finished")
public class NewtonFinishedEvent
extends IiIIIIIi_Class66 {
    private final String process;

    @Generated
    public String getProcess() {
        return this.process;
    }

    @Generated
    public NewtonFinishedEvent(String string) {
        this.process = string;
    }
}

