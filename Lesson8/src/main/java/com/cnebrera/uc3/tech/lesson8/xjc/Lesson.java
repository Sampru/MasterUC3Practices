//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.12.14 a las 06:00:28 PM CET 
//


package com.cnebrera.uc3.tech.lesson8.xjc;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para lesson complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="lesson">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lessonFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="lessonTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="lessonName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="lessonTeacher" type="{}fullTeacherInfo"/>
 *         &lt;element name="lessonMark" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lessonId" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lesson", propOrder = {
    "lessonFrom",
    "lessonTo",
    "lessonName",
    "lessonTeacher",
    "lessonMark"
})
public class Lesson {

    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lessonFrom;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lessonTo;
    @XmlElement(required = true)
    protected String lessonName;
    @XmlElement(required = true)
    protected FullTeacherInfo lessonTeacher;
    @XmlElement(required = true)
    protected BigInteger lessonMark;
    @XmlAttribute(name = "lessonId", required = true)
    protected BigInteger lessonId;

    /**
     * Obtiene el valor de la propiedad lessonFrom.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLessonFrom() {
        return lessonFrom;
    }

    /**
     * Define el valor de la propiedad lessonFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLessonFrom(XMLGregorianCalendar value) {
        this.lessonFrom = value;
    }

    /**
     * Obtiene el valor de la propiedad lessonTo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLessonTo() {
        return lessonTo;
    }

    /**
     * Define el valor de la propiedad lessonTo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLessonTo(XMLGregorianCalendar value) {
        this.lessonTo = value;
    }

    /**
     * Obtiene el valor de la propiedad lessonName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * Define el valor de la propiedad lessonName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLessonName(String value) {
        this.lessonName = value;
    }

    /**
     * Obtiene el valor de la propiedad lessonTeacher.
     * 
     * @return
     *     possible object is
     *     {@link FullTeacherInfo }
     *     
     */
    public FullTeacherInfo getLessonTeacher() {
        return lessonTeacher;
    }

    /**
     * Define el valor de la propiedad lessonTeacher.
     * 
     * @param value
     *     allowed object is
     *     {@link FullTeacherInfo }
     *     
     */
    public void setLessonTeacher(FullTeacherInfo value) {
        this.lessonTeacher = value;
    }

    /**
     * Obtiene el valor de la propiedad lessonMark.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLessonMark() {
        return lessonMark;
    }

    /**
     * Define el valor de la propiedad lessonMark.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLessonMark(BigInteger value) {
        this.lessonMark = value;
    }

    /**
     * Obtiene el valor de la propiedad lessonId.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLessonId() {
        return lessonId;
    }

    /**
     * Define el valor de la propiedad lessonId.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLessonId(BigInteger value) {
        this.lessonId = value;
    }

}
