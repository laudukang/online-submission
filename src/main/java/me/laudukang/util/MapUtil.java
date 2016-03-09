package me.laudukang.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Created with IDEA
 * <p>Author: laudukang
 * <p>Date: 2016/3/9
 * <p>Time: 9:53
 * <p>Version: 1.0
 */
public class MapUtil {
    public static Map<String, Object> deleteMap = new HashMap<>(2);
    public static Map<String, Object> saveMap = new HashMap<>(2);
    public static Map<String, Object> updateMap = new HashMap<>(2);
    public static Map<String, Object> forbiddenOperationMap = new HashMap<>(2);

    public static Map<String, Object> deleteMap() {
        if (deleteMap.isEmpty()) {
            deleteMap.put("success", true);
            deleteMap.put("msg", "删除成功");
        }
        return deleteMap;
    }

    public static Map<String, Object> saveMap() {
        if (saveMap.isEmpty()) {
            saveMap.put("success", true);
            saveMap.put("msg", "保存成功");
        }
        return saveMap;
    }

    public static Map<String, Object> updateMap() {
        if (updateMap.isEmpty()) {
            updateMap.put("success", true);
            updateMap.put("msg", "更新成功");
        }
        return updateMap;
    }

    public static Map<String, Object> forbiddenOperationMap() {
        if (forbiddenOperationMap.isEmpty()) {
            forbiddenOperationMap.put("success", false);
            forbiddenOperationMap.put("msg", "非法操作");
        }
        return forbiddenOperationMap;
    }
}
