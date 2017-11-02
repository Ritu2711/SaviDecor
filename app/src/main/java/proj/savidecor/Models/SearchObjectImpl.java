package proj.savidecor.Models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sai on 1/11/17.
 */

public class SearchObjectImpl {

    public List getObjectFromList(List ls, String className, String key,
                                  String value) {
        List objLst = ls;
        List result = new ArrayList();
        Iterator it = objLst.iterator();
        Class c = null;
        while (it.hasNext()) {
            Object obj = it.next();
            c = obj.getClass();
            Field[] flds = c.getDeclaredFields();
            String fValue = null;
            for (Field f : flds) {
                if (f.getName().equalsIgnoreCase(key)) {
                    try {
                        fValue = (String) f.get(obj);
                        if (fValue.equalsIgnoreCase(value)) {
                            result.add(obj);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
