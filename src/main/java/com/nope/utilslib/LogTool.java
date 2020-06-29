package com.nope.utilslib;

/**
 * Class used for logging purposes, simply prints messages with a prefix<br>
 * <br>
 * LogTool logTool = LogTool.getInstance();<br>
 * logTool.I("info message");<br>
 * <br>
 * All log messages can be globally enabled or disabled with logTool.enable() and logTool.disable()
 * 
 * @author zvr
 */
public class LogTool{
    
	public final static String PREFIX = "";
	
	public final static int FATAL = 1;
	public final static int ERROR = 0;
	public final static int WARNING = -1;
	public final static int INFO = -2;
	public final static int VERBOSE = -3;
	public final static int DEBUG = -4;
	
    private static LogTool instance;
    
	private boolean isEnabled;
	private int level;

    private LogTool(){
        isEnabled = true;
        level = VERBOSE;
    }
    
    /**
     * Generates and/or returns the instance of this class
     * @return The only instance of this class 
     */
    public static LogTool getInstance(){
        if(instance == null){
            instance =  new LogTool();
        }
        return instance;
    }
    
    /**
     * Prints a message prefixed as fatal
     * 
     * @param s The message to print 
     */
	public void F(String s){
		if(isEnabled && level <= FATAL)
			System.out.println("(F) "+s);
	}
    /**
     * Prints a message prefixed as error
     * 
     * @param s The message to print 
     */
	public void E(String s){
		if(isEnabled && level <= ERROR)
			System.out.println("(E) "+s);
	}
    /**
     * Prints a message prefixed as warning
     * 
     * @param s The message to print 
     */
	public void W(String s){
		if(isEnabled && level <= WARNING)
			System.out.println("(W) "+s);
	}
    /**
     * Prints a message prefixed as an info
     * 
     * @param s The message to print 
     */
	public void I(String s){
		if(isEnabled && level <= INFO)
			System.out.println("(I) "+s);
	}
    /**
     * Prints a message prefixed as verbose
     * 
     * @param s The message to print 
     */
	public void V(String s){
		if(isEnabled && level <= VERBOSE)
			System.out.println("(V) "+s);
	}
    /**
     * Prints a message prefixed as debug
     * 
     * @param s The message to print 
     */
	public void D(String s){
		if(isEnabled && level <= DEBUG)
			System.out.println("(D) "+s);
	}
    
    /**
     * Disables log messages
     * 
     */
    public void disable(){
        isEnabled = false;
    }
    public void enable(){
        isEnabled = true;
        
    }
    
    /** 
     * Gets the current logging level
     * @return The currently set logging level
     */
    public int getLevel() {
        return level;
    }
    /**
     * Sets the current logging level<br>
     * If you call setLevel(LogTool.INFO) all log messages called using info
     * level or above will be shown
     * 
     * @param level The logging level 
     */
    public void setLevel(int level){
        this.level = level;
    }
}