package org.ecwid.cloner.cloneables;

import java.util.*;

/**
 * овечка Долли для клонирования
 */
public class DollySheep {
    private String name ="";
    private int age = 0;
    private String furColor = "";
    private List<DollySheep> parents= new ArrayList<>(2);
    private Map<String, DollySheep> friends = new HashMap<>(2);

    public DollySheep(String name, int age, String furColor) {
        this.name = name;
        this.age = age;
        this.furColor = furColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }

    public DollySheep() {
    }

    public Map<String, DollySheep> getFriends() {
        if (friends == null) friends = new HashMap<>(2);
        return friends;
    }

    public void setFriends(Map<String, DollySheep> friends) {
        this.friends = friends;
    }

    public String getName() {
        if (name == null) name = "";
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getFurColor() {
        return furColor;
    }

    public List<DollySheep> getParents() {
        if (parents == null) parents = new ArrayList<>(2);
        return parents;
    }

    public void setParents(List<DollySheep> parents) {
        this.parents = parents;
    }

}
