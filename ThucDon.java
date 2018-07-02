package com.example.admin.dicho;

/**
 * Created by Admin on 6/28/2018.
 */

public class ThucDon {
    private int IdTD;
    private String TenTD;
    private String GiaTD;

    public ThucDon(int idTD, String tenTD, String giaTD) {
        IdTD = idTD;
        TenTD = tenTD;
        GiaTD = giaTD;
    }

    public int getIdTD() {
        return IdTD;
    }

    public void setIdTD(int idTD) {
        IdTD = idTD;
    }

    public String getTenTD() {
        return TenTD;
    }

    public void setTenTD(String tenTD) {
        TenTD = tenTD;
    }

    public String getGiaTD() {
        return GiaTD;
    }

    public void setGiaTD(String giaTD) {
        GiaTD = giaTD;
    }
}
