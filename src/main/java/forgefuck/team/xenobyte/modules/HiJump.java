package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.config.Cfg;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.gui.click.elements.Panel;
import yourbunnywrote.team.virtuoz.gui.click.elements.ScrollSlider;
import yourbunnywrote.team.virtuoz.utils.TickHelper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HiJump extends CheatModule {
    
    @Cfg("power") private int power;
    
    public HiJump() {
        super("HiJump", Category.MOVE, PerformMode.TOGGLE);
        power = 5;
    }
    
    private void removePotion() {
        utils.player().removePotionEffect(Potion.jump.getId());
    }
    
    @Override public void onDisabled() {
        removePotion();
    }
    
    @Override public int tickDelay() {
        return TickHelper.ONE_SEC;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            removePotion();
            utils.player().addPotionEffect(new PotionEffect(Potion.jump.getId(), Integer.MAX_VALUE, power));
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Jump with a given power", "Прыжок с заданной мощностью");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Power", power, 20) {
                @Override public void onScroll(int dir, boolean withShift) {
                    power = processSlider(dir, withShift);
                }
            }
        );
    }

}
