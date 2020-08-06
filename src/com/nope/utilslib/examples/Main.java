package com.nope.utilslib.examples;

import com.nope.utilslib.LogTool;
import com.nope.utilslib.Util;

/**
 * Test file
 * 
 * @author 
 */
public class Main {

 
    static int temp;
    static Util util;
    static LogTool logTool;
   
    public static void main (String [] args) {

        logTool = LogTool.getInstance();
        util = Util.getInstance();
 
        logTool.setLevel(LogTool.DEBUG);
        
        dynamicArrayTest();
    }
    
    private static void logTest(){
        
        logTool.D("debug msg");
        logTool.V("debug msg");
        logTool.I("debug msg");
        logTool.W("debug msg");
        logTool.E("debug msg");
        logTool.F("debug msg");
        
        logTool.D(util.formatFields(logTool, true));
    }
    
    private static void inputTest(){
        int value = util.inputNumber("Input a number");
        util.display(value);
        
        String text = util.readInputString("Input text or nothing", 2, 16, true);
        util.display(text);
        
        text = util.readInputString("Input text only", 2, 16, false);
        util.display(text);
    }
    
    private static void dateTest(){
        
        String dateStr = util.readInputDate("Enter a date ", false);
        util.display(dateStr);
    }
    
    private static void dynamicArrayTest(){
        
        String[] person1 = {"david", "12", "paris"};
        String[] person2 = {"hector", "35", "london"};
        String[] person3 = {"darell", "86", "moscow"};
        String[] person4 = {"sophia", "58", "tokyo"};
        
        // 4 persons and each person has 3 attributes 
        String[][] data = new String[4][3];
        
        data[0] = person1;
        data[1] = person2;
        data[2] = person3;
        data[3] = person4;
        
        String[] columnnNames = {"names", "ages", "cities"};
        
        util.display(
            util.generateDisplayabeTable(columnnNames, data, "test table"));
    }
}