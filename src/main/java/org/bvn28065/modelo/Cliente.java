package org.bvn28065.modelo;

import jakarta.persistence.*;
import lombok.*;

// Entity identifica que esta clase Java es una entidad
@Entity
/* Para que una clase java se convierta en una Entidad tenemos que añadirle:
    - @Entity
    - Identificar el atributo de la clase que es Id
    - Establecer: Getter, Setter, toString y Constructor
*/

// Métodos obligatorios para convertir la clase en Entidad
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Table(name = "customer")
public class Cliente {

    // Obligatorio idenfiticar el ID
    @Id
    // Identificamos que este atributo en la tabla es de tipo AUTO_INCREMENT
    /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    // Le decimos a Springboot como se llama la tabla que contiene ID en la BBDD
    @Column(name = "CustomerId")
    private Integer id;

    @Column(name = "FirstName")
    private String nombre;

    @Column(name = "LastName")
    private String apellidos;
}
