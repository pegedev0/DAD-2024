package es.us.lsi.dad;

import java.util.Objects;

public class LedActuator {
	
	Integer LEDid;
	Integer LEDstate;
	Integer LEDintensity;
	
	
	@Override
	public String toString() {
		return "LedActuator [LEDid=" + LEDid + ", LEDstate=" + LEDstate + ", LEDintensity=" + LEDintensity + "]";
	}
	
	
	public LedActuator(Integer lEDid, Integer lEDstate, Integer lEDintensity) {
		super();
		LEDid = lEDid;
		LEDstate = lEDstate;
		LEDintensity = lEDintensity;
	}
	

	public Integer getLEDid() {
		return LEDid;
	}
	
	public Integer getLEDstate() {
		return LEDstate;
	}
	
	public void setLEDstate(Integer lEDstate) {
		LEDstate = lEDstate;
	}
	
	public Integer getLEDintensity() {
		return LEDintensity;
	}
	
	public void setLEDintensity(Integer lEDintensity) {
		LEDintensity = lEDintensity;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(LEDid, LEDintensity, LEDstate);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LedActuator other = (LedActuator) obj;
		return Objects.equals(LEDid, other.LEDid) && Objects.equals(LEDintensity, other.LEDintensity)
				&& Objects.equals(LEDstate, other.LEDstate);
	}
	
}
