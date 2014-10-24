package com.apocalypsedefense.core;

public class PrintLogAdapter implements LogAdapter {

	@Override
	public void d(String tag, String msg) {
		System.out.println(String.format("%s (%s)", msg, tag));
	}
	
}