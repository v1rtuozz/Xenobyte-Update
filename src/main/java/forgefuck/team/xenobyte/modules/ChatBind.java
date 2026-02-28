package yourbunnywrote.team.virtuoz.modules;

import java.util.ArrayList;
import java.util.List;

import yourbunnywrote.team.virtuoz.api.config.Cfg;
import yourbunnywrote.team.virtuoz.api.gui.InputType;
import yourbunnywrote.team.virtuoz.api.module.Category;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.api.module.PerformSource;
import yourbunnywrote.team.virtuoz.gui.click.elements.Button;
import yourbunnywrote.team.virtuoz.gui.click.elements.Panel;
import yourbunnywrote.team.virtuoz.gui.swing.UserInput;

public class ChatBind extends CheatModule {
    
    @Cfg("commands") private List<String> commands;
    
    public ChatBind() {
        super("ChatBind", Category.MISC, PerformMode.SINGLE);
        commands = new ArrayList<String>();
        commands.add("/home");
    }
    
    @Override public void onPerform(PerformSource src) {
        commands.forEach(utils::serverChatMessage);
    }
    
    @Override public String moduleDesc() {
        return lang.get("Execution of the set commands by keybind", "Выполнение заданных команд по кейбинду");
    }
    
    @Override public Panel settingPanel() {
        return new Panel(
            new Button("Commands") {
                @Override public void onLeftClick() {
                    new UserInput(lang.get("Commands", "Команды"), commands, InputType.CUSTOM).showFrame();
                }
                @Override public String elementDesc() {
                    return lang.get("Command list", "Список команд");
                }
            }
        );
    }

}
