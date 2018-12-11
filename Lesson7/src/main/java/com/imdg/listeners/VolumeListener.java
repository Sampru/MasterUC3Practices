package com.imdg.listeners;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapListener;
import com.imdg.pojos.MarketOrder;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Sobremesa on 31/10/2016.
 */
public class VolumeListener implements EntryAddedListener<String, MarketOrder>, EntryUpdatedListener<String, MarketOrder>, Serializable {
    private String instrumentoAControlar;
    private int volumenAcumulado = 0;
    private HashMap<String, MarketOrder> ordenes;

    public VolumeListener(String instrument) {
        this.instrumentoAControlar = instrument;
        this.ordenes = new HashMap<>();
    }

    /**
     * Escuchar entradas que se añaden y sumarlo al volumen/imprimir alerta si llegamos a 30000
     *
     * @param entryEvent
     */
    @Override
    public void entryAdded(EntryEvent<String, MarketOrder> entryEvent) {
        MarketOrder mo = entryEvent.getValue();

          /* Almacenar orden y añadir unidades */
        if (mo.getInstrument().equals(this.instrumentoAControlar)) {
            this.volumenAcumulado += mo.getVolume();
            this.ordenes.put(entryEvent.getKey(), mo);
        }

          /* Comprobar si se ha excedido */
        if (this.volumenAcumulado > 30000){
            System.out.println("Se han acumulado 30000 unidades.");
            this.volumenAcumulado = 0;
        }
    }

    /**
     * Escuchar entradas que se añaden, restar valor antiguo y
     * sumar el nuevo al volumen/imprimir alerta si llegamos a 30000
     *
     * @param entryEvent
     */
    @Override
    public void entryUpdated(EntryEvent<String, MarketOrder> entryEvent) {
        MarketOrder mo = entryEvent.getValue();
        MarketOrder oldMo = this.ordenes.get(entryEvent.getKey());

        /* Actualizar orden y añadir unidades */
        if (mo.getInstrument().equals(this.instrumentoAControlar)) {
            this.volumenAcumulado += (mo.getVolume() - oldMo.getVolume());
            this.ordenes.put(entryEvent.getKey(), mo);
        }

        /* Comprobar si se ha excedido */
        if (this.volumenAcumulado > 30000){
            System.out.println("Se han acumulado 30000 unidades.");
            this.volumenAcumulado = 0;
        }
    }
}
