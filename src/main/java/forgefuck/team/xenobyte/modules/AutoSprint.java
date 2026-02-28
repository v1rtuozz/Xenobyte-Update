package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;

public class AutoSprint extends CheatModule {
    
    public AutoSprint() {
        super("AutoSprint", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.player().setSprinting(true);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Non-stop sprint", "Постоянный спринт");
    }

}