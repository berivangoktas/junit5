package io.berivan.couse.unittest;

import io.Contact;
import io.Kure;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTestWithDynamicTest
{

    @TestFactory
    Collection<DynamicTest> dynamicTestsWithCollection()
    {

        return Arrays.asList(
                DynamicTest.dynamicTest("add",
                () -> assertEquals(2, 2)),
                DynamicTest.dynamicTest("multiply",
                () -> assertEquals(2, 2)));


    }


}
