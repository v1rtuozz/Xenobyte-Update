package yourbunnywrote.team.virtuoz.modules;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import yourbunnywrote.team.virtuoz.api.integration.NEI;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiOpenEvent;

public class NEIShowHidden extends CheatModule {
    
    public NEIShowHidden() {
        super("NEIShowHidden", Category.NONE, PerformMode.ENABLED_ON_START);
    }
    
    @Override public boolean isWorking() {
        return NEI.isAvailable();
    }
    
    @Override public boolean isWidgetable() {
        return false;
    }
    
    @SubscribeEvent public void guiOpen(GuiOpenEvent e) {
        if (e.gui instanceof GuiContainer) {
            NEI.clearHiddenItems();
        }
    }

}
