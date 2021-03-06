package me.desht.pneumaticcraft.common.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.desht.pneumaticcraft.common.progwidgets.WidgetRegistrator;

import java.util.*;

public class ProgWidgetConfig extends JsonConfig {
    public static final Set<String> blacklistedPieces = new HashSet<String>();

    public ProgWidgetConfig() {
        super(true);
    }

    @Override
    public String getFolderName() {
        return "ProgrammingPuzzleBlacklist";
    }

    @Override
    protected void writeToJson(JsonObject json) {
        json.addProperty("description", "In the 'blacklist' tag you can put the programming puzzle names that need to blacklisted from this instance. When they were used in existing programs already they will be deleted. A list of all programming puzzle names can be seen in 'allWidgets'.");
        JsonArray array = new JsonArray();
        List<String> names = new ArrayList<String>(WidgetRegistrator.getAllWidgetNames());
        Collections.sort(names);
        for (String name : names) {
            array.add(new JsonPrimitive(name));
        }
        json.add("allWidgets", array);
        array = new JsonArray();
        for (String name : blacklistedPieces) {
            array.add(new JsonPrimitive(name));
        }
        json.add("blacklist", array);
        WidgetRegistrator.compileBlacklist();
    }

    @Override
    protected void readFromJson(JsonObject json) {
        JsonArray array = json.get("blacklist").getAsJsonArray();
        blacklistedPieces.clear();
        for (JsonElement element : array) {
            blacklistedPieces.add(element.getAsString());
        }
    }

}
