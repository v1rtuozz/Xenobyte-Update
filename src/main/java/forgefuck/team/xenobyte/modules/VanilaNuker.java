package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.config.Cfg;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.gui.click.elements.Button;
import yourbunnywrote.team.virtuoz.gui.click.elements.Panel;
import yourbunnywrote.team.virtuoz.gui.click.elements.ScrollSlider;
import yourbunnywrote.team.virtuoz.utils.TickHelper;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class VanilaNuker extends CheatModule {
    
    @Cfg("handshake") private boolean handshake;
    @Cfg("onlyXRay") private boolean onlyXRay;
    @Cfg("onView") private boolean onView;
    @Cfg("radius") private int radius;
    @Cfg("delay") private int delay;
    
    public VanilaNuker() {
        super("VanilaNuker", Category.WORLD, PerformMode.TOGGLE);
        radius = 1;
    }
    
    @Override public int tickDelay() {
        return delay;
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame) {
            utils.nukerList(onView ? utils.mop() : utils.myCoords(), radius, onlyXRay ? xraySelector() : null).forEach(pos -> {
                int x = pos[0];
                int y = pos[1];
                int z = pos[2];
                if (utils.isInCreative()) {
                    utils.sendPacket(new C07PacketPlayerDigging(0, x, y, z, 0));
                } else {
                    utils.sendPacket(new C07PacketPlayerDigging(0, x, y, z, 0));
                    utils.sendPacket(new C07PacketPlayerDigging(2, x, y, z, 0));
                }
            });
            if (handshake) {
                utils.player().swingItem();
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Digging blocks in a radius (in tick)", "Вскапывание блоков в радиусе (в тике)");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Radius", radius, 6) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
                }
            },
            new ScrollSlider("Delay", delay, 0, TickHelper.ONE_SEC) {
                @Override public void onScroll(int dir, boolean withShift) {
                    delay = processSlider(dir, withShift);
                }
                @Override public String elementDesc() {
                    return lang.get("Digging delay", "Задержка копания");
                }
            },
            new Button("OnView", onView) {
                @Override public void onLeftClick() {
                    buttonValue(onView = !onView);
                }
                @Override public String elementDesc() {
                    return lang.get("By sight or around the player", "По взгляду или вокруг игрока");
                }
            },
            new Button("OnlyXRay", onlyXRay) {
                @Override public void onLeftClick() {
                    buttonValue(onlyXRay = !onlyXRay);
                }
                @Override public String elementDesc() {
                    return lang.get("Digging in only XRay blocks", "Вскапывание только XRay блоков");
                }
            },
            new Button("HandShake", handshake) {
                @Override public void onLeftClick() {
                    buttonValue(handshake = !handshake);
                }
                @Override public String elementDesc() {
                    return lang.get("Wave of the hand", "Взмах руки");
                }
            }
        );
    }

}
