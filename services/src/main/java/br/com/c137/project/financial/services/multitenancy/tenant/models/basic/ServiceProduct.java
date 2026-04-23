package br.com.c137.project.financial.services.multitenancy.tenant.models.basic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "services_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
