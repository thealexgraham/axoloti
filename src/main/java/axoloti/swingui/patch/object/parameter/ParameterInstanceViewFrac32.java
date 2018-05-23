package axoloti.swingui.patch.object.parameter;

import axoloti.Modulation;
import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.datatypes.ValueFrac32;
import axoloti.patch.object.parameter.ParameterInstance;
import axoloti.patch.object.parameter.ParameterInstanceFrac32;
import axoloti.patch.object.parameter.preset.PresetDouble;
import axoloti.realunits.NativeToReal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

abstract class ParameterInstanceViewFrac32 extends ParameterInstanceView {

    ParameterInstanceViewFrac32(ParameterInstance parameterInstance, IAxoObjectInstanceView axoObjectInstanceView) {
        super(parameterInstance, axoObjectInstanceView);
        initComponents();
    }

    private void initComponents() {
        // TODO: fix modulations: does not belong in view, review
        if (getDModel().getModulators() != null) {
            List<Modulation> modulators = getDModel().getModulators();
            for (Modulation m : modulators) {
                System.out.println("mod amount " + m.getValue());
                m.postConstructor(getDModel());
            }
        }
    }

    @Override
    public ParameterInstanceFrac32 getDModel() {
        return (ParameterInstanceFrac32) super.getDModel();
    }

    @Override
    void updateUnit() {
        super.updateUnit();
        NativeToReal conv = getDModel().getConversion();
        if (conv != null) {
            String n = conv.convertToReal(new ValueFrac32(
                    getDModel().getValue()));
            valuelbl.setText(n);
        }
    }

    @Override
    public void populatePopup(JPopupMenu m) {
        super.populatePopup(m);
        JMenuItem m_default = new JMenuItem("Reset to default value");
        m_default.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDModel().getController().addMetaUndo("Reset to default", getFocusEdit());
                getDModel().getController().applyDefaultValue();
            }
        });
        m.add(m_default);
    }

    @Override
    public boolean handleAdjustment() {
        // TODO: fix preset logic
        PresetDouble p = getDModel().getPreset(presetEditActive);
        if (p != null) {
            p.setValue(getControlComponent().getValue());
        }
        if (getDModel().getValue() != getControlComponent().getValue()) {
            if (getDModel().getController() != null) {
                Double d = getControlComponent().getValue();
                getDModel().getController().changeValue(d);
            }
        } else {
            return false;
        }
        return true;
    }

    public void updateModulation(int index, double amount) {
        getDModel().updateModulation(index, amount);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        super.modelPropertyChange(evt);
        if (ParameterInstance.VALUE.is(evt)) {
            Double v = (Double) evt.getNewValue();
            ctrl.setValue(v);
            updateUnit();
        } else if (ParameterInstance.CONVERSION.is(evt)) {
            updateUnit();
        }
    }
}