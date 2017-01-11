package indi.jcl.magicutils.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String getJsonString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Unable to serialize to json: " + object, e);
            return null;
        }
    }

    public static String getJsonString(Object object, Class clazz) {
        try {
            return mapper.writerWithView(clazz).writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Unable to serialize to json: " + object, e);
            return null;
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            logger.error("Unable to deserialize from json: " + json, e);
            return null;
        }
    }

    public static void main(String[] args) {
//        User user = new User();
//        user.setPwd("123");
//        user.setUserName("abc");
//        System.out.println(getJsonString(user));
//        System.out.println(getJsonString(user, User.Output.class));
//        System.out.println(getJsonString(user, User.Input.class));

/*        JSONObject obj = new JSONObject();
        obj.put("a1", "a1");
        JSONArray array = new JSONArray();
        JSONObject b2 = new JSONObject();
        b2.put("test","test");
        array.add(b2);
        obj.put("a2", array);
        Map<String,Object> ma = toBean(obj.toString(), Map.class);
//        System.out.println(ma.get("a2"));
        List<Map> list = (ArrayList) ma.get("a2");
        System.out.println(list.get(0).get("test"));
//        System.out.println(ma.get("a2").getClass().getName());
*/
    }
}