package com.goknur.kelimelik.rest.model;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by goknur on 18.07.2017.
 */

@Parcel
public class Kelime {

    private String kelime;

    List<Option> options;

    public Kelime(String kelime, List<Option> options) {
        this.kelime = kelime;
        this.options = options;
    }

    public Kelime() {
    }

    public String getKelime() {
        return kelime;
    }

    public void setKelime(String kelime) {
        this.kelime = kelime;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }



}
