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
    }
}
