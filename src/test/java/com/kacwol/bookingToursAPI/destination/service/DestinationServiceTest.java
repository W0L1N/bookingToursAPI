package com.kacwol.bookingToursAPI.destination.service;

import com.kacwol.bookingToursAPI.destination.model.Destination;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class DestinationServiceTest {

    @Mock
    private DestinationRepo repo;

    @Mock
    private DestinationMapper mapper;

    @InjectMocks
    private DestinationService service;

    @Test
    public void changeName_shouldWork() {
        Long id = 1L;
        String nameBeforeChange = "nameBefore";
        String description = "description";
        Destination destination = new Destination(id,nameBeforeChange,description);

        String nameAfterChange = "nameAfter";

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(destination));

        ArgumentCaptor<Destination> captor = ArgumentCaptor.forClass(Destination.class);
        service.changeName(id, nameAfterChange);

        Mockito.verify(repo).save(captor.capture());

        Destination expected =  new Destination(id,nameAfterChange, description);
        Destination actual = captor.getValue();

        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void changeDescription_shouldWork() {
        Long id = 1L;
        String name = "name";
        String descriptionBeforeChange = "descriptionBefore";
        Destination destination = new Destination(id,name,descriptionBeforeChange);
        String descriptionAfterChange = "descriptionAfter";

        Mockito.when(repo.findById(id)).thenReturn(Optional.of(destination));

        ArgumentCaptor<Destination> captor = ArgumentCaptor.forClass(Destination.class);
        service.changeDescription(id, descriptionAfterChange);
        Mockito.verify(repo).save(captor.capture());

        Destination expected = new Destination(id, name, descriptionAfterChange);
        Destination actual = captor.getValue();

        Assert.assertEquals(expected.getDescription(), actual.getDescription());
    }
}
