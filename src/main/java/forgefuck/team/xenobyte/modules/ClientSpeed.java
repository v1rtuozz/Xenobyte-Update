package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.config.Cfg;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.gui.click.elements.Panel;
import yourbunnywrote.team.virtuoz.gui.click.elements.ScrollSlider;
import yourbunnywrote.team.virtuoz.utils.Reflections;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

public class ClientSpeed extends CheatModule {
    
    @Cfg("tickRate") private int tickRate; 
    private Timer vanilaTimer;
    
    public ClientSpeed() {
        super("ClientSpeed", Category.PLAYER, PerformMode.TOGGLE);
        tickRate = 5;
    }
    
    @Override public void onPostInit() {
        vanilaTimer = Reflections.getPrivateValue(Minecraft.class, utils.mc(), 16);
    }
    
    @Override public void onDisabled() {
        vanilaTimer.timerSpeed = 1;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            vanilaTimer.timerSpeed = tickRate;
        }    
    }
    
    @Override public String moduleDesc() {
        return lang.get("Changing the speed of client ticks", "Изменение скорости клиентских тиков");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("TickRate", tickRate, 20) {
                @Override public void onScroll(int dir, boolean withShift) {
                    tickRate = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Tick modifier", "Модификатор частоты тиков");
                }
            }
        );
    }

}