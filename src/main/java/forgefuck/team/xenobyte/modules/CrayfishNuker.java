package yourbunnywrote.team.virtuoz.modules;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import yourbunnywrote.team.virtuoz.api.config.Cfg;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.gui.click.elements.Button;
import yourbunnywrote.team.virtuoz.gui.click.elements.Panel;
import yourbunnywrote.team.virtuoz.gui.click.elements.ScrollSlider;
import net.minecraftforge.client.event.MouseEvent;

public class CrayfishNuker extends CheatModule {
    
    @Cfg("onlyXRay") private boolean onlyXRay;
    @Cfg("onView") private boolean onView;
    @Cfg("radius") private int radius;
    
    public CrayfishNuker() {
        super("CrayfishNuker", Category.MODS, PerformMode.TOGGLE);
        radius = 1;
    }
    
    @SubscribeEvent public void mouseEvent(MouseEvent e) {
        if (e.button == 0 && e.buttonstate) {
            utils.nukerList(onView ? utils.mop() : utils.myCoords(), radius, onlyXRay ? xraySelector() : null).forEach(pos -> {
                utils.sendPacket("cfm", (byte) 14, pos[0], pos[1], pos[2]);
            });
            e.setCanceled(true);
        }
    }
    
    @Override public boolean isWorking() {
        return Loader.isModLoaded("cfm");
    }
    
    @Override public String moduleDesc() {
        return lang.get("Deletes blocks in radius by left click", "Удаляет блоки в радиусе по ЛКМ");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new ScrollSlider("Radius", radius, 20) {
                @Override public void onScroll(int dir, boolean withShift) {
                    radius = processSlider(dir, withShift);
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
                    return lang.get("Removing only XRay blocks", "Удаление только XRay блоков");
                }
            }
        );
    }

}