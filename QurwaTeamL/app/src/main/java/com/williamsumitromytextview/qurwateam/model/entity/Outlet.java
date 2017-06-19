package com.williamsumitromytextview.qurwateam.model.entity;

/**
 * Created by william on 28/05/2017.
 */

public class Outlet {
    private Integer id_Outlet;
    private Integer id_franchise;
    private String namaPTFranchisee;
    private String alamatFranchisee;
    private String contactFranchisee;
    private String Owner_Franchisee;
    private String tahun_berdiri_Franchisee;


    public Integer getId_Outlet() {
        return id_Outlet;
    }

    public void setId_Outlet(Integer id_Outlet) {
        this.id_Outlet = id_Outlet;
    }
    public Integer getId_franchise() {
        return id_franchise;
    }

    public void setId_franchise(Integer id_franchise) {
        this.id_franchise = id_franchise;
    }
    public String getNamaPTFranchisee() {
        return namaPTFranchisee;
    }

    public void setNamaPTFranchisee(String namaPTFranchisee) {
        this.namaPTFranchisee = namaPTFranchisee;
    }

    public String getAlamatFranchisee() {
        return alamatFranchisee;
    }

    public void setAlamatFranchisee(String alamatFranchisee) {
        this.alamatFranchisee = alamatFranchisee;
    }

    public String getContactFranchisee() {
        return contactFranchisee;
    }

    public void setContactFranchisee(String contactFranchisee) {
        this.contactFranchisee = contactFranchisee;
    }
    public String getOwner_Franchisee() {
        return Owner_Franchisee;
    }

    public void setOwner_Franchisee(String owner_Franchisee) {
        Owner_Franchisee = owner_Franchisee;
    }

    public String getTahun_berdiri_Franchisee() {
        return tahun_berdiri_Franchisee;
    }

    public void setTahun_berdiri_Franchisee(String tahun_berdiri_Franchisee) {
        this.tahun_berdiri_Franchisee = tahun_berdiri_Franchisee;
    }
}
