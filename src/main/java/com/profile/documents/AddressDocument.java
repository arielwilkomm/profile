package com.profile.documents;

import com.profile.records.address.AddressRecord.AddressType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "addresses")
@DynamicUpdate
public class AddressDocument implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;

    @Id
    @Indexed
    private String id = generateId();

    @Indexed
    private String cpf;
    private String street;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private AddressType addressType;

    private String generateId() {
        int sequence = new Random().nextInt(99999) + 1;
        return "adrs-" + (postalCode != null ? postalCode : "00000") + "-" + String.format("%05d", sequence);
    }
}