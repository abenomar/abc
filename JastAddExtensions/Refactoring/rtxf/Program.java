//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.14 at 05:10:24 PM GMT 
//


package rtxf;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}cu" maxOccurs="unbounded"/>
 *         &lt;element ref="{}typedecl" maxOccurs="unbounded"/>
 *         &lt;element ref="{}bodydecl" maxOccurs="unbounded"/>
 *         &lt;element ref="{}stmt" maxOccurs="unbounded"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cu",
    "typedecl",
    "bodydecl",
    "stmt"
})
@XmlRootElement(name = "program")
public class Program {

    protected List<Cu> cu;
    protected List<Typedecl> typedecl;
    protected List<Bodydecl> bodydecl;
    protected List<Stmt> stmt;

    /**
     * Gets the value of the cu property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cu property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCu().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Cu }
     * 
     * 
     */
    public List<Cu> getCu() {
        if (cu == null) {
            cu = new ArrayList<Cu>();
        }
        return this.cu;
    }

    /**
     * Gets the value of the typedecl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the typedecl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTypedecl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Typedecl }
     * 
     * 
     */
    public List<Typedecl> getTypedecl() {
        if (typedecl == null) {
            typedecl = new ArrayList<Typedecl>();
        }
        return this.typedecl;
    }

    /**
     * Gets the value of the bodydecl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bodydecl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBodydecl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bodydecl }
     * 
     * 
     */
    public List<Bodydecl> getBodydecl() {
        if (bodydecl == null) {
            bodydecl = new ArrayList<Bodydecl>();
        }
        return this.bodydecl;
    }

    /**
     * Gets the value of the stmt property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the stmt property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Stmt }
     * 
     * 
     */
    public List<Stmt> getStmt() {
        if (stmt == null) {
            stmt = new ArrayList<Stmt>();
        }
        return this.stmt;
    }

}
