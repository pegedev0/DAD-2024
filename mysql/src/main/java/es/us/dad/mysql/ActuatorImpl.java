package es.us.dad.mysql;

import java.util.Objects;

public class ActuatorImpl {
	
	protected Integer idActuador;
	protected Integer idPlaca;
	protected Integer state;
	protected Integer intensity;
	protected Integer idGrupo;
	
	public ActuatorImpl(Integer idActuador, Integer idPlaca, Integer state, Integer intensity, Integer idGrupo) {
		super();
		this.idActuador = idActuador;
		this.idPlaca = idPlaca;
		this.state = state;
		this.intensity = intensity;
		this.idGrupo = idGrupo;
	}

	@Override
	public String toString() {
		return "ActuatorImpl [idActuador=" + idActuador + ", idPlaca=" + idPlaca + ", state=" + state + ", intensity="
				+ intensity + ", idGrupo=" + idGrupo + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idActuador, idPlaca, intensity, state, idGrupo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActuatorImpl other = (ActuatorImpl) obj;
		return Objects.equals(idActuador, other.idActuador) && Objects.equals(idPlaca, other.idPlaca)
				&& Objects.equals(intensity, other.intensity) && Objects.equals(state, other.state)
				&& Objects.equals(idGrupo, other.idGrupo);
	}

	public Integer getIdActuador() {
		return idActuador;
	}

	public void setIdActuador(Integer idActuador) {
		this.idActuador = idActuador;
	}

	public Integer getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(Integer idPlaca) {
		this.idPlaca = idPlaca;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIntensity() {
		return intensity;
	}

	public void setIntensity(Integer intensity) {
		this.intensity = intensity;
	}
	
}
