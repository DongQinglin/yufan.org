package me.dongqinglin.flina.rest.data.payload.response;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void should_be_number_in_using_enum() {
        int ordinal = Message.CodeStatus.FAILURE.ordinal();
        System.out.println(ordinal);
    }
}