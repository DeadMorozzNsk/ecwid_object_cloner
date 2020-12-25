package org.ecwid.cloner.cloneables;

import java.util.*;

/**
 * овечка Долли для клонирования
 */
public class DollySheep {
    private String name;
    private int age;
    private String furColor;
    private List<DollySheep> parents;
    private Map<String, DollySheep> friends;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DollySheep that = (DollySheep) o;
        return age == that.age &&
                Objects.equals(name, that.name) &&
                Objects.equals(furColor, that.furColor) &&
                Objects.equals(parents, that.parents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, furColor, parents);
    }
}
