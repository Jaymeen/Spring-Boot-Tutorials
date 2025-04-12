package hibernatetutorial;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    public String city;
    public String state;
    public String pincode;

    public Address(String city, String state, String pincode) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", pincode='" + pincode + '\'' + ' ' +
                '}';
    }
}
