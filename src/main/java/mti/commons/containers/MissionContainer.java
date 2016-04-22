package mti.commons.containers;

import java.util.ArrayList;
import java.util.List;

import mti.commons.model.Mission;

public class MissionContainer {
	Mission mission;
	ArrayList<JobContainer> jobContainerList;

	public MissionContainer() {
		mission = null;
		jobContainerList = new ArrayList<JobContainer>();
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public List<JobContainer> getJobContainers() {
		return jobContainerList;
	}

	public void setJobContainers(List<JobContainer> jobContainers) {
		this.jobContainerList = new ArrayList<JobContainer>(jobContainers);
	}

	public void addJobContainer(JobContainer jobContainer) {
		jobContainerList.add(jobContainer);
	}

	public int getNumberOfDots() {
		int numberOfDots = 0;
		for (int i = 0; i < jobContainerList.size(); i++) {
			numberOfDots += jobContainerList.get(i).getNumberOfDots();
		}
		return numberOfDots;
	}

	public int getNumberOfJobs() {
		return jobContainerList.size();
	}

	public int getNumberOfDwells() {
		int numberOfDwells = 0;
		for (int i = 0; i < jobContainerList.size(); i++) {
			numberOfDwells += jobContainerList.get(i).getNumberOfDwells();
		}
		return numberOfDwells;
	}
}
