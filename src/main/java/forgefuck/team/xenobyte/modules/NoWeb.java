package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.utils.Reflections;
import net.minecraft.entity.Entity;

public class NoWeb extends CheatModule {
    
    public NoWeb() {
        super("NoWeb", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Entity.class, utils.player(), false, 27);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("The web no longer clings", "Паутина больше не цепляет");
    }

}
