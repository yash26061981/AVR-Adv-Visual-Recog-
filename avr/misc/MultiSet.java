package com.avanseus.avr.misc;

public class MultiSet{
	private int componentCount;
	private byte[][] sets;

	public MultiSet(int componentCount){
		this.componentCount = componentCount;
		sets = new byte[componentCount][componentCount];
	}

	public int getComponentCount(){
		return componentCount;
	}

	public byte[][] getSets() {
		return sets;
	}

	public void setSets(byte[][] sets) {
		this.sets = sets;
	}

	public void addItem(int bucketNo, byte componentNo){
		int j=0;
		for (j=0; j<componentCount && sets[bucketNo][j] !=0; j++);
		sets[bucketNo][j] = componentNo;
	}

	public int lookupItem(byte componentNo){
		for (int i=0; i<componentCount; i++){
			for (int j=0; j<componentCount && (sets[i][j] != 0); j++){
				if (sets[i][j] == componentNo){
					return i;
				}
			}
		}
		System.out.println("Lookup failed for component number: " + (int)componentNo);
		return -1;
	}

	public void mergeSets(int firstSetNo, int secondSetNo){
		int j;
		for (j=0; (j<componentCount) && (sets[firstSetNo][j] != 0); j++);
		for (int i=0; i<componentCount && (sets[secondSetNo][i] != 0); i++){
			sets[firstSetNo][j] = sets[secondSetNo][i];
			sets[secondSetNo][i] = 0; 
			j = j+1;
		}
	}

	public void lookupAndMergeSets(byte firstComponent, byte secondComponent){
		int firstSetNo = lookupItem(firstComponent);
		int secondSetNo = lookupItem(secondComponent);
		mergeSets(firstSetNo,secondSetNo);
	}

	public int[] denseSetElements(){
		int maxCount = 0, count = 0, denseSetIndicator = 0;
		for (int i=0; i<componentCount; i++){
			count = 0;
			for (int j=0; j<componentCount && (sets[i][j] != 0); j++){
				count++;		
			}
			if (count > maxCount){
				maxCount = count;
				denseSetIndicator = i;
			}
		}
		if (maxCount > 0){
			int[] elementSet = new int[maxCount];
			for (int j=0; j<componentCount && (sets[denseSetIndicator][j] != 0); j++){
				elementSet[j] = sets[denseSetIndicator][j];
			}
			return elementSet;
		}else{
			return null;
		}
	}

	public int countValidSets(){
		int counter = 0;
		for (int i=0; i<componentCount; i++){
			if (sets[i][0] !=0){
				counter++;
			}
		}
		return counter;
	}

	public void printMultiSet(){
		for (int i=0; i<componentCount; i++){
			for (int j=0; j<componentCount; j++){
				System.out.print((int)sets[i][j] + " ");
			}
			System.out.println();
		}
	}

	/*public static void main(String args[]){
		MultiSet multiSet = new MultiSet(3);
		//byte[][] buckets = multiSet.getSets();

		multiSet.addItem(0, (byte)1);
		multiSet.addItem(1, (byte)2);
		multiSet.addItem(2, (byte)3);

		multiSet.mergeSets(0, 1);
		multiSet.mergeSets(0, 2);

		multiSet.printMultiSet();
	}*/
}

