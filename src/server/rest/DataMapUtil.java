package server.rest;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import server.comm.DataMap;
import utils.Log;

import java.util.List;
import java.util.Vector;

/**
 * @author 함의진
 * @version 1.5.0
 * DataMap 검증을 위한 Validation 클래스
 */
public class DataMapUtil {

    private static final String EMPTY_STRING = "";
    private static final String MASK_STRING = "**********";

    /**
     * String 가변 파라미터에 대해 Null 여부를 반환한다.
     * @param map 검증 대상 맵
     * @param args 가변 파라미터 - String 기반 키
     * @return
     */
    public static boolean isValid(DataMap map, String... args){
        for(String arg : args) if(map.get(arg) == null) return false;
        return true;
    }

    public static <T> List<T> getListOf(List<DataMap> maps, String key){
        List<T> list = new Vector<>();
        for(DataMap map : maps) list.add((T) map.get(key));

        return list;
    }

    /**
     * String 파라미터 값의 키-밸류에 대해 밸류를 특정 값으로 소거한다.
     * @param map 목표 데이터맵
     * @param key 목표 키
     */
    public static void mask(DataMap map, String key){
        if(map != null) map.put(key, MASK_STRING);
    }

    public static void maskWithLength(DataMap map, String key){
        if(map != null){
            StringBuffer mask = new StringBuffer("");
            for(int i=0; i<map.getString(key).length(); i++){
                if(i == 0) mask.append(map.getString(key).charAt(0));
                else mask.append("*");
            }
            map.put(key, mask.toString());
        }
    }

    public static boolean isSatisfied(DataMap map, String... args){
        for(String arg : args)
            if(map.get(arg) == null){
                return false;
            }else{
                if(map.getString(arg).equals(EMPTY_STRING)) return false;
            }
        return true;
    }

    public static boolean isEmptyValue(DataMap map, String... args){
        for(String arg : args) if(map.get(arg) == null || map.get(arg).toString().trim().equals("")) return false;
        return true;
    }

    public static void isEmptyValueThenPut(DataMap map, String[] keyArray, Object[] toPut){
        if(keyArray.length != toPut.length) throw new IllegalArgumentException("The size of Key Array and toPut Array must be same.");
        for(int i = 0; i < keyArray.length; i++) {
            String arg = keyArray[i];
            if(map.get(arg) == null || map.get(arg).toString().trim().equals("")) {
                map.put(arg, toPut[i]);
            }
        }
    }

    public static boolean isVoid(String str){
        return str == null || str.equals("");
    }


    public static Pair<Integer, Integer> extractIndice(DataMap map){
        int page = 1;
        int limit = 20;
        if(isValid(map, "page")) page = map.getInt("page");
        if(page == 0 || page < 0) page = 1;
        if(isValid(map, "limit")) limit = map.getInt("limit");
        Pair<Integer, Integer> pair = new ImmutablePair<>(page, limit);

        return pair;
    }

}
