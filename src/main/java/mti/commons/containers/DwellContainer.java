package mti.commons.containers;

import java.util.ArrayList;
import java.util.List;

import mti.commons.model.Dwell;
import mti.commons.model.TargetReport;

public class DwellContainer {
	Dwell dwell;
	ArrayList<TargetReport> targetReportList;

	public DwellContainer() {
		targetReportList = new ArrayList<TargetReport>();
		dwell = null;
	}

	public Dwell getDwell() {
		return dwell;
	}

	public void setDwell(Dwell dwell) {
		this.dwell = dwell;
	}

	public List<TargetReport> getDots() {
		return targetReportList;
	}

	public void setDots(List<TargetReport> dots) {
		this.targetReportList = new ArrayList<TargetReport>(dots);
	}

	public void addDot(TargetReport dot) {
		targetReportList.add(dot);
	}

	public int getNumberOfDots() {
		return targetReportList.size();
	}

}
