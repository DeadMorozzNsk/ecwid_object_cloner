package org.ecwid.cloner;

import org.ecwid.cloner.cloneables.DollySheep;
import org.ecwid.cloner.cloneables.Man;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CloneLabTest {

    @Test
    public void testListCopy() {
        Man man1= new Man("John", 31, Arrays.asList("1984", "Effective Java. Third Edition"));
        Man man2 = Cloner.deepCopy(man1);
        Assertions.assertNotNull(man2);
        Assertions.assertEquals(man1, man2);
        man2.getFavoriteBooks().add("Necronoicon");
        Assertions.assertNotEquals(man1, man2);
    }


    @Test
    public void testSheepCloning() {
        DollySheep dolly1 = new DollySheep("Dolly1", 1, "Black");
        DollySheep dolly2 = new DollySheep("Dolly2", 2, "White");
        dolly1.getFriends().put(dolly2.getName(), dolly2);
        dolly1.getParents().add(dolly2);
        DollySheep dolly = Cloner.deepCopy(dolly1);
        Assertions.assertNotNull(dolly);
        Assertions.assertEquals(dolly, dolly1);
        dolly.getParents().get(0).setName("Not dolly");
        Assertions.assertNotEquals(dolly, dolly1);
    }
}