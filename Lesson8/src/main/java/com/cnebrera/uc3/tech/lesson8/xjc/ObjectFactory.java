//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.23 a las 06:50:25 PM CET 
//


package com.cnebrera.uc3.tech.lesson8.xjc;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cnebrera.uc3.tech.lesson8.xjc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cnebrera.uc3.tech.lesson8.xjc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link StudentLessons }
     * 
     */
    public StudentLessons createStudentLessons() {
        return new StudentLessons();
    }

    /**
     * Create an instance of {@link Lesson }
     * 
     */
    public Lesson createLesson() {
        return new Lesson();
    }

    /**
     * Create an instance of {@link FullTeacherInfo }
     * 
     */
    public FullTeacherInfo createFullTeacherInfo() {
        return new FullTeacherInfo();
    }

    /**
     * Create an instance of {@link TeacherInfo }
     * 
     */
    public TeacherInfo createTeacherInfo() {
        return new TeacherInfo();
    }

}
