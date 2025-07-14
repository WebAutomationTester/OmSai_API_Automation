package PayloadFiles;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
    public static JsonPath convertToJson(String respones){
        JsonPath jsAdd = new JsonPath(respones);
        return jsAdd;
    }
}
