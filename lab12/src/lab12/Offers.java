package lab12;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
public class Offers {

    private List<Offer> offers;

    public List<Offer> getOffers() {
        return offers;
    }

    @XmlElement(name = "offer")
    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return offers.toString();
    }

}