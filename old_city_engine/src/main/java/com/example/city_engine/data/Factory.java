package com.example.city_engine.data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="factories")
public class Factory {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int level = 1;
    @Column(name="user_id")
    private Long userId = Long.valueOf(0);

    @ManyToOne
    @JoinColumn(name="city_id")
    private City city;

    @OneToMany(mappedBy="factory")
    private Operation[] operations;

    // Create one basic test factory with a basic test operation.
    public static Factory Create() 
    {
        var factory = new Factory();
        factory.setName("Factory");
        factory.setOperations(new Operation[]{Operation.CreateGenerator(), Operation.Create()});
        return factory;
    }
}


