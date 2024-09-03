package torneo;

public class Equipo {
    
    private String nombre;
    private int golesContra;
    private int golesAFavor;
    private int puntos;
    
    public Equipo(int gC, int gF, int puntos, String nom) {
        this.golesContra = gC;
        this.golesAFavor = gF;
        this.nombre = nom;
        this.puntos = puntos;
    }

    public Equipo() {
        this.golesContra = 0;
        this.golesAFavor = 0;
        this.puntos = 0;
        this.nombre = "";
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesContra() {
        return golesContra;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getGolesAFavor() {
        return golesAFavor;
    }

    public void setGolesAFavor(int golesAFavor) {
        this.golesAFavor = golesAFavor;
    }
   
}