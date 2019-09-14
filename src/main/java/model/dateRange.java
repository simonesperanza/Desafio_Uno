package model;

import java.util.ArrayList;

public class dateRange {
    int id;
    String fechaCreacion;
    String fechaFin;
    ArrayList<String> fechasFaltantes;

    public dateRange(int id, String creacion, String fin, ArrayList<String> fechas){
        this.id = id;
        this.fechaCreacion = creacion;
        this.fechaFin = fin;
        this.fechasFaltantes = fechas;
    }
}
