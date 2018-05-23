package axoloti.piccolo.patch.object.attribute;

import axoloti.abstractui.DocumentWindow;
import axoloti.abstractui.IAxoObjectInstanceView;
import axoloti.patch.object.attribute.AttributeInstance;
import axoloti.patch.object.attribute.AttributeInstanceTextEditor;
import axoloti.piccolo.components.control.PButtonComponent;
import axoloti.swingui.TextEditor;
import javax.swing.SwingUtilities;

class PAttributeInstanceViewTextEditor extends PAttributeInstanceViewString {

    PButtonComponent bEdit;

    public PAttributeInstanceViewTextEditor(AttributeInstance attribute, IAxoObjectInstanceView axoObjectInstanceView) {
        super(attribute, axoObjectInstanceView);
        initComponents();
    }

    @Override
    public AttributeInstanceTextEditor getDModel() {
        return (AttributeInstanceTextEditor) super.getDModel();
    }

    void showEditor() {
        if (getDModel().editor == null) {
            DocumentWindow dw = (DocumentWindow) SwingUtilities.getWindowAncestor(this.getProxyComponent());
            getDModel().editor = new TextEditor(AttributeInstanceTextEditor.ATTR_VALUE, getDModel(), dw);
            getDModel().editor.setTitle(getDModel().getParent().getInstanceName() + "/" + getDModel().getDModel().getName());
        }
        getDModel().editor.toFront();

        // TODO: piccolo verify this
        getDModel().editor.setState(java.awt.Frame.NORMAL);
        getDModel().editor.setVisible(true);
    }

    private void initComponents() {
        bEdit = new PButtonComponent("Edit", axoObjectInstanceView);
        addChild(bEdit);
        bEdit.addActListener(new PButtonComponent.ActListener() {
            @Override
            public void fire() {
                showEditor();
            }
        });
    }

    @Override
    public void lock() {
        if (bEdit != null) {
            bEdit.setEnabled(false);
        }
    }

    @Override
    public void unlock() {
        if (bEdit != null) {
            bEdit.setEnabled(true);
        }
    }

    @Override
    public void setString(String sText) {
        getDModel().setValue(sText);
        if (getDModel().editor != null) {
            getDModel().editor.setText(sText);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}