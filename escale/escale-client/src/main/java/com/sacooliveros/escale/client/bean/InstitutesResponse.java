package com.sacooliveros.escale.client.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Ricardo on 05/06/2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "instituciones")
public class InstitutesResponse implements Serializable{

    @XmlElement(name = "items")
    private List<Institute> items;

    public List<Institute> getItems() {
        return items;
    }

    public void setItems(List<Institute> items) {
        this.items = items;
    }
}
