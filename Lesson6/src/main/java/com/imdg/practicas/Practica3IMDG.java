package com.imdg.practicas;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class Practica3IMDG {

    public static void main(String[] args) {
        // Instanciar hazelcast Cliente y crear una cache
        ClientConfig config = new ClientConfig();
        ArrayList<String> ips = new ArrayList<>();
        ips.add("127.0.0.1");
        config.getNetworkConfig().setAddresses(ips);

        // Crear un cliente
        HazelcastInstance client = HazelcastClient.newHazelcastClient(config);
        IMap<String, String> cache = client.getMap("Lesson6.3");

        // Generar un string aleatorio
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        // Leer el string
        System.out.println("String: " + cache.get("str"));

        // Poner el string
        cache.put("str", generatedString);

        // Leer el string
        System.out.println("Cached string: " + cache.get("str"));

        // Cerrar el cliente
        client.shutdown();

    }
}
