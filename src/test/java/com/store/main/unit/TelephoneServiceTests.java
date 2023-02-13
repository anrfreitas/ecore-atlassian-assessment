package com.store.main.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.store.entities.Customer;
import com.store.entities.Telephone;
import com.store.exceptions.NotFoundException;
import com.store.repositories.CustomerRepository;
import com.store.repositories.TelephoneRepository;
import com.store.services.TelephoneService;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TelephoneServiceTests {

    @InjectMocks
    private TelephoneService telephoneService;

    @Mock
    private CustomerRepository cRepository;

    @Mock
    private TelephoneRepository tRepository;

    @Captor
    ArgumentCaptor<Telephone> telephoneCaptor;

    @Test
    @DisplayName("should add telephone successfully")
    void addTelephoneSuccessfully() {
        // arrange / setup
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("john doe");
        customer.setEmail("john@doe.com");

        Telephone telephone = new Telephone();
        telephone.setId(1L);
        telephone.setPhone("99999999");

        Mockito.when(cRepository.findById(1L)).thenReturn(Optional.of(customer));

        // action
        telephoneService.addTelephone(1L, telephone);

        // assertions
        Mockito.verify(cRepository).findById(1L);
        // Mockito.verify(tRepository).save(telephone);

        Mockito.verify(tRepository).save(telephoneCaptor.capture());
        Telephone savedTelephone = telephoneCaptor.getValue();

        Assertions.assertThat(savedTelephone.getCustomer()).isNotNull();
        Assertions.assertThat(savedTelephone.getCustomer()).isSameAs(customer);
    }

    @Test
    @DisplayName("should failed on add telephone")
    void addTelephoneFailed() {
        // arrange / setup
        Telephone telephone = new Telephone();
        telephone.setId(1L);
        telephone.setPhone("99999999");

        Mockito.when(cRepository.findById(null)).thenThrow(NotFoundException.class);

        // action + assertions
        assertThrows(NotFoundException.class, () -> {
            telephoneService.addTelephone(null, telephone);
        });
    }
}
