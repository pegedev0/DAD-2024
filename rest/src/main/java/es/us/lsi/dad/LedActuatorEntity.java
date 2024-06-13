package es.us.lsi.dad;

import java.util.Objects;

public class LedActuatorEntity {
	
	protected Integer LEDid;
	protected Integer IdGroup;
	protected Integer LEDstate;
	protected Integer LEDintensity;
	protected Integer idPlaca;
	protected Long Time;
	public LedActuatorEntity(Integer lEDid, Integer idGroup, Integer lEDstate, Integer lEDintensity, Integer idPlaca,
			Long time) {
		super();
		LEDid = lEDid;
		IdGroup = idGroup;
		LEDstate = lEDstate;
		LEDintensity = lEDintensity;
		this.idPlaca = idPlaca;
		Time = time;
	}
	@Override
	public String toString() {
		return "LedActuatorEntity [LEDid=" + LEDid + ", IdGroup=" + IdGroup + ", LEDstate=" + LEDstate
				+ ", LEDintensity=" + LEDintensity + ", idPlaca=" + idPlaca + ", Time=" + Time + "]";
	}
	public Integer getLEDid() {
		return LEDid;
	}
	public void setLEDid(Integer lEDid) {
		LEDid = lEDid;
	}
	public Integer getIdGroup() {
		return IdGroup;
	}
	public void setIdGroup(Integer idGroup) {
		IdGroup = idGroup;
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
	public Integer getIdPlaca() {
		return idPlaca;
	}
	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}
	public Long getTime() {
		return Time;
	}
	public void setTime(Long time) {
		Time = time;
	}
	@Override
	public int hashCode() {
		return Objects.hash(IdGroup, LEDid, LEDintensity, LEDstate, Time, idPlaca);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LedActuatorEntity other = (LedActuatorEntity) obj;
		return Objects.equals(IdGroup, other.IdGroup) && Objects.equals(LEDid, other.LEDid)
				&& Objects.equals(LEDintensity, other.LEDintensity) && Objects.equals(LEDstate, other.LEDstate)
				&& Objects.equals(Time, other.Time) && Objects.equals(idPlaca, other.idPlaca);
	}
	
}