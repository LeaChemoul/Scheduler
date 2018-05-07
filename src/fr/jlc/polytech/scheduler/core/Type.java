package fr.jlc.polytech.scheduler.core;

/**
 * Enumerates the different type for a machine.
 */
public enum Type {
	CPU(new Capacity(8,'G'), new Capacity(40, 'G')),
	GPU(new Capacity(11, 'T'), new Capacity(25, 'T')),
	IO(new Capacity(1,'G'), new Capacity(8, 'G'));
	
	private Capacity CapacityMin;
	private Capacity CapacityMax;
	
	/**
	 * Unique constructor which create an instance of type with a minimal capacity and a maximal capacity
	 * @param CapacityMin Minimal capacity allowed
	 * @param CapacityMax Maximal capacity allowed
	 */
	Type(Capacity CapacityMin, Capacity CapacityMax) {
		setCapacityMin(CapacityMin);
		setCapacityMax(CapacityMax);
	}

	public Capacity getCapacityMax() {
		return CapacityMax;
	}

	public void setCapacityMax(Capacity capacityMax) {
		CapacityMax = capacityMax;
	}

	public Capacity getCapacityMin() {
		return CapacityMin;
	}

	public void setCapacityMin(Capacity capacityMin) {
		CapacityMin = capacityMin;
	}

	@Override
	public String toString() {
		switch (this)
		{
			case CPU:
				return "CPU";
			case GPU:
				return "GPU";
			case IO:
				return "I/O";
			default:
				return this.name().toUpperCase();
				
		}
	}
}
