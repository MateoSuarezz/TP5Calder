package torneo;
import torneo.Equipo;
import java.util.Comparator;

public class PartidosEquipo{
    
    private Equipo equipoX;
    private int puntos;
    private int golesX;
	private int amarillas;
	private int rojas;
    
    public PartidosEquipo(){
    		this.equipoX = null;
    		this.puntos =  0;
    		this.golesX = 0;
    		this.amarillas = 0;
			this.rojas = 0;
    }
    
    public PartidosEquipo (Equipo x, int goles, int amarillas, int rojas, int puntos){
    		this.equipoX = x;
    		this.golesX = goles;
			this.puntos = puntos;
			this.amarillas = amarillas;
			this.rojas = rojas;
    		
    }

	public Equipo getEquipoX() {
		return equipoX;
	}

	public void setEquipoX(Equipo equipoX) {
		this.equipoX = equipoX;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getGolesX() {
		return golesX;
	}

	public void setGolesX(int golesX) {
		this.golesX = golesX;
	}

	public int getAmarillas() {
		return amarillas;
	}

	public void setAmarillas(int amarillas) {
		this.amarillas = amarillas;
	}

	public int getRojas() {
		return rojas;
	}

	public void setRojas(int rojas) {
		this.rojas = rojas;
	}

	
}