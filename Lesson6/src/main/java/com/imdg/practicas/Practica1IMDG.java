package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

public class Practica1IMDG {

    public static void main(String[] args) {
        // Configurar la red para uso local
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        // Instanciar hazelcast y crear una cache
        HazelcastInstance haze = Hazelcast.newHazelcastInstance();
        IMap<String, String> cache = haze.getMap("Lesson6.1");

        // Insertar un dato
        cache.put("Key", "Value");
    }
}
