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
        man2.getFavoriteBooks().add("Necronomicon");
        Assertions.assertNotEquals(man1, man2);
    }


    @Test
    public void testSheepCloning() {
        DollySheep dolly1 = new DollySheep("Dolly1", 1, "Black");
        DollySheep dolly2 = new DollySheep("Dolly2", 2, "White");
        dolly1.getFriends().put(dolly2.getName(), dolly2);
        dolly2.getParents().add(dolly1);
        dolly1.getParents().add(dolly2);
        DollySheep dolly = Cloner.deepCopy(dolly1);
        Assertions.assertNotNull(dolly);
        Assertions.assertNotEquals(dolly, dolly1);
        Assertions.assertEquals(dolly.getAge(), dolly1.getAge());
        Assertions.assertEquals(dolly.getName(), dolly1.getName());
        Assertions.assertEquals(dolly.getFurColor(), dolly1.getFurColor());
        dolly.getParents().add(new DollySheep("Dolly3", 1, "Gray"));
        Assertions.assertTrue(dolly.getParents().size() > dolly1.getParents().size());
        Assertions.assertNotEquals(dolly.getParents(), dolly1.getParents());
    }
}