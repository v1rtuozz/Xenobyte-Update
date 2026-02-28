package yourbunnywrote.team.virtuoz.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import yourbunnywrote.team.virtuoz.ModulesList;
import yourbunnywrote.team.virtuoz.api.Xeno;
import yourbunnywrote.team.virtuoz.api.exceptions.DuplicateModuleException;
import yourbunnywrote.team.virtuoz.api.gui.WidgetMessage;
import yourbunnywrote.team.virtuoz.api.gui.WidgetMode;
import yourbunnywrote.team.virtuoz.api.module.CheatModule;
import yourbunnywrote.team.virtuoz.api.module.PerformMode;
import yourbunnywrote.team.virtuoz.api.module.PerformSource;
import yourbunnywrote.team.virtuoz.gui.click.elements.Button;
import yourbunnywrote.team.virtuoz.modules.Widgets;
import yourbunnywrote.team.virtuoz.modules.XenoGui;
import yourbunnywrote.team.virtuoz.utils.Config;
import yourbunnywrote.team.virtuoz.utils.EventHelper;
import net.minecraft.client.Minecraft;

public class ModuleHandler  {
    
    private List<CheatModule> modulesList, workingList, enabledList;
    
    public ModuleHandler() {
        modulesList = new ArrayList<CheatModule>();
        new ModulesList().forEach(m -> {
            if (modulesList.contains(m)) {
                DuplicateModuleException dEx = new DuplicateModuleException(m);
                Xeno.logger.error(dEx);
                dEx.printStackTrace();
                throw dEx;
            }
            modulesList.add(m);
        });
        enabledList = new CopyOnWriteArrayList<CheatModule>();
        workingList = allModules().filter(CheatModule::isWorking).collect(Collectors.toList());
        allModules().forEach(m -> m.handlerInit(this));
        new Config(this);
        enabledList = workingModules().peek(m -> m.cfgState = m.getMode() == PerformMode.SINGLE || m.getMode() == PerformMode.DISABLED_ON_START ? false : m.getMode() == PerformMode.ENABLED_ON_START ? true : m.cfgState).filter(m -> m.cfgState).collect(Collectors.toCollection(CopyOnWriteArrayList::new));
        allModules().forEach(CheatModule::onPostInit);
        new PacketHandler(this, Minecraft.getMinecraft().getNetHandler());
        new EventHandler(this);
    }
    
    public Stream<CheatModule> allModules() {
        return modulesList.stream();
    }
    
    public Stream<CheatModule> enabledModules() {
        return enabledList.stream();
    }
    
    public Stream<CheatModule> workingModules() {
        return workingList.stream();
    }
    
    public Stream<CheatModule> categoryedModules() {
        return workingModules().filter(CheatModule::hasCategory);
    }
    
    public XenoGui xenoGui() {
        return (XenoGui) getModuleByClass(XenoGui.class);
    }
    
    public Widgets widgets() {
        return (Widgets) getModuleByClass(Widgets.class);
    }
    
    public CheatModule getModuleByName(String name) {
        return moduleGetter(m -> m.getName().equals(name));
    }
    
    public CheatModule getModuleByClass(Class<? extends CheatModule> clazz) {
        return moduleGetter(m -> m.getClass().equals(clazz));
    }
    
    public CheatModule moduleGetter(Predicate<CheatModule> predicate)  {
        return allModules().filter(predicate).findFirst().orElse(null);
    }
    
    public void perform(CheatModule module) {
        perform(module, null);
    }
    
    public boolean isEnabled(CheatModule module) {
        return module == null ? false : enabledList.contains(module);
    }
    
    public boolean toggle(CheatModule module) {
        if (isEnabled(module)) {
            disable(module);
        } else {
            enable(module);
        }
        return isEnabled(module);
    }
    
    public void enable(CheatModule module) {
        if (!isEnabled(module)) {
            enabledList.add(module);
            module.cfgState = true;
            module.setLastCounter();
            if (module.provideStateEvents()) {
                module.onEnabled();
                workingModules().forEach(m -> m.onModuleEnabled(module));
            }
            if (module.provideForgeEvents()) {
                EventHelper.register(module);
            }
        }
    }
    
    public void disable(CheatModule module) {
        if (isEnabled(module)) {
            enabledList.remove(module);
            module.cfgState = false;
            if (module.provideStateEvents()) {
                module.onDisabled();
                workingModules().forEach(m -> m.onModuleDisabled(module));
            }
            if (module.provideForgeEvents()) {
                EventHelper.unregister(module);
            }
        }
    }
    
    public void bind(CheatModule module, Button button, int key) {
        CheatModule conflicted = moduleGetter(m -> m.hasKeyBind() && m.getKeyBind() == key);
        if (conflicted != null && ! conflicted.equals(module)) {
            widgets().widgetMessage(new WidgetMessage(Xeno.lang.get("Keybind conflicts with", "Кейбинд конфликтует с") + " " + (conflicted.hasCategory() ? conflicted.getCategory() + "/" : "") + conflicted, WidgetMode.FAIL));
        } else {
            if (module.getKeyBind() == key) {
                if (!module.equals(xenoGui())) {
                    module.resetKeyBind();
                    button.buttonValue(null);
                    if (module.provideBindEvents()) {
                        workingModules().forEach(m -> m.onModuleUnBinded(module));
                    }
                }
            } else {
                module.setKeyBind(key);
                button.buttonValue(module.getKeyName());
                if (module.provideBindEvents()) {
                    workingModules().forEach(m -> m.onModuleBinded(module));
                }
            }
        }
    }
    
    public void perform(CheatModule module, Button button) {
        WidgetMessage mess = new WidgetMessage(module, Xeno.lang.get("completed", "выполнен"), WidgetMode.INFO);
        switch (module.getMode()) {
        case SINGLE:
            break;
        case TOGGLE:
        case ENABLED_ON_START:
        case DISABLED_ON_START:
            boolean enabled = toggle(module);
            if (button != null) {
                button.setSelected(enabled);
            }
            mess = new WidgetMessage(module, enabled ? "ON" : "OFF", enabled ? WidgetMode.SUCCESS : WidgetMode.FAIL);
        }
        if (module.isWidgetable() && module.allowStateMessages()) {
            widgets().widgetMessage(mess);
        }
        module.onPerform(button == null ? PerformSource.KEY : PerformSource.BUTTON);
    }

}