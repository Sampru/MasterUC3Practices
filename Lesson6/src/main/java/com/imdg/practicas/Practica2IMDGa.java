package com.imdg.practicas;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICountDownLatch;
import com.hazelcast.core.IMap;
import com.imdg.pojos.Person;

import java.util.concurrent.TimeUnit;

public class Practica2IMDGa {

    public static void main(String[] args) {
        // Configurar la red para uso local
        Config config = new Config();
        config.getNetworkConfig().getJoin().getTcpIpConfig().addMember("localhost").setEnabled(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);

        // Crear persona
        Person person = new Person("Pablo Escobar", 28051, "Calle falsa", "1 2 3");

        // Instanciar hazelcast y crear una cache
        HazelcastInstance haze = Hazelcast.newHazelcastInstance();
        IMap<String, Person> cache = haze.getMap("Lesson6.2");

        // Crear un latch
        ICountDownLatch latch = haze.getCountDownLatch("Lesson6.2");

        try {
            // Darle 3 tokens al latch
            latch.trySetCount(3);

            // Insertar un dato
            cache.put("nodo1", person);

            // Quitar un token
            latch.countDown();
            System.out.println(latch.getCount() + " nodos restantes.");

            // Esperar a demas procesos
            latch.await(100, TimeUnit.SECONDS);

            // Leer los 3 datos.
            System.out.println(cache.get("nodo1").toString());
            System.out.println(cache.get("nodo2").toString());
            System.out.println(cache.get("nodo3").toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            latch.destroy();
        }
    }
}
