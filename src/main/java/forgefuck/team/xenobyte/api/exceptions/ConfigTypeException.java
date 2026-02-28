package yourbunnywrote.team.virtuoz.api.exceptions;

public class ConfigTypeException extends Exception {
    
    public ConfigTypeException(String type) {
        super("Type [" + type + "] not implemented");
    }

}