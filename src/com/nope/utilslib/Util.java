package com.nope.utilslib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities ( yes, that's it )
 *
 * @author zvr
 */
public class Util {

    private static Util instance;

    private final String ALPHABET_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String DIGITS_CHARACTERS = "0123456789";
    private final String DATE_FORMAT = "dd/MM/yyyy";
    
    private int linesToClear; 
    private long time;
    private Random rand;
    private InputStreamReader reader;
    private BufferedReader buffer;
    private LogTool logTool;

    private Util() {
        init();
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    /**
     * Initializes the different variables used across this class
     */
    public final void init() {

        time = System.currentTimeMillis();
        rand = new Random();
        reader = new InputStreamReader(System.in);
        buffer = new BufferedReader(reader);
        logTool = LogTool.getInstance();
        linesToClear = 32;
    }

    /**
     * Closes things that need to be
     *
     * @throws IOException
     */
    public void stop() throws IOException {

        buffer.close();
        reader.close();
    }

    /**
     * Sets the current time into a variable so you can use checkTime to see how
     * many time elapsed since startTime() was called<br>
     *
     * This method has no use by itself, you have to use checkTime() afterwards
     * to actually get a result<br>
     *
     * @see checkTime()
     */
    public void startTime() {
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
    public long checkTime() {
        return System.nanoTime() - time;
    }

    /**
     * Executes the method matching exoID, if it does exist<br>
     *
     * @param exoID Name of the exercise to execute
     * @param cls The class where the exercise is taken from
     */
    public void executeExercise(String exoID, Class cls) {

        Method m;
        exoID = exoID.toUpperCase();

        try {
            clear();
            m = cls.getMethod("exo" + exoID);
            clear();
            display("Exercise " + exoID);
            m.invoke(null);

        } catch (NoSuchMethodException e) {

            display("Exercise " + exoID + " does not exist");
            display("Here is a list of available ones ( unsorted ): ");

            for (Method mt : cls.getDeclaredMethods()) {

                if (mt.getName().startsWith("exo")) {
                    System.out.print(mt.getName().replace("exo", "") + "  ");
                }
            }
            display("");
            executeExercise(inputString("Type a number :"), cls);
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            display("Undefined error");
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            display("Error while executing " + exoID);
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prompts the user to press enter<br>
     * Its main use is to pause program execution until the user decides to
     * continue
     */
    public void pressEnter() {
        inputString("Press Enter");
    }

    /**
     * Prompts the user to input a string with a length between minLength and
     * maxLength<br>
     * It keeps asking until a valid string has been received
     * 
     * @param message A message to display for the user
     * @param minLength Minimum string length
     * @param maxLength Maximum string length
     * @param isNullAllowed True if null values (length == 0) are allowed
     * @return The string from user input
     * @see inputString
     */
    public String readInputString(String message, int minLength, int maxLength, boolean isNullAllowed){
        String s = inputString(message+" ("+minLength+"-"+maxLength+") :", isNullAllowed);
        
        if(s == null){
            return s;
        }
        
        while(s.length() < minLength || s.length() > maxLength){
            display("Invalid value");
            s = inputString(message+" ("+minLength+"-"+maxLength+") :");
        }
        return s;
    }
    
    /**
     * Prompts the user to input a string with a length between minLength and
     * maxLength<br>
     * It keeps asking until a valid string has been received
     * Does not allow null (length == 0) values
     * 
     * @param message A message to display for the user
     * @param minLength Minimum string length
     * @param maxLength Maximum string length
     * @return The string from user input
     * @see readInputString(String,int,int,boolean)
     * @see inputString
     */
    public String readInputString(String message, int minLength, int maxLength){
        return readInputString(message, minLength, maxLength, false);
    }
    
    /**
     * Prompts the user to input a number between min and max<br>
     * It keeps asking until a valid number has been received
     * 
     * @param message A message to display for the user
     * @param min Minimum number
     * @param max Maximum number
     * @return The number from user input
     * @see inputNumber
     */
    public int readInputInt(String message, int min, int max){
        int i = inputNumber(message+" ("+min+"-"+max+") :");
        
        while(i < min || i > max){
            display("Invalid value");
            i = inputNumber(message+" ("+min+"-"+max+") :");
        }
        return i;
    }
    
    /**
     * Prompts the user to input a date<br>
     * The date must exist and use the following format :<br>
     * DD/MM/YYYY<br>
     * 
     * @param message Message displayed to the user
     * @param isNullAllowed True if null is allowed
     * @return The date input by the user
     */
    public String readInputDate(String message, boolean isNullAllowed){
        String dateString = readInputString(message, 8, 10, isNullAllowed);
        
        if(dateString == null){
           return dateString;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setLenient(false);
        
        try {
            dateFormat.parse(dateString);
        } catch (ParseException ex) {
            display("Invalid date");
            dateString = readInputString(message+" "+DATE_FORMAT.toUpperCase(), 8, 10, isNullAllowed);
        }
        return dateString;
    }
    /**
     * Prompts the user to input text
     *
     * @param message A message to display for the user
     * @param isNullAllowed True if null values are allowed
     * @return The String from user input
     */
    public String inputString(String message, boolean isNullAllowed) {
        String s = "";

        System.out.println(message + " ");
        try {
            s = buffer.readLine();
        } catch (IOException ex) {
            display("IO Error");
        }
        
        if(isNullAllowed && s.length() == 0){
            return null;
        }
        
        return s;
    }
    /**
     * Prompts the user to input text
     *
     * @param message A message to display for the user
     * @return The String from user input
     */
    public String inputString(String message) {
        return inputString(message, false);
    }

    /**
     * Prompts the user to input a character
     *
     * @param message
     * @return The first character obtained from user input
     */
    public char inputChar(String message) {
        return inputString(message).charAt(0);
    }

    /**
     * Prompts the user to input a number
     *
     * @param message
     * @return Integer from user input
     */
    public int inputNumber(String message) {
        int num;
        
        try {
            num = Integer.parseInt(inputString(message));
            return num;
        } catch (NumberFormatException e) {
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
    public int[] generateIntArray(int length, boolean sorted) {

        int[] array = new int[length];

        for (int i = 0; i < length; i++) {
            array[i] = rand.nextInt(99);
        }
        if (sorted) {
            Arrays.sort(array);
        }
        return array;
    }

    /**
     * @see generateIntArray
     * @param length The length of the array
     * @return An array containing random values
     */
    public int[] generateIntArray(int length) {
        return generateIntArray(length, false);
    }

    /**
     * Generates a random number between min and max included
     *
     * @param min The minium value ( included )
     * @param max The maximum value ( included )
     * @return The generated number
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
        String allowedChars
                = ALPHABET_CHARACTERS
                + ALPHABET_CHARACTERS.toLowerCase()
                + DIGITS_CHARACTERS;

        for (int i = 0; i < length; i++) {
            generatedString += allowedChars.charAt(rand.nextInt(allowedChars.length()));
        }

        return generatedString;
    }

    /**
     * Generates a properly organized table<br>
     * The provided column names must be ordered such as it matches the order in the dataset
     * and its lenght must match the length of a row in the dataset
     * 
     * @param columnNames Names of the displayed columns (in order from left to right)
     * @param data A two-dimensional String array of data to display
     * @param message A message to display above the table
     * @return 
     */
    public String generateDisplayabeTable(
        String[] columnNames, String[][] data, String message){
    
        final int extraSpacesBeforeValue = 1;
        final int extraSpacesAfterValue = 1;
        final char columnSeparator = '|';
        final char columDelimiter = '+';
        final char columnFiller = '-';
        
        int[] longestValues = new int[columnNames.length];
    
        String table = "";
        String encasingLine = "";
        String colLine = "";
        String dataLines = "";
        String dataLine;
        int extraSpacesTmp;
        
        if(columnNames.length != longestValues.length){
            
            table += "\n"+"The table cannot be displayed";
            table += "\n"+"Column and value arrays have different length";
            
            return table;
        }
   
        // Check the longest attribute for each column in every row
        for(int col = 0; col < longestValues.length; col++){
            
             // The min width will be the column name length
            longestValues[col] = columnNames[col].length();
            
            for(int row = 0; row < data.length; row++){
 
                if(data[row][col].length() > longestValues[col]){
                    longestValues[col] = data[row][col].length();
                }
            }
        }
        
        // Create table header
        for(int i = 0; i < columnNames.length; i++){
            
            String subString = "";
            
            // Add extra spaces before column name
            extraSpacesTmp = 0;
            while(extraSpacesTmp++ < extraSpacesBeforeValue){
                subString += " ";
            }
            
            // Add the column name
            subString += columnNames[i];
            
            // Add extra spaces after column name
            extraSpacesTmp = 0;
            while(extraSpacesTmp++ < extraSpacesAfterValue){
                subString += " ";
            }

            // Add spaces to make it match the required length
            while(subString.length() < longestValues[i] + extraSpacesBeforeValue + extraSpacesAfterValue){
                subString += " ";
            }
                
            // Add the column separator after the column name
            subString += columnSeparator;
            
            colLine += subString;
            
            // Fill the lines above and below the column
            for(int j = 0; j < subString.length() - 1; j++){
                encasingLine += columnFiller;
            }
            encasingLine += columDelimiter;
        }
        
        // Add the data
        for(int row = 0; row < data.length; row++){
            
            for(int col = 0; col < longestValues.length; col++){
        
                dataLine = "";
                
                // Add extra spaces before value
                extraSpacesTmp = 0;
                while(extraSpacesTmp++ < extraSpacesBeforeValue){
                    dataLine += " ";
                }

                // Add the value
                dataLine += data[row][col];

                // Add extra spaces after value
                extraSpacesTmp = 0;
                while(extraSpacesTmp++ < extraSpacesAfterValue){
                    dataLine += " ";
                }

                // Add spaces to make it match the required length
                while(dataLine.length() < longestValues[col] + extraSpacesBeforeValue + extraSpacesAfterValue){
                    dataLine += " ";
                }

                // Add the column separator after the value
                dataLine += columnSeparator;
                
                // Add this part to the current line
                dataLines += dataLine;
            }
            dataLines += "\n";
        }
        
        if(message != null){
            table += "\n"+message;
            table += "\n"+message.replaceAll("\\.*.","*");
        }

        table += "\n"+encasingLine;
        table += "\n"+colLine;
        table += "\n"+encasingLine;
        table += "\n"+dataLines;
        table += encasingLine;

        return table;
    }
    
    /**
     * Displays a string
     *
     * @param message The string to display
     */
    public void display(String message) {
        System.out.println(message);
    }

    /**
     * Displays a number
     *
     * @param value The number to display
     */
    public void display(int value) {
        display("" + value);
    }

    /**
     * Displays a number
     *
     * @param array The array to display
     */
    public void display(int[] array) {

        String arrayString = "[";
        for (int i : array) {
            arrayString += " " + i + " ";
        }
        display(arrayString += "]");
    }

    /**
     * Clears the output<br>
     * Actually it just makes new lines
     *
     * @param lines The amount of lines to jump
     */
    public void clear(int lines) {
        for (int i = lines; i > 0; i--) {
            System.out.println("");
        }
    }

    /**
     * Clears the output<br>
     * clear(32)
     */
    public void clear() {
        clear(linesToClear);
    }

    /**
     * Gets, sorts and returns fields of the specified class to use them
     *
     * @param cls The class to get fields from
     * @return An Array of Fields defined in the class
     */
    public Field[] getFields(Class cls) {

        ArrayList<Field> fields = new ArrayList<>();

        // Loops over each of the superclass
        while (cls != Object.class) {
            fields.addAll(Arrays.asList(cls.getDeclaredFields()));
            logTool.D("Added field " + cls.getSimpleName());
            cls = cls.getSuperclass();
        }

        Field[] field = new Field[fields.size()];
        fields.toArray(field);

        //Sorts all fields into alphabetical order
        Arrays.sort(field, new Comparator<Field>() {

            public int compare(Field f1, Field f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });
        return field;
    }

    // TODO : ADD A VERIFICATION IF THE GETTER DOES NOT EXIST FOR THIS FIELD
    /**
     * Attempts to get a value from the given field It only works if the field
     * passed as argument has a getter method
     *
     * @param field The field to get the value from
     * @param object The object this field is from
     * @return The value returned from this field
     */
    public Object getValueFromField(Field field, Object object) {

        // Prepares the method name ( getter ) that matches the current variable we want
        String methodName = "get" + Character.toUpperCase(field.getName().charAt(0))
                + field.getName().substring(1);

        // Attempts to find and invoke the method that will get us the variable's value
        try {
            return object.getClass().getMethod(methodName, (Class[]) null).invoke(object);
        } catch (IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | IllegalAccessException e) {
            logTool.E("There was an error while getting the value from " + field.getName());
            logTool.I(e.getMessage());
            return "could not get the value";
        }
    }

    // TODO : ADD A VERIFICATION IF THE SETTER DOES NOT EXIST FOR THIS FIELD
    /**
     * Attempts to set a value to the specified field if the matching setter
     * does exist
     *
     * @param field The field to set the value to
     * @param object The object this field is from
     * @param value The value to set
     */
    public void setValueToField(Field field, Object object, Object value) {

        // Prepares the method name ( setter ) that matches the current variable we want
        String methodName = "set" + Character.toUpperCase(field.getName().charAt(0))
                + field.getName().substring(1);

        invokeMethod(methodName, object, value);
    }

    /**
     * Retrieves and returns every Object's fields into a String that can be
     * displayed in human-readable format<br>
     * Fields values can only be obtained if they have an associated getter
     *
     *
     * @param object The object to get fields from
     * @param addNewLine Set to true if you want each field on a separate line
     * @return The formatted string with all fields
     */
    public String formatFields(Object object, boolean addNewLine) {

        String formatedString = "";

        for (Field field : getFields(object.getClass())) {
            // Varibale's name
            formatedString += " " + field.getName() + ":";
            // Variable's value
            try {
                formatedString += " " + getValueFromField(field, object);
            } catch (IllegalArgumentException e) {
            }

            if (addNewLine) {
                formatedString += "\n";
            }
        }
        return formatedString;
    }

    /**
     * Prints all fields on the same line<br>
     * Same as : formatFields(object,false)
     *
     * @see formatFields
     * @param object The object to get fields from
     * @return The formatted string with all fields
     */
    public String formatFields(Object object) {
        return formatFields(object, false);
    }

    // TODO : REFACTORING
    /**
     * Attempts to find and invoke the method that will get us the variable's
     * value
     *
     *
     */
    private Object invokeMethod(String method, Object recv, Object arg) {
        Class c = null;

        // Attempts to check if the method contains primitive type args
        try {
            c = (Class) arg.getClass().getField("TYPE").get(null);
        } catch (NoSuchFieldException e) {
            c = arg.getClass();
        } catch (IllegalAccessException | IllegalArgumentException e) {
            logTool.E(e + "");
        }

        try {
            return recv.getClass().getMethod(method, c).invoke(recv, arg);
        } catch (IllegalArgumentException | InvocationTargetException | SecurityException | IllegalAccessException e) {
            logTool.E(e + "");
        } catch (NoSuchMethodException e) {
            logTool.E("Method " + method + "(" + c.getSimpleName() + " o) not found.");
        }

        return null;
    }

    public void setLinesToClear(int linesToClear) {
        this.linesToClear = linesToClear;
    }
}
