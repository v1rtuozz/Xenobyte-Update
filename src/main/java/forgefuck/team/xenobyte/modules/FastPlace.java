package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.utils.Reflections;
import net.minecraft.client.Minecraft;

public class FastPlace extends CheatModule {
    
    public FastPlace() {
        super("FastPlace", Category.WORLD, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            Reflections.setPrivateValue(Minecraft.class, utils.mc(), 0, 47);
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Quick block placing", "Быстрая установка блоков");
    }

}