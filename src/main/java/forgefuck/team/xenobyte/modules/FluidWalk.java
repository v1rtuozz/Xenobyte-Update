package yourbunnywrote.team.virtuoz.modules;

import org.lwjgl.input.Keyboard;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import net.minecraft.block.BlockLiquid;

public class FluidWalk extends CheatModule {
    
    public FluidWalk() {
        super("FluidWalk", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public void onTick(boolean inGame) {
        if (inGame && utils.block(utils.myCoords()) instanceof BlockLiquid) {
            utils.player().motionY = 0;
            if (Keyboard.isKeyDown(utils.mc().gameSettings.keyBindJump.getKeyCode())) {
                utils.player().motionY = 0.4;
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Walking on liquids", "Прогулки по жидкостям");
    }

}
