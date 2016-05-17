package com.strongunion.wireless.controller.ticket;

import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Vote {
	private BlockingQueue<IpInfo> ipInfoQueue;
	private IPCollectTask ipCollectTask;
	private VoteThread voteThread;
	public Vote() {
		ipInfoQueue = new 	LinkedBlockingQueue<IpInfo>() ;
		ipCollectTask  = new IPCollectTask(ipInfoQueue);
		voteThread = new VoteThread(ipInfoQueue);
	}
	
	public void vote(){
		Timer timer = new Timer();
		long delay = 0;
        long period = 1000 * 60 * 60;
        // 每一个小时采集一次ip
        timer.scheduleAtFixedRate(ipCollectTask, delay, period);
        // 开启投票任务
        voteThread.start();
	}
	public static void main(String[] args) {
		Vote vote = new Vote();
		vote.vote();
	}
}
