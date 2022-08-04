# constructor-prototype

This is a sample prototype code to explore the creation of Constructor instead of use fields.

The proposal is to have support to:

```java

@Entity
public class Animal {

    @Id
    private final String id;

    @Column
    private final  String name;

    @Column
    private final  String race;

    @Constructor
    Animal(@Id String id, @Column("name") String name, @Column("race") String race) {
        this.id = id;
        this.name = name;
        this.race = race;
    }
```