package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SensorEntityListWrapper {
	private List<SensorEntity> SensorList;

	public SensorEntityListWrapper() {
		super();
	}
	
	public SensorEntityListWrapper(Collection<SensorEntity> SensorList) {
		super();
		this.SensorList = new ArrayList<SensorEntity>(SensorList);
	}
	
	public SensorEntityListWrapper(List<SensorEntity> SensorList) {
		super();
		this.SensorList = new ArrayList<SensorEntity>(SensorList);
	}

	public List<SensorEntity> getSensorList() {
		return SensorList;
	}

	public void setSensorList(List<SensorEntity> sensorList) {
		SensorList = sensorList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(SensorList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorEntityListWrapper other = (SensorEntityListWrapper) obj;
		return Objects.equals(SensorList, other.SensorList);
	}

	@Override
	public String toString() {
		return "SensorEntityListWrapper [SensorList=" + SensorList + "]";
	}
	
}
