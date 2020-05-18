package com.example.assignment1;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainActivityTest {

    ArrayList<bookings> slots = new ArrayList<>();

    @Test
    public void createBookintFail_1() {
        slots.add(new bookings("zzz", "12/02/2020", 10));
        bookings b = new bookings();
        long flg1 = b.createBookint("zzz", "12/02/2020", 10, slots);
        assertEquals(1, flg1, .1);
    }

    @Test
    public void createBookintFail_2() {
        bookings b = new bookings();
        slots.add(new bookings("mmm", "12/02/2020", 10));
        slots.add(new bookings("nnn", "12/02/2020", 10));
        slots.add(new bookings("lll", "12/02/2020", 10));
        slots.add(new bookings("www", "12/02/2020", 10));
        slots.add(new bookings("xxx", "12/02/2020", 10));
        slots.add(new bookings("qqq", "12/02/2020", 10));
        slots.add(new bookings("rrrr", "12/02/2020", 10));
        slots.add(new bookings("zzzzz", "12/02/2020", 10));
        slots.add(new bookings("mxmxm", "12/02/2020", 10));
        slots.add(new bookings("kkk", "12/02/2020", 10));
        long flg2 = b.createBookint("zzz", "12/02/2020", 10, slots);

        assertEquals(2, flg2, .2);

    }
    @Test
    public void createBookintSuccess() {
        slots.add(new bookings("zzz", "12/02/2020", 10));
        bookings b = new bookings();
        long flg0 = b.createBookint("www", "12/02/2020", 10, slots);
        assertEquals(0, flg0, .0);
    }


}