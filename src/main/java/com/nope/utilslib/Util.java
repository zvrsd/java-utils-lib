package com.nope.utilslib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities ( yes, that's it )
 * 
 * @author kev
 */
public class Util {

    private static Util instance;
    
    private final String ALPHABET_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String DIGITS_CHARACTERS = "0123456789";
    
    private long time;
    private Random rand;
    private InputStreamReader reader;
    private BufferedReader buffer;
    
    private Util(){
        start();
    }
    
    public static Util getInstance(){
        if(instance == null){
            instance = new Util();
        }
        return instance;
    }
    
    /**
     * Initializes the different variables used across this class
     */
    public final void start(){
   
        time = System.currentTimeMillis();
        rand = new Random();
        reader = new InputStreamReader(System.in);
        buffer = new BufferedReader(reader);
    }
    /**
     * Closes things that need to be
     * 
     * @throws IOException 
     */
    public void stop() throws IOException{

        buffer.close();
        reader.close();
    }
    /**
     * Sets the current time into a variable so you can use
     * checkTime to see how many time elapsed since startTime() was called<br>
     * 
     * This method has no use by itself, you have to use checkTime() afterwards
     * to actually get a result<br>
     * 
     * @see checkTime()
     */
    public void startTime(){
        time = System.nanoTime();
    }   
    /**
     * Check how many milliseconds elapsed since startTime() was called<br>
     * 
     * This method has no use by itself, you have to call startTime() first to 
     * actually get a result<br>
     * 
     * @return Time elapsed since startTime() was called ( in milliseconds )
     * @see startTime()
     */
    public long checkTime(){
        return System.nanoTime() - time;
    }
    /**
     * Executes the method matching exoID, if it does exist<br>
     * 
     * @param exoID Name of the exercise to execute
     * @param cls The class where the exercise is taken from
     */
    public void executeExercise(String exoID, Class cls){
        
        Method m;
        exoID = exoID.toUpperCase();
        
        try{
            clear();
            m = cls.getMethod("exo"+exoID);
            clear();
            display("Exercise "+exoID);
            m.invoke(null);
         
        }catch(NoSuchMethodException e){
           
            display("Exercise "+exoID+" does not exist");
            display("Here is a list of available ones ( unsorted ): ");
            
            for(Method mt : cls.getDeclaredMethods()){
            
                if(mt.getName().startsWith("exo")){
                    System.out.print(mt.getName().replace("exo", "")+"  ");
                }
            }
            display("");
            executeExercise(inputString("Type a number :"), cls);
        }catch(IllegalAccessException | IllegalArgumentException ex){
            display("Undefined error");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }catch(InvocationTargetException ex) {
            display("Error while executing "+exoID);
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Prompts the user to press enter<br>
     * Its main use is to pause program execution until the user decides to
     * continue
     */
    public void pressEnter(){
        inputString("Press Enter to go back");
    }
    /**
     * Prompts the user to input text
     * 
     * @param message
     * @return String from user input
     */
    public String inputString(String message){
        String s = "";
        
        System.out.println(message+" ");
        try {
            s = buffer.readLine();
        } catch (IOException ex) {
            display("IO Error");
        }
        return s;
    }
    /**
     * Prompts the user to input a character
     * 
     * @param message
     * @return The first character obtained from user input
     */
    public char inputChar(String message){
        return inputString(message).charAt(0);
    }  
    /**
     * Prompts the user to input a number
     * 
     * @param message
     * @return Integer from user input
     */
    public int inputNumber(String message){
        int num;
    
        try{
            num = Integer.parseInt(inputString(message));
            return num;
        }catch(NumberFormatException e){
            display("This is not a valid integer");
            return inputNumber(message);
        }
    }
    /**
     * Creates an integer array with random values
     *
     * @param length The length of the array
     * @param sorted If the array into ascending order
     * @return An array containing random values
     */
    public int[] generateIntArray(int length, boolean sorted){
    
        int[] array = new int[length];
        
        for(int i = 0; i < length; i++){   
            array[i] = rand.nextInt(99);
        }
        if(sorted){
            Arrays.sort(array);
        }
        return array;
    }
    /**
     * @see generateIntArray
     * @param length The length of the array
     * @return An array containing random values
     */
    public int[] generateIntArray(int length){
        return generateIntArray(length, false);
    }
    /**
     * 
     */
    public int getRandomNumber(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    /**
     * Generates a string of the specified length
     * 
     * @param length The length of the generated string
     * @return The generated string
    */
    public String generateString(int length) {
        
        String generatedString = "";
        String allowedChars = 
                ALPHABET_CHARACTERS +
                ALPHABET_CHARACTERS.toLowerCase() +
                DIGITS_CHARACTERS;

        for(int i = 0; i < length; i++){
            generatedString += allowedChars.charAt(rand.nextInt(allowedChars.length()));
        }
        
        return generatedString;
    }
    /**
     * Displays a string
     * 
     * @param message The string to display
     */
    public void display(String message){
        System.out.println(message);
    }
    /**
     * Displays a number
     * 
     * @param value The number to display
     */
    public void display(int value){
        display(""+value);
    }
    /**
     * Displays a number
     * 
     * @param array The array to display
     */
    public void display(int[] array){
        
        String arrayString = "[";
        for(int i : array){
            arrayString += " "+i+" ";
        }
        display(arrayString += "]");
    }
    /**
     * Clears the output<br>
     * Actually it just makes new lines
     * @param lines The amount of lines to jump
     */
    public void clear(int lines){
       for(int i = lines; i > 0; i--){
           System.out.println("");
       }
    }
    /**
     * Clears the output<br>
     * clear(32)
     */
    public void clear(){
        clear(32);
    }
}
