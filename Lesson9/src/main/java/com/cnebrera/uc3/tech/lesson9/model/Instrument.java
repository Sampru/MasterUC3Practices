package com.cnebrera.uc3.tech.lesson9.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.*;

/**
 * This class represents the instrument info
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "instrument", namespace = "http:/tech.uc3.cnebrera.com/lesson9")
public class Instrument {

    /**
     * a int that identifies the instrument
     */
    @XmlAttribute(name = "instrumentId")
    @JsonProperty(value = "instrument_id")
    private int instrumentId;

    /**
     * human understood representation of the security
     */
    @XmlAttribute(name = "symbol")
    @JsonProperty(value = "symbol")
    private String symbol;


    public int getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Instrument that = (Instrument) o;

        if (instrumentId != that.instrumentId) {
            return false;
        }
        return symbol != null ? symbol.equals(that.symbol) : that.symbol == null;

    }

    @Override
    public int hashCode() {
        int result = instrumentId;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }
}