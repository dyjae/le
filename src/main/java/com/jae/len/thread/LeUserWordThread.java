package com.jae.len.thread;

import com.jae.len.model.LeUserWord;

public class LeUserWordThread implements Runnable{

	@SuppressWarnings("unused")
	private  LeUserWord uw;
	
	public volatile boolean flag = true;
	
	
	@Override
	public void run() {
		
	}
	
}
