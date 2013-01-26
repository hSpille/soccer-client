package de.janchristoph.soccer.connection;

public abstract class NewDataRunnable implements Runnable {
	private String dataset;

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

}
