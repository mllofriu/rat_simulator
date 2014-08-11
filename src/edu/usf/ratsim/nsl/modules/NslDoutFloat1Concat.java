package edu.usf.ratsim.nsl.modules;

import java.util.List;

import nslj.src.lang.NslDinFloat1;
import nslj.src.lang.NslDoutFloat1;
import nslj.src.lang.NslModule;

public class NslDoutFloat1Concat extends NslDoutFloat1 {

	private List<NslDinFloat1> states;
	private int size;

	public NslDoutFloat1Concat(
			NslModule owner,
			String name, int size, List<NslDinFloat1> states) {
		super(owner, name, size);
		
		this.states = states;
		
		this.size = size;
	}

	@Override
	public float get(int pos) {
		int i = 0;
		while (pos - states.get(i).getSize() > 0){
			pos -= states.get(i).getSize();
			i++;
		}
		
		return states.get(i).get(pos);
	}

	@Override
	public int getSize() {
		return size;
	}
	
	
	
	
	

}
