package es.us.dad.mysql;

import java.util.Objects;

public class SensorImpl {

	protected Integer idSensor;
	protected Integer idPlaca;
	protected Double record;
	protected Long time;
	protected Integer tipoSensor;
	protected Integer idGrupo;
	
	@Override
	public String toString() {
		return "SensorImpl [idSensor=" + idSensor + ", idPlaca=" + idPlaca + ", record=" + record + ", time=" + time
				+ ", tipoSensor=" + tipoSensor + ", idGrupo=" + idGrupo + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idPlaca, idSensor, record, time, tipoSensor, idGrupo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorImpl other = (SensorImpl) obj;
		return Objects.equals(idPlaca, other.idPlaca) && Objects.equals(idSensor, other.idSensor)
				&& Objects.equals(record, other.record) && Objects.equals(time, other.time)
				&& Objects.equals(tipoSensor, other.tipoSensor) && Objects.equals(idGrupo, other.idGrupo);
	}
	public Integer getIdSensor() {
		return idSensor;
	}
	public void setIdSensor(Integer idSensor) {
		this.idSensor = idSensor;
	}
	public Integer getIdPlaca() {
		return idPlaca;
	}
	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}
	public Double getRecord() {
		return record;
	}
	public void setRecord(Double record) {
		this.record = record;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getTipoSensor() {
		return tipoSensor;
	}
	public void setTipoSensor(Integer tipoSensor) {
		this.tipoSensor = tipoSensor;
	}
	public SensorImpl(Integer idSensor, Integer idPlaca, Double record, Long time, Integer tipoSensor, Integer idGrupo) {
		super();
		this.idSensor = idSensor;
		this.idPlaca = idPlaca;
		this.record = record;
		this.time = time;
		this.tipoSensor = tipoSensor;
		this.idGrupo = idGrupo;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
