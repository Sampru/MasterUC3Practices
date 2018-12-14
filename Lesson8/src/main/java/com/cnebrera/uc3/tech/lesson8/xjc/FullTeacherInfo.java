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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para fullTeacherInfo complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="fullTeacherInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{}teacherInfo">
 *       &lt;sequence>
 *         &lt;element name="teacherCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="teacherCountry" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fullTeacherInfo", propOrder = {
    "teacherCity",
    "teacherCountry"
})
public class FullTeacherInfo
    extends TeacherInfo
{

    @XmlElement(required = true)
    protected String teacherCity;
    @XmlElement(required = true)
    protected String teacherCountry;

    /**
     * Obtiene el valor de la propiedad teacherCity.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherCity() {
        return teacherCity;
    }

    /**
     * Define el valor de la propiedad teacherCity.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherCity(String value) {
        this.teacherCity = value;
    }

    /**
     * Obtiene el valor de la propiedad teacherCountry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherCountry() {
        return teacherCountry;
    }

    /**
     * Define el valor de la propiedad teacherCountry.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherCountry(String value) {
        this.teacherCountry = value;
    }

}
