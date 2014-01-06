/*  SCCS - @(#)NslDoubleSync.java	1.5 - 09/01/99 - 00:19:52 */

// Copyright: Copyright (c) 1997 University of Southern California Brain Project.
// Copyright: This software may be freely copied provided the toplevel
// Copyright: COPYRIGHT file is included with each such copy.
// Copyright: Email nsl@java.usc.edu.

package nslj.src.system;



public class NslDoubleSync {
	private boolean msgWait = false;
    
	public synchronized void nslSend() {
		
		//System.out.println("Me bloqueo");
		while (msgWait==true) {
	        	try {
				wait();
			} catch (InterruptedException e) {}
		}
		msgWait=true;
		notify();
		//System.out.println("Me desbloquearon");
	}
	
	public synchronized void nslRecv() {
		while (msgWait==false) {
	        	try {
				wait();
			} catch (InterruptedException e) {}
		}
		msgWait=false;
		notify();
	}
}

