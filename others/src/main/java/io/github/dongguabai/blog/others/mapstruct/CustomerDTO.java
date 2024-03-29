package io.github.dongguabai.blog.others.mapstruct;

public class CustomerDTO {
    private String name;
    private String street;
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}