package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.api.module.PerformSource;
import yourbunnywrote.team.virtuoz.gui.swing.CreditsGui;

public class Credits extends CheatModule {
    
    public Credits() {
        super("Credits", Category.MISC, PerformMode.SINGLE);
    }
    
    @Override public void onPerform(PerformSource src) {
        new CreditsGui().showFrame();
    }
    
    @Override public String moduleDesc() {
        return lang.get("Product information + links", "Информация о продукте + ссылки");
    }

}
