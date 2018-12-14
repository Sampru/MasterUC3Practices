//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.14 a las 06:00:28 PM CET 
//


package com.cnebrera.uc3.tech.lesson8.xjc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para teacherInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="teacherInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="teacherFirstName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="teacherLastName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "teacherInfo", propOrder = {
    "teacherFirstName",
    "teacherLastName"
})
@XmlSeeAlso({
    FullTeacherInfo.class
})
public class TeacherInfo {

    @XmlElement(required = true)
    protected String teacherFirstName;
    @XmlElement(required = true)
    protected String teacherLastName;

    /**
     * Obtiene el valor de la propiedad teacherFirstName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherFirstName() {
        return teacherFirstName;
    }

    /**
     * Define el valor de la propiedad teacherFirstName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherFirstName(String value) {
        this.teacherFirstName = value;
    }

    /**
     * Obtiene el valor de la propiedad teacherLastName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherLastName() {
        return teacherLastName;
    }

    /**
     * Define el valor de la propiedad teacherLastName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherLastName(String value) {
        this.teacherLastName = value;
    }

}
