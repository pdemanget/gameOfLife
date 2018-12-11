package pdemanget.gameoflife.utils;

import java.util.AbstractQueue;
import java.util.Iterator;

public class IntStack extends AbstractQueue<Integer>{
	static final int SIZE=2048;
	int size;
	int[] data = new int[SIZE];

	
	@Override
	public int size() {
		return size;
	}
	
	public void addInt(int value) {
		ensureCapacity(size);
		data[size]=value;
		size++;
	}
	
	private void ensureCapacity(int size2) {
		if(data.length <=size2) grow(data.length+SIZE);
	}

	private void grow(int newLength) {
		int[] copy = new int[newLength];
		System.arraycopy(data, 0, copy, 0,
                Math.min(data.length, newLength));
		data = copy;
		
	}

	public int popInt() {
		size--;
		return data[size];
	}

	@Override
	public boolean offer(Integer e) {
		addInt(e);
		return true;
	}

	@Override
	public Integer poll() {
		
		return popInt();
	}

	@Override
	public Integer peek() {
		return  data[size-1];
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			int cursor=0;

			@Override
			public boolean hasNext() {
				return cursor<size;
			}

			@Override
			public Integer next() {
				int res = data[cursor];
				cursor++;
				return res;
			}
		};
	}

}
