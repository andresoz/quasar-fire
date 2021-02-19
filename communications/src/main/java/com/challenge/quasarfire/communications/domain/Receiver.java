package com.challenge.quasarfire.communications.domain;

import org.springframework.stereotype.Component;

/**
 * @author Andres Ortiz andresortiz248@gmail.com
 * */
@Component
public class Receiver {
    public Receiver() {}

    //satellites coordinates{x,y}
    final Position kenobi = new Position(-500,-200);
    final Position skywalker = new Position(100,-100);
    final Position sato = new Position(500,100);

    /**
     * Receive the name of a satellite and return its position.
     * @param satelliteName - a string with the satellite name not {@code null}.
     * @return result - the position of the satellite.
     * @throws NullPointerException if satelliteName is null.
     */
    public Position getSatellitePosition(String satelliteName){
        switch (satelliteName){
            case "kenobi": return kenobi;
            case "skywalker": return skywalker;
            case "sato": return sato;
            default: return null;
        }
    }

    /**
     * Receive an array of strings with some null values and return a string with  the message received
     * @param message - a string array containing the message receive by a satellite not {@code null}.
     * @return result - a string with the message receive.
     * @throws NullPointerException if message is null.
     */
    public String getSatelliteMessage(String[] message){
        String result = "";
        for(String word : message){
            if(word != "")
            result += word + " ";
        }
        return result.strip();
    }

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
        for (float distance : distances){
            if(distance <= 0.0f){
                throw new IllegalArgumentException("The distance " + distance + " is not greater than 0");
            }
        }

        // 4[(x2-x1)*(y3-y1)-(y2-y1)*(x3-x1)]
        float denominator = 4 * ((skywalker.getX()-kenobi.getX())*(sato.getY()-kenobi.getY())-(skywalker.getY()-kenobi.getY())*(sato.getX()-kenobi.getX()));

        // 2*(y3-y1)*(d1^2 - d2^2 - x1^2 + x2^2 - y1^2 + y2^2) - 2*(y2-y1)*(d1^2 - d3^2 - x1^2 + x3^2 - y1^2 + y3^2)
        float numeratorX = (float) ( 2 * (sato.getY()-kenobi.getY())*(Math.pow(distances[0],2)-Math.pow(distances[1],2)
                -Math.pow(kenobi.getX(),2)+Math.pow(skywalker.getX(), 2)-Math.pow(kenobi.getY(),2)+Math.pow(skywalker.getY(),2)) -
                2*(skywalker.getY()-kenobi.getY())*(Math.pow(distances[0],2)-Math.pow(distances[2],2)
                        -Math.pow(kenobi.getX(),2)+Math.pow(sato.getX(),2)
                        -Math.pow(kenobi.getY(),2)+Math.pow(sato.getY(),2)));
        
        // 2*(x2-x1)*(d1^2 - d3^2 - x1^2 + x3^2 - y1^2 + y3^2) - 2*(x3-x1)*(d1^2 - d2^2 - x1^2 + x2^2 - y1^2 + y2^2)
        float numeratorY = (float) (2 * (skywalker.getX()-kenobi.getX())*(Math.pow(distances[0],2)-Math.pow(distances[2],2)
                        -Math.pow(kenobi.getX(),2)+Math.pow(sato.getX(),2)-Math.pow(kenobi.getY(),2)+Math.pow(sato.getY(),2)) -
                        2 * (sato.getX()-kenobi.getX())*(Math.pow(distances[0],2)-Math.pow(distances[1],2)
                                -Math.pow(kenobi.getX(),2)+Math.pow(skywalker.getX(),2)
                                -Math.pow(kenobi.getY(),2)+Math.pow(skywalker.getY(),2)));

        float result[] = {round(numeratorX/denominator), round(numeratorY/denominator)};

        //check if the result is possible (if the three circles whose radius are the distances intersect each other)
       if( (int) distances[0] == (int)Math.sqrt(Math.pow(kenobi.getX()-result[0],2)+Math.pow(kenobi.getY()-result[1],2)) &&
               (int)distances[1] == (int)Math.sqrt(Math.pow(skywalker.getX()-result[0],2)+Math.pow(skywalker.getY()-result[1],2)) &&
               (int)distances[2] == (int)Math.sqrt(Math.pow(sato.getX()-result[0],2)+Math.pow(sato.getY()-result[1],2))){
            return result;
        }else {
            return null;
        }
    }

    /**
     * Receive a flout and round it with one decimal precision.
     * @param number - number to be rounded
     * @return result - a flout with one decimal precision.
     */
    private static float round(float number) {
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
