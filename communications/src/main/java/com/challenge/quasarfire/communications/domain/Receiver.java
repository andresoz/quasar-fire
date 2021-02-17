package com.challenge.quasarfire.communications.domain;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
    public Receiver() {}

    //satellites coordinates{x,y}
    final float kenobi[] = {-500,-200};
    final float skywalker[] = {100,-100};
    final float sato[] = {500,100};

    /**
     * Receive an array of floats containing the distances from the satellites to the source of the message,
     * use a trilateration equation to find the position of the transmitter in a plane x,y if the position can
     * not be found, it returns null.
     * @param distances - a float array containing the distance from each satellite to the message source, not {@code null}.
     * @return result - a float array containing the cartesian coordinates of the emitter, if the location can not be found it returns null.
     * @throws IllegalArgumentException if one of the distances is less or equals to zero.
     */
    public float[] getLocation(float[] distances){

        //validate that distances are positive
        for (Float distance : distances){
            if(distance <= 0.0f){
                throw new IllegalArgumentException("The distance " + distance + " is not greater than 0");
            }
        }

        // 4[(x2-x1)*(y3-y1)-(y2-y1)*(x3-x1)]
        Float denominator = 4 * ((skywalker[0]-kenobi[0])*(sato[1]-kenobi[1])-(skywalker[1]-kenobi[1])*(sato[0]-kenobi[0]));

        // 2*(y3-y1)*(d1^2 - d2^2 - x1^2 + x2^2 - y1^2 + y2^2) - 2*(y2-y1)*(d1^2 - d3^2 - x1^2 + x3^2 - y1^2 + y3^2)
        Float numeratorX = (float) ( 2 * (sato[1]-kenobi[1])*(Math.pow(distances[0],2)-Math.pow(distances[1],2)
                -Math.pow(kenobi[0],2)+Math.pow(skywalker[0], 2)-Math.pow(kenobi[1],2)+Math.pow(skywalker[1],2)) -
                2*(skywalker[1]-kenobi[1])*(Math.pow(distances[0],2)-Math.pow(distances[2],2)
                        -Math.pow(kenobi[0],2)+Math.pow(sato[0],2)
                        -Math.pow(kenobi[1],2)+Math.pow(sato[1],2)));
        
        // 2*(x2-x1)*(d1^2 - d3^2 - x1^2 + x3^2 - y1^2 + y3^2) - 2*(x3-x1)*(d1^2 - d2^2 - x1^2 + x2^2 - y1^2 + y2^2)
        Float numeratorY = (float) (2 * (skywalker[0]-kenobi[0])*(Math.pow(distances[0],2)-Math.pow(distances[2],2)
                        -Math.pow(kenobi[0],2)+Math.pow(sato[0],2)-Math.pow(kenobi[1],2)+Math.pow(sato[1],2)) -
                        2 * (sato[0]-kenobi[0])*(Math.pow(distances[0],2)-Math.pow(distances[1],2)
                                -Math.pow(kenobi[0],2)+Math.pow(skywalker[0],2)
                                -Math.pow(kenobi[1],2)+Math.pow(skywalker[1],2)));

        float result[] = {round(numeratorX/denominator), round(numeratorY/denominator)};

        //check if the result is possible (if the three circles whose radius are the distances intersect each other)
       if( (int) distances[0] == (int)Math.sqrt(Math.pow(kenobi[0]-result[0],2)+Math.pow(kenobi[1]-result[1],2)) &&
               (int)distances[1] == (int)Math.sqrt(Math.pow(skywalker[0]-result[0],2)+Math.pow(skywalker[1]-result[1],2)) &&
               (int)distances[2] == (int)Math.sqrt(Math.pow(sato[0]-result[0],2)+Math.pow(sato[1]-result[1],2))){
            return result;
        }else {
            return null;
        }
    }

    public static float round(float number) {
        int pow = 10;
        float tmp = number * pow;
        return ( (float) ( (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) ) ) / pow;
    }

    /**
     * Receive a two-dimensional array containing incomplete messages with an offset from three different satellites,
     * fix the offset between the messages and rebuild the original message from the source.
     * @param messages - a two-dimensional array containing the messages, not {@code null}.
     * @return result - a String containing the original message transmitted from its source.
     * @throws NullPointerException if messages is null.
     */
    public String getMessage(String[][] messages){

        //remove offset from messages
        String[] kenobi = removeOffset(removeOffset(messages[0],messages[1])[0],messages[2])[0];
        String[] skywalker = removeOffset(removeOffset(messages[0],messages[1])[1],messages[2])[0];
        String[] sato = removeOffset(removeOffset(messages[0],messages[1])[0],messages[2])[1];

        String[][] messagesWithoutOffset = new String[][]{kenobi,skywalker,sato};

        String[] temp = new String[kenobi.length];
        //merge the incomplete messages
        for (String[] message : messagesWithoutOffset){
            for (int i = 0; i < message.length; i++){
                if(message[i] != ""){
                    temp[i] = message[i] + " ";
                }
            }
        }
        //build string from array
        String result = "";
        for(String word : temp){
            result += word;
        }
        return result.strip();
    }

    /**
     * Receive two arrays of strings with an offset between them and return both of them synchronized.
     *
     * @param message1 - the first message, not {@code null}.
     * @param message2 - the second message, not {@code null}.
     * @return result - an array containing the synchronized messages, following the form result = {message1,message2}.
     * @throws NullPointerException if either message1 or message2 is null.
     */
    String[][] removeOffset(String[] message1,String[] message2){

        int offset = message1.length - message2.length;
        if(offset > 0){
            String[] temp = new String[message2.length];
            for(int i = offset; i < message1.length; i++){
                temp[i-offset] = message1[i];
            }
            return new String[][]{temp, message2};
        }else if(offset < 0){
            String[] temp = new String[message1.length];
            for(int i = Math.abs(offset); i < message2.length; i++){
                temp[i-Math.abs(offset)] = message2[i];
            }
            return new String[][]{message1, temp};
        }
        else {
            return new String[][]{message1,message2};
        }
    }
}
