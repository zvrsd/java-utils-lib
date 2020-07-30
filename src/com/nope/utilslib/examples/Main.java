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
   
    public static void main (String [] args) {

        LogTool lt = LogTool.getInstance();
        Util util = Util.getInstance();
        
        lt.setLevel(LogTool.DEBUG);
        lt.D("debug msg");
        lt.V("debug msg");
        lt.I("debug msg");
        lt.W("debug msg");
        lt.E("debug msg");
        lt.F("debug msg");
        
        lt.D(util.formatFields(lt, true));
        
        int value = util.inputNumber("Input a number");
        util.display(value);
        
        String text = util.readInputString("Input text or nothing", 2, 16, true);
        util.display(text);
        
        text = util.readInputString("Input text only", 2, 16, false);
        util.display(text);
    }
}
