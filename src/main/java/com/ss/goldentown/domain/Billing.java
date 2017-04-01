package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Billing.
 */
@Entity
@Table(name = "billing")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "billing")
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "time")
    private ZonedDateTime time = ZonedDateTime.now();

    @ManyToOne
    private Deal deal;

    @ManyToOne
    private User user;

    @ManyToOne
    private User src;

    @ManyToOne
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Billing price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Billing time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Deal getDeal() {
        return deal;
    }

    public Billing deal(Deal deal) {
        this.deal = deal;
        return this;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public User getUser() {
        return user;
    }

    public Billing user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSrc() {
        return src;
    }

    public Billing src(User user) {
        this.src = user;
        return this;
    }

    public void setSrc(User user) {
        this.src = user;
    }

    public Product getProduct() {
        return product;
    }

    public Billing product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Billing billing = (Billing) o;
        if (billing.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, billing.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Billing{" +
            "id=" + id +
            ", price='" + price + "'" +
            ", time='" + time + "'" +
            '}';
    }
}
