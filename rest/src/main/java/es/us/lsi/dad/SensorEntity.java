package es.us.lsi.dad;

import java.util.Objects;

public class SensorEntity {


	protected Integer IdSensor;
	protected Integer idGrupo;
	protected Integer IdPlaca;
	protected Double Record;
	protected Long Time;
	protected Integer TipoSensor;
	
	public SensorEntity(Integer idSensor,Integer idPlaca,Double record, Long time, Integer tipoSensor, Integer idgrupo) {
		super();
		IdSensor = idSensor;
		Record = record;
		Time = time;
		IdPlaca=idPlaca;
		TipoSensor=tipoSensor;
		idGrupo=idgrupo;
		
	}

	public Integer getidGrupo() {
		return idGrupo;
	}
	
	public Integer getIdSensor() {
		return IdSensor;
	}

	public Double getRecord() {
		return Record;
	}

	public Long getTime() {
		return Time;
	}

	public Integer getTipoSensor() {
		return TipoSensor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(IdSensor, Record, Time, TipoSensor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorEntity other = (SensorEntity) obj;
		return Objects.equals(IdSensor, other.IdSensor) && Objects.equals(Record, other.Record)
				&& Objects.equals(Time, other.Time) && Objects.equals(TipoSensor, other.TipoSensor);
	}

	public void setIdSensor(Integer idSensor) {
		IdSensor = idSensor;
	}
	
	public void setidGrupo(Integer idgrupo) {
		idGrupo = idgrupo;
	}

	public void setRecord(Double record) {
		Record = record;
	}

	public void setTime(Long time) {
		Time = time;
	}

	public void setTipoSensor(Integer tipoSensor) {
		TipoSensor = tipoSensor;
	}

	@Override
	public String toString() {
		return "SensorEntity [IdSensor=" + IdSensor + ", idGrupo=" + idGrupo + ", Record=" + Record + ", Time=" + Time
				+ ", TipoSensor=" + TipoSensor + "]";
	}


	
}
