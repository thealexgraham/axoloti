package axoloti.swingui.patch.object.parameter;

import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.patch.object.parameter.ParameterInstance;
import axoloti.swingui.components.control.CheckboxComponent;

class ParameterInstanceViewBin32 extends ParameterInstanceViewBin {

    ParameterInstanceViewBin32(ParameterInstance parameterInstance, IAxoObjectInstanceView axoObjectInstanceView) {
        super(parameterInstance, axoObjectInstanceView);
        initCtrlComponent(ctrl);
    }

    @Override
    public CheckboxComponent createControl() {
        return new CheckboxComponent(0, 32);
    }

    private final CheckboxComponent ctrl = createControl();

    @Override
    public CheckboxComponent getControlComponent() {
        return ctrl;
    }
}
