package yourbunnywrote.team.virtuoz.modules;

import org.lwjgl.input.Keyboard;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.api.module.PerformSource;
import yourbunnywrote.team.virtuoz.gui.click.XenoGuiScreen;

public class XenoGui extends CheatModule {
    
    public XenoGui() {
        super("XenoGui", Category.NONE, PerformMode.SINGLE);
        setKeyBind(Keyboard.KEY_B);
    }
    
    @Override public void onPerform(PerformSource type) {
        utils.openGui(new XenoGuiScreen(moduleHandler()), true);
    }
    
    @Override public boolean allowStateMessages() {
        return false;
    }

}