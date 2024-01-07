package persistence;

import org.json.JSONArray;
import org.json.JSONObject;

public interface ConvertToJson {
    // EFFECTS: returns this as JSON object.
    JSONObject toJson();
}
