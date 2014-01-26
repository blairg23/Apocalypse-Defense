package com.apocalypsedefense.core;

/**
 * Interface for logging so that we can use printlns on PC and Log in Android.
 * @author Pat
 */
public interface LogAdapter {
	/**
	 * Send a DEBUG log message.
	 * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged. 
	 */
	public void d(String tag, String msg);
}