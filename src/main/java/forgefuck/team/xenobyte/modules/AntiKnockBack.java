package yourbunnywrote.team.virtuoz.modules;

import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class AntiKnockBack extends CheatModule {
    
    public AntiKnockBack() {
        super("AntiKnockBack", Category.MOVE, PerformMode.TOGGLE);
    }
    
    @Override public boolean doReceivePacket(Packet packet) {
        if (packet instanceof S12PacketEntityVelocity) {
            if (utils.isInGame() && utils.myId() == ((S12PacketEntityVelocity)packet).func_149412_c()) {
                return false;
            }
        }
        return true;
    }
    
    @Override public String moduleDesc() {
        return lang.get("Turns off the knockback effect of the player", "Выключает эффект отбрасывания у игрока");
    }

}