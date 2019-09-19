package io.berivan.couse.unittest;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.Test;

public interface CreateDomain<T>
{
    T createDomain();

    @Contact(Kure.Bireysel)
    @Test
    default void createDomainShouldBeImplemented()
    {
        assertNotNull(createDomain());
    }
}
