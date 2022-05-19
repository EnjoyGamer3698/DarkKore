package io.github.darkkronicle.darkkore.gui.config;

import io.github.darkkronicle.darkkore.config.options.StringOption;
import io.github.darkkronicle.darkkore.gui.components.Component;
import io.github.darkkronicle.darkkore.gui.components.impl.TextBoxComponent;
import io.github.darkkronicle.darkkore.util.Color;
import io.github.darkkronicle.darkkore.util.FluidText;
import io.github.darkkronicle.darkkore.util.StringUtil;
import net.minecraft.text.Text;

public class StringOptionComponent extends TextOptionComponent<String, StringOption> {

    private TextBoxComponent textBox;

    public StringOptionComponent(StringOption option, int width) {
        super(option, width);
        selectable = true;
    }

    @Override
    public boolean isValid(String string) {
        return true;
    }

    @Override
    public String getStringValue() {
        return option.getValue();
    }

    @Override
    public void setValueFromString(String string) {
        option.setValue(string);
    }

    @Override
    public Text getConfigTypeInfo() {
        return new FluidText("§7§o" + StringUtil.translate("darkkore.optiontype.info.string"));
    }

    protected void onChanged(String string) {
        option.setValue(string);
        onUpdate();
    }

    @Override
    public Component getMainComponent() {
        textBox = new TextBoxComponent(option.getValue(),150, 14, this::onChanged);
        textBox.setBackgroundColor(new Color(50, 50, 50, 150));
        return textBox;
    }

    @Override
    public boolean charTyped(char key, int modifiers) {
        if (textBox.isSelected()) {
            return textBox.charTyped(key, modifiers);
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (textBox.isSelected()) {
            return textBox.keyPressed(keyCode, scanCode, modifiers);
        }
        return false;
    }
}