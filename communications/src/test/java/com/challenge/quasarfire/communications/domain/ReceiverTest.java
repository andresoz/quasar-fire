package com.challenge.quasarfire.communications.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiverTest {

    /**
     * Test to get the location of the message source.
     *
     * @result the location should be received.
     */
    @Test
    public void testGetLocation(){
        Receiver receiver = new Receiver();
        //Random distances
        float distances[]={500f,570f,850f};
        float location[] = receiver.getLocation(distances);
        //check approximate location with the int values
        assertEquals(-332, (int) location[0]);
        assertEquals(271, (int) location[1]);
    }

    /**
     * Test to validate positive distances.
     *
     * @result an {@link IllegalArgumentException} should be triggered.
     */
    @Test
    public void testGetLocationNegativeDistances(){
        Receiver receiver = new Receiver();
        float distances[]={-500f,570f,850f};
        assertThrows(IllegalArgumentException.class, () -> {
            float location[] = receiver.getLocation(distances);
        });
    }

    /**
     * Test to check if there is a solution to the given distances.
     *
     * @result the method should return null.
     */
    @Test
    public void testGetLocationNoPossibleLocation(){
        Receiver receiver = new Receiver();
        //Random distances
        float distances[]={300f,670f,450f};
        assertNull(receiver.getLocation(distances));
    }

    /**
     * Test to rebuild the original message based on the incomplete messages with an offset receive by each satellite.
     *
     * @result the original message should be returned.
     */
    @Test
    public void testGetMessage(){
        Receiver receiver = new Receiver();
        //test messages
        String messages[][]={{"", "this", "is", "a", "message"},{"this", "is", "a", "message"},{"", "", "is", "", "message"}};
        String result = receiver.getMessage(messages);
        assertEquals(new String("this is a message"), result);
    }

    /**
     * Test to synchronized two message with an offset.
     *
     * @result the two messages should be returned synchronized.
     */
    @Test
    public void testRemoveOffset(){
        Receiver receiver = new Receiver();
        //test messages
        String messages[][]={{"", "this", "is", "a", "message"},{"this", "is", "a", "message"}};
        String[][] messagesWithoutOffset = receiver.removeOffset(messages[0],messages[1]);

        assertEquals(messagesWithoutOffset[0].length, messagesWithoutOffset[1].length);
        for (int i=0; i<messagesWithoutOffset[0].length;i++){
            assertEquals(messagesWithoutOffset[0][i], messagesWithoutOffset[1][i]);
        }
    }
}
