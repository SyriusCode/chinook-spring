package org.bvn28065.modelo;


import jakarta.persistence.*;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDateTime;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "invoice")
public class Factura {

    @Id
    @Column(name = "InvoiceId")
    private Integer id;

    @Column(name = "InvoiceDate")
    private LocalDateTime fecha;

    @Column(name = "Total")
    private Float total;

    // Tercer y ultimo paso para convertir una clase a Entidad
    // Este paso solo es necesario si existe una RELACION entre tablas
    @ManyToOne
    private Cliente idCliente;

}
