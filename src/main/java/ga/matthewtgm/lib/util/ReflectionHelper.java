package ga.matthewtgm.lib.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Makes Java reflection easier.
 */
public class ReflectionHelper {

    /**
     * @param clazz The class to change the field in.
     * @param name  The name of the field.
     * @return The field requested from the class provided.
     * @author MatthewTGM
     */
    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            if (!field.isAccessible())
                field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets the field requested in the class provided.
     *
     * @param clazz    The class to change the field in.
     * @param instance An instance of the class.
     * @param name     The name of the field.
     * @param value    The value to set it to.
     * @author MatthewTGM
     */
    public static <I, V> void setField(Class<?> clazz, I instance, String name, V value) {
        try {
            Field field = getField(clazz, name);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param clazz
     * @param name
     * @param paramTypes
     * @return The method requested in the class provided.
     * @author MatthewTGM
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
        try {
            Method method = clazz.getDeclaredMethod(name, paramTypes);
            if (!method.isAccessible())
                method.setAccessible(true);
            return method;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getAnnotatedMethod(Class<?> clazz, String name, Class<? extends Annotation> annotationClass, Class<?>... paramTypes) {
        try {
            Method method = getMethod(clazz, name, paramTypes);
            return method.isAnnotationPresent(annotationClass) ? method : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}