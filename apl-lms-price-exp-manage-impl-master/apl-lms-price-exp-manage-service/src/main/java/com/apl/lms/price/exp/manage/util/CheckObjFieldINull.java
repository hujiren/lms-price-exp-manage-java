package com.apl.lms.price.exp.manage.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author hjr start
 * @Classname CheckObjFieldINull
 * @Date 2020/8/31 20:57
 */
@Component
public class CheckObjFieldINull {
    
    public boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {

        boolean isNull = false;
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.get(obj) == null){
                isNull = true;
            }
        }
        return isNull;
    }
}
