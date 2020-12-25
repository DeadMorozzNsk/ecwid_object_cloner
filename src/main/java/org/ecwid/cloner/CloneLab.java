package org.ecwid.cloner;

import org.ecwid.cloner.cloneables.Man;

import java.util.Arrays;

/**
 * Лаборатория по клонированию
 */
public class CloneLab {
    public static void main(String[] args) {
        Man man1= new Man("John", 31, Arrays.asList("1984", "Effective Java. Third Edition"));
        Man man2 = Cloner.deepCopy(man1);
        System.out.println(man1.equals(man2));
    }
}
