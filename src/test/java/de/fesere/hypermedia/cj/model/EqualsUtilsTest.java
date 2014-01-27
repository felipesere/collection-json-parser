package de.fesere.hypermedia.cj.model;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EqualsUtilsTest {

    @Test
    public void test_sameReference_true() {
        String a = "A";

        assertTrue(EqualsUtils.isSameTypeNotNull(a, a));
    }

    @Test
    public void test_noNullsAndSAmeType_true() {
         String a = "A";
         String b = "B";

        assertTrue(EqualsUtils.isSameTypeNotNull(a, b));
    }

    @Test
    public void test_noNullsAndDifferentType_false() {
        String a = "A";
        Integer b = 12;

        assertFalse(EqualsUtils.isSameTypeNotNull(a, b));
    }

    @Test
    public void test_noNullsAndDifferentTypeYetMatchable_false() {
        Integer a = 12;
        Long b = 14L;

        assertFalse(EqualsUtils.isSameTypeNotNull(a, b));
    }

    @Test
    public void test_noNullsAndDifferentPrimitiveType_false() {
        int a = 12;
        long b = 14L;

        assertFalse(EqualsUtils.isSameTypeNotNull(a, b));
    }

    @Test
    public void test_everyBoxIsAShape_true() {
        Shape a = new Shape();
        Box other = new Box();

        assertTrue(EqualsUtils.isSameTypeNotNull(other, a));
    }

    @Test
    public void test_noEveryShapeIsABox_false() {
        Shape a = new Shape();
        Box other = new Box();

        assertFalse(EqualsUtils.isSameTypeNotNull(a, other));
    }

    @Test
    public void test_firstNull_false() {
        String a = null;
        long b = 14L;

        assertFalse(EqualsUtils.isSameTypeNotNull(a, b));
    }

    @Test
    public void test_secondNull_false() {
        long a = 12L;
        String b= null;

        assertFalse(EqualsUtils.isSameTypeNotNull(a, b));
    }


    private class Shape {

    }

    private class Box extends Shape {
    }
}
