package me.dongqinglin;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

class Student {
    private String name;

    public Student setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
       return this.name;
    }
}


public class ArrayListInteger {
    @Test
    public void should_update() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        Integer i1 = 1;
        Integer i2 = 1561;

        Integer i3 = 1136545;
        arrayList.add(i1);
        arrayList.add(i2);
        arrayList.add(i3);

        Integer integer = arrayList.get(1);
        integer = new Integer(154324);
        // arrayList.set(1, integer);
        // Assert.assertEquals(new Integer(3), arrayList.get(1));
    }

    @Test
    public void should_update_in_array() {
        Integer[] integers = {1, 2, 3};
        Integer age = integers[2];
        age = 6;
        System.out.println(integers[2]);
        // Assert.assertEquals(new Integer(6), integers[2]);
    }

    @Test
    public void should_update_name() {
        ArrayList<Student> arrayList = new ArrayList<>();
        arrayList.add( new Student().setName("1234"));
        arrayList.add( new Student().setName("34515"));
        arrayList.add( new Student().setName("123541235"));

        Student student = arrayList.get(2);
        student.setName("立场");

        // Assert.assertEquals("立场", arrayList.get(2).getName());
    }

}
