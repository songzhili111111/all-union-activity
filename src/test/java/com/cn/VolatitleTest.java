package com.cn;

public class VolatitleTest {
  
	volatile static int race = 0;
	
	volatile static boolean stop = false;
	
	static void doStop(){
		stop = true;
	}
	
	static void doService(){
		while(!stop){
			System.out.println("songzhili");
		}
	}
	private synchronized static void increase(){
		race++;
	}
	
	public static void main(String[] args) {
		
		for(int t=0;t<20;t++){
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int index=0;index<100;index++){
						increase();
					}
				}
			});
			thread.start();
		}
		if(Thread.activeCount() > 0){
			Thread.yield();
		}
		System.out.println(race);
	}
}
