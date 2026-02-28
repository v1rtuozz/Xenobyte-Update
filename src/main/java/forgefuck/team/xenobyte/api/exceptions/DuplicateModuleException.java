package yourbunnywrote.team.virtuoz.api.exceptions;

import yourbunnywrote.team.virtuoz.api.module.CheatModule;

public class DuplicateModuleException extends RuntimeException {
    
    public DuplicateModuleException(CheatModule module) {
        super("Duplicate module found [" + module + "]");
    }

}
