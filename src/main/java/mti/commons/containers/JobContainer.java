package mti.commons.containers;

import java.util.ArrayList;
import java.util.List;

import mti.commons.model.Job;

public class JobContainer {
	Job job;
	ArrayList<DwellContainer> dwellContainerList;

	public JobContainer() {
		dwellContainerList = new ArrayList<DwellContainer>();
		job = null;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public List<DwellContainer> getDwellContainers() {
		return dwellContainerList;
	}

	public void setDwellContainers(List<DwellContainer> dwellContainers) {
		this.dwellContainerList = new ArrayList<DwellContainer>(dwellContainers);
	}

	public void addDwellContainer(DwellContainer dwellContainer) {
		dwellContainerList.add(dwellContainer);
	}

	public int getNumberOfDots() {
		int numberOfDots = 0;
		for (int i = 0; i < dwellContainerList.size(); i++) {
			numberOfDots += dwellContainerList.get(i).getNumberOfDots();
		}
		return numberOfDots;
	}

	public int getNumberOfDwells() {
		return dwellContainerList.size();
	}
}
