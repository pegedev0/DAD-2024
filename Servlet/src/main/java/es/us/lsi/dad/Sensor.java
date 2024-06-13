package es.us.lsi.dad;

import java.util.Objects;

public class Sensor {

	Integer IdSensor;
	Double Record;
	Long Time;
	String TipoSensor;
	
	public Sensor(Integer idSensor, Double record, Long time, Integer tipoSensor) {
		super();
		IdSensor = idSensor;
		Record = record;
		Time = time;
		
		if(tipoSensor.equals(0)) {
			TipoSensor = "Sensor de Ultrasonido";
		} else if (tipoSensor.equals(1)){
			TipoSensor="Sensor de Temperatura";
		}else {
			TipoSensor=null;
		}
	}

	@Override
	public String toString() {
		return "Sensor [IdSensor=" + IdSensor + ", TipoSensor=" + TipoSensor + ", Time=" + Time + ", Classification="
				+ TipoSensor + "]";
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

	public String getTipoSensor() {
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
		Sensor other = (Sensor) obj;
		return Objects.equals(IdSensor, other.IdSensor) && Objects.equals(Record, other.Record)
				&& Objects.equals(Time, other.Time) && Objects.equals(TipoSensor, other.TipoSensor);
	}
	
	
	
}
