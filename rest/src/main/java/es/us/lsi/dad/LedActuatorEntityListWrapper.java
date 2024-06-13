package es.us.lsi.dad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LedActuatorEntityListWrapper {
	
	private List<LedActuatorEntity> LedList;

	public LedActuatorEntityListWrapper() {
		super();
	}

	public LedActuatorEntityListWrapper(Collection<LedActuatorEntity> LedList) {
		super();
		this.LedList = new ArrayList<LedActuatorEntity>(LedList);
	}
	
	public LedActuatorEntityListWrapper(List<LedActuatorEntity> LedList) {
		super();
		this.LedList = new ArrayList<LedActuatorEntity>(LedList);
	}

	public List<LedActuatorEntity> getLedList() {
		return LedList;
	}

	public void setLedList(List<LedActuatorEntity> LedList) {
		this.LedList = LedList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LedList == null) ? 0 : LedList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LedActuatorEntityListWrapper other = (LedActuatorEntityListWrapper) obj;
		if (LedList == null) {
			if (other.LedList != null)
				return false;
		} else if (!LedList.equals(other.LedList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LedActuatorEntityListWrapper [LedList=" + LedList + "]";
	}


}
