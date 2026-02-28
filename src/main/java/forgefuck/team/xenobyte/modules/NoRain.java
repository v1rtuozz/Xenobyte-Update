package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;

public class NoRain extends CheatModule {
    
    public NoRain() {
        super("NoRain", Category.WORLD, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.world().setRainStrength(0);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("When the PC is shit", "Когда комп говно");
    }

}
