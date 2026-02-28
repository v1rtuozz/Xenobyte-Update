package yourbunnywrote.team.virtuoz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import yourbunnywrote.team.virtuoz.render.Renderer;
import yourbunnywrote.team.virtuoz.utils.LangProvider;
import yourbunnywrote.team.virtuoz.utils.Utils;

public interface Xeno {

    String mod_id = "xxxeeeennooobytee updated";
    String mod_name = "xxxeeeennooobytee";
    String mod_version = "2.0";
    String mod_author = "Ninteend0&virtuoz";
    String format_prefix = "§8[§4" + mod_name + "§8]§r ";
    
    String ds_link = "1";
    String tg_link = "1";
    String gh_link = "1";
    String yt_link = "1";

    Utils utils = new Utils();
    Renderer render = new Renderer();
    LangProvider lang = new LangProvider();
    Logger logger = LogManager.getLogger(mod_name);
    
}