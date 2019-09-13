package io.berivan.couse.unittest;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public interface CreateDomain<T>
{
    T createDomain();

    @Test
    default void createDomainShouldBeImplemented()
    {
        assertNotNull(createDomain());
    }
}
