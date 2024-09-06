package torneo;

public class PartidosEquipo {

    private Equipo equipoX;
    private int puntos;
    private int golesX;
    private int golesEnContra;
    private int golesDiferencia;
    private int amarillas;
    private int rojas;

    public PartidosEquipo() {
        this.equipoX = null;
        this.puntos = 0;
        this.golesX = 0;
        this.golesEnContra = 0;
        this.golesDiferencia = 0;
        this.amarillas = 0;
        this.rojas = 0;
    }

    public PartidosEquipo(Equipo x, int goles, int golesEnContra, int amarillas, int rojas, int puntos) {
        this.equipoX = x;
        this.golesX = goles;
        this.golesEnContra = golesEnContra;
        this.golesDiferencia = Math.abs(goles - golesEnContra);
        this.amarillas = amarillas;
        this.rojas = rojas;
        this.puntos = puntos;
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

    public int getGolesEnContra() {
        return golesEnContra;
    }

    public void setGolesEnContra(int golesEnContra) {
        this.golesEnContra = golesEnContra;
    }

    public int getGolesDiferencia() {
        return golesDiferencia;
    }

    public void setGolesDiferencia(int golesDiferencia) {
        this.golesDiferencia = golesDiferencia;
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

    public String getEquipoDatos() {
        return equipoX.getNombre() + "\n Puntaje: " + puntos + "\n Goles: " + golesX + "\n Goles en contra: " + golesEnContra +
                "\n Diferencia de goles: " + golesDiferencia + "\n Tarjetas rojas: " + rojas + "\n Tarjetas amarillas: " + amarillas;
    }
}