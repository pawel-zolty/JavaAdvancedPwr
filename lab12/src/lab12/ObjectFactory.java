//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.05.27 at 06:39:23 PM CEST 
//


package lab12;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the lab12 package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: lab12
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Offer }
     * 
     */
    public Offer createOffer() {
        return new Offer();
    }

    /**
     * Create an instance of {@link Offer.Seller }
     * 
     */
    public Offer.Seller createOfferSeller() {
        return new Offer.Seller();
    }

    /**
     * Create an instance of {@link Offer.Equipment }
     * 
     */
    public Offer.Equipment createOfferEquipment() {
        return new Offer.Equipment();
    }

    /**
     * Create an instance of {@link Offer.Seller.Address }
     * 
     */
    public Offer.Seller.Address createOfferSellerAddress() {
        return new Offer.Seller.Address();
    }

}
