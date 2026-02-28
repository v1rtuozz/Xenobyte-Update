package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.utils.Reflections;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class FastBreak extends CheatModule {

    public FastBreak() {
        super("FastBreak", Category.WORLD, PerformMode.TOGGLE);
    }

    @Override public void onTick(boolean isInGame) {
        if (isInGame && utils.mc().gameSettings.keyBindAttack.getIsKeyPressed() && utils.mc().objectMouseOver != null && utils.mc().objectMouseOver.typeOfHit == MovingObjectType.BLOCK) {
            Reflections.setPrivateValue(PlayerControllerMP.class, utils.mc().playerController, 0, 8);
            int x = utils.mc().objectMouseOver.blockX;
            int y = utils.mc().objectMouseOver.blockY;
            int z = utils.mc().objectMouseOver.blockZ;
            Block block = utils.block(x, y, z);
            if (block != null) {
                float hardness = ((float) Reflections.getPrivateValue(PlayerControllerMP.class, utils.mc().playerController, 6)) + block.getPlayerRelativeBlockHardness(utils.player(), utils.world(), x, y, z);
                Reflections.setPrivateValue(PlayerControllerMP.class, utils.mc().playerController, hardness, 6);
            }
        }
    }
    
    @Override public String moduleDesc() {
        return lang.get("Fast breaking blocks", "Быстрое ломание блоков");
    }

}