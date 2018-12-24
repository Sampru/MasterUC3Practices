package com.cnebrera.uc3.tech.lesson9;

import com.cnebrera.uc3.tech.lesson9.jaxb.JaxbSerializer;
import com.cnebrera.uc3.tech.lesson9.json.JsonSerializer;
import com.cnebrera.uc3.tech.lesson9.kryo.KryoSerializer;
import com.cnebrera.uc3.tech.lesson9.model.ReferenceData;
import com.cnebrera.uc3.tech.lesson9.proto.Lesson9;
import com.cnebrera.uc3.tech.lesson9.proto.ProtoSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main Class that measure the performance
 */
public class Measurement {
    private static long NUM_ITERATIONS = 100000;
    /**
     * a org.slf4j.Logger with the instance of this class given by org.slf4j.LoggerFactory
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(Measurement.class);

    private final static JaxbSerializer jaxbSerializer = new JaxbSerializer();
    private final static JsonSerializer jsonSerializer = new JsonSerializer();
    private final static KryoSerializer kryoSerializer = new KryoSerializer();
    private final static ProtoSerializer protoSerializer = new ProtoSerializer();

    public static void main(String[] args) throws URISyntaxException, IOException {


        //Read the info from a xml and populate the class

        URL url = Measurement.class.getClassLoader().getResource("Example.xml");
        URL urlJson = Measurement.class.getClassLoader().getResource("Example.json");

        String str = new String(Files.readAllBytes(Paths.get(url.toURI())));
        String json = new String(Files.readAllBytes(Paths.get(urlJson.toURI())));

        ReferenceData referenceData = jaxbSerializer.deserialize(str);

        LOGGER.debug("[Practica 1] Size of referenceData instrument list {}", referenceData.getListOfInstruments().size());
        LOGGER.debug("[Practica 1] Algorithm identifier{}", referenceData.getAlgorithmIdentifier());
        LOGGER.debug("[Practica 1] Algorithm marketId{}", referenceData.getMarketId());

        LOGGER.debug("[Practica 2] Json Serializer [{}] ", referenceData.equals(jsonSerializer.deserialize(json)));

        Lesson9.ReferenceData.Builder referenceDataBuilder = Lesson9.ReferenceData.newBuilder();

        // Set the parameters in the builder using the values read in referenceData from JSON to ensure both have the same contents
        referenceDataBuilder.setMarketId(referenceData.getMarketId())
                .setAlgorithmIdentifier(referenceData.getAlgorithmIdentifier());
        referenceData.getListOfInstruments().forEach(i -> referenceDataBuilder.addInstrument(Lesson9.Instrument.newBuilder()
                .setInstrumentId(i.getInstrumentId())
                .setSymbol(i.getSymbol()))
        );

        //Test Proto
        Lesson9.ReferenceData referenceDataProto = referenceDataBuilder.build();
        LOGGER.debug("[Practica 3] Proto Serializer [{}] ", referenceDataProto.equals(protoSerializer.deserialize(protoSerializer.serialize(referenceDataProto))));

        //Test Kryo
        LOGGER.debug("[Practica 4] Kryo Serializer [{}] ", referenceData.equals(kryoSerializer.deserialize(kryoSerializer.serialize(referenceData))));

        // Heat up
        heatUp(referenceData, referenceDataProto, str, jsonSerializer.serialize(referenceData), kryoSerializer.serialize(referenceData), referenceDataProto.toByteArray());

        //Test performance serialization
        testPerformanceSerialization(referenceData, referenceDataProto);

        //Test performance deserialization
        testPerformanceDeSerialization(str, jsonSerializer.serialize(referenceData), kryoSerializer.serialize(referenceData), referenceDataProto.toByteArray());

        //Test performance serialization and deserialization
        testPerformanceSerializationAndDeserialization(referenceData, referenceDataProto);
    }

    private static void testPerformanceSerialization(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto) {
        byte[] jaxbOut = {},
                jsonOut = {},
                kryoOut = {},
                protoOut = {};
        // JAXB serialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jaxbOut = jaxbSerializer.serialize(referenceData).getBytes();
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni) / NUM_ITERATIONS;

        // Json serialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jsonOut = jsonSerializer.serialize(referenceData).getBytes();
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni) / NUM_ITERATIONS;

        // Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            protoOut = protoSerializer.serialize(referenceDataProto);
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni) / NUM_ITERATIONS;

        // Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            kryoOut = kryoSerializer.serialize(referenceData);
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni) / NUM_ITERATIONS;

        System.out.println("/*********************\\ Serialization /*********************\\");
        String format = "%-3s:\t\tTime : %-15dSize : %d%n";
        System.out.printf(format, "JAXB", meanJaxb, jaxbOut.length);
        System.out.printf(format, "JSON", meanJson, jsonOut.length);
        System.out.printf(format, "Proto", meanProto, protoOut.length);
        System.out.printf(format, "Kryo", meanKryo, kryoOut.length);
    }

    private static void testPerformanceDeSerialization(String jaxbSerialize, String jsonSerialize, byte[] kryoSerialize, byte[] protoSerialize) {
        // JAXB deserialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jaxbSerializer.deserialize(jaxbSerialize);
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni) / NUM_ITERATIONS;

        // Json deserialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jsonSerializer.deserialize(jsonSerialize);
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni) / NUM_ITERATIONS;

        // Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            protoSerializer.deserialize(protoSerialize);
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni) / NUM_ITERATIONS;

        // Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            kryoSerializer.deserialize(kryoSerialize);
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni) / NUM_ITERATIONS;


        System.out.println("/*********************\\ Deserialization /*********************\\");
        String format = "%-3s:\t\tTime : %d%n";
        System.out.printf(format, "JAXB", meanJaxb);
        System.out.printf(format, "JSON", meanJson);
        System.out.printf(format, "Proto", meanProto);
        System.out.printf(format, "Kryo", meanKryo);
    }

    private static void testPerformanceSerializationAndDeserialization(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto) {
        // JAXB serialization
        long jaxbSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jaxbSerializer.deserialize(jaxbSerializer.serialize(referenceData));
        }
        long jaxbSerializationFin = System.nanoTime();
        long meanJaxb = (jaxbSerializationFin - jaxbSerializationIni) / NUM_ITERATIONS;

        // Json serialization
        long jsonSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            jsonSerializer.deserialize(jsonSerializer.serialize(referenceData));
        }
        long jsonSerializationFin = System.nanoTime();
        long meanJson = (jsonSerializationFin - jsonSerializationIni) / NUM_ITERATIONS;

        // Protocol Buffers serialization
        long protoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            protoSerializer.deserialize(protoSerializer.serialize(referenceDataProto));
        }
        long protoSerializationFin = System.nanoTime();
        long meanProto = (protoSerializationFin - protoSerializationIni) / NUM_ITERATIONS;

        // Kryo serialization
        long kryoSerializationIni = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            kryoSerializer.deserialize(kryoSerializer.serialize(referenceData));
        }
        long kryoSerializationFin = System.nanoTime();
        long meanKryo = (kryoSerializationFin - kryoSerializationIni) / NUM_ITERATIONS;

        System.out.println("/*********************\\ Both /*********************\\");
        String format = "%-3s:\t\tTime : %d%n";
        System.out.printf(format, "JAXB", meanJaxb);
        System.out.printf(format, "JSON", meanJson);
        System.out.printf(format, "Proto", meanProto);
        System.out.printf(format, "Kryo", meanKryo);
    }

    private static void heatUp(ReferenceData referenceData, Lesson9.ReferenceData referenceDataProto, String jaxbSerialize, String jsonSerialize, byte[] kryoSerialize, byte[] protoSerialize) {
        System.out.print("Heating up");
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            if (i % (NUM_ITERATIONS / 10) == 0) System.out.print(".");
            jaxbSerializer.serialize(referenceData);
            jaxbSerializer.deserialize(jaxbSerialize);
            jaxbSerializer.deserialize(jaxbSerializer.serialize(referenceData));
            jsonSerializer.serialize(referenceData);
            jsonSerializer.deserialize(jsonSerialize);
            jsonSerializer.deserialize(jsonSerializer.serialize(referenceData));
            protoSerializer.serialize(referenceDataProto);
            protoSerializer.deserialize(protoSerialize);
            protoSerializer.deserialize(protoSerializer.serialize(referenceDataProto));
            kryoSerializer.serialize(referenceData);
            kryoSerializer.deserialize(kryoSerialize);
            kryoSerializer.deserialize(kryoSerializer.serialize(referenceData));
        }
        System.out.println("\nStarting tests");
    }
}

