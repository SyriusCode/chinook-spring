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

    @Column(name = "CustomerId")
    private Integer idCliente;

    @Column(name = "InvoiceDate")
    private LocalDateTime fecha;

    @Column(name = "Total")
    private Float total;

}
