package life.zwp.community.utils;

import java.util.UUID;

public class UUIDUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public static void main(String[] args) {
        String uuid = getUUID();
        System.err.println(uuid);
    }
}
