package org.ecwid.cloner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Класс-утилита для копирования объектов
 */
public class Cloner {
    private static final ClassDefiner definer = new ClassDefiner();

    public Cloner() {
    }

    public static <T> T deepCopy(T obj) {
        Map<Object, Object> copies = new HashMap<>(10);
        return deepCopy(obj, copies);
    }

    /**
     * Позволяет сделать глубокую копию объекта (по субъективному мнению автора)
     *
     * @param obj Объект копирования
     * @param <T> Класс копирования
     * @return копия переданного в метод объекта
     */
    private static <T> T deepCopy(T obj, Map<Object, Object> copies) {
        if (obj.getClass().equals(String.class)) return obj;
        try {
            Constructor<T>[] constructors = (Constructor<T>[]) obj.getClass().getDeclaredConstructors();
            Class<?>[] paramTypes = constructors[0].getParameterTypes();
            /* формируем список аргументов первого попавшегося конструктора */
            List<Object> args = new ArrayList<>();
            for (Class<?> cls : paramTypes) {
                if (cls.isPrimitive()) {
                    args.add(0);
                } else if (cls.equals(String.class)) {
                    args.add("");
                } else if (cls.isArray()) {
                    args.add(new Object[10]);
                } else if (cls.equals(List.class)) {
                    args.add(new ArrayList<>());
                } else if (cls.equals(Map.class)) {
                    args.add(new HashMap<>());
                } else if (cls.equals(Set.class)) {
                    args.add(new HashSet<>());
                } else if (cls.equals(Collection.class)) {
                    args.add(new ArrayList<>());
                } else {
                    args.add(new Object());
                }
            }

            T clone = constructors[0].newInstance(args.toArray());
            copies.put(obj, clone);
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (field.getType().isPrimitive()
                        || field.getType().equals(String.class)
                        || field.getType().equals(Boolean.class)
                        || (field.getType().getSuperclass() != null &&
                        field.getType().getSuperclass().equals(Number.class))) {
                    field.set(clone, field.get(obj));
                } else {
                    Object childObj = field.get(obj);
                    definer.define(field.getType());
                    if (childObj == obj || childObj.equals(obj)) {
                        field.set(clone, clone);
                    } else if (definer.isList()) {
                        List<Object> src = (List<Object>) childObj;
                        List<Object> list = new ArrayList<>(src.size());
                        for (Object current : src) {
                            if (copies.containsKey(current)) {
                                list.add(copies.get(current));
                            } else {
                                list.add(deepCopy(current, copies));
                            }
                        }
                        field.set(clone, list);
                    } else if (definer.isArray()) {
                        Object[] objects = (Object[]) childObj;
                        Object[] target = new Object[objects.length];
                        for (int i = 0; i < objects.length; i++) {
                            target[i] = deepCopy(objects[i], copies);
                        }
                        field.set(clone, target);
                    } else if (definer.isSet()) {
                        Set<Object> src = (Set<Object>) childObj;
                        Set<Object> target = new HashSet<>(src.size());
                        for (Object o : src) {
                            if (copies.containsKey(o))
                                target.add(copies.get(o));
                            else
                                target.add(deepCopy(o, copies));
                        }
                        field.set(clone, target);
                    } else if (definer.isMap()) {
                        Map<Object, Object> src = (Map<Object, Object>) childObj;
                        Map<Object, Object> target = new HashMap<>(src.size());
                        src.forEach((k, v) -> {
                            if (copies.containsKey(k)) k = copies.get(k);
                            else k = deepCopy(k, copies);
                            if (copies.containsKey(v)) v = copies.get(v);
                            else v = deepCopy(v, copies);
                            target.put(k, v);
                        });
                        field.set(clone, target);
                    } else {
                        field.set(clone, deepCopy(field.get(obj), copies));
                    }
                }
            }
            return clone;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

/**
 * вспомогательный класс для, надеюсь, упрощения читаемости кода =)
 */
class ClassDefiner {
    private boolean array;
    private boolean list;
    private boolean map;
    private boolean set;
    private boolean collection;

    public void define(Class<?> cl) {
        array = cl.getName().contains("[");
        list = cl.equals(List.class);
        map = cl.equals(Map.class);
        set = cl.equals(Set.class);
        collection = cl.equals(Collection.class);
    }

    public boolean isArray() {
        return array;
    }

    public boolean isList() {
        return list;
    }

    public boolean isMap() {
        return map;
    }

    public boolean isSet() {
        return set;
    }

    public boolean isCollection() {
        return collection;
    }
}