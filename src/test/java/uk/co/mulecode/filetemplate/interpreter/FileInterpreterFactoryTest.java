package uk.co.mulecode.filetemplate.interpreter;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class FileInterpreterFactoryTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void fileInterpreterFactory_JsonInterpreter() {

        var factory = new FileInterpreterFactory();
        var fileInterpreter = factory.get("json");

        assertThat(fileInterpreter, is(notNullValue()));
    }

    @Test
    public void fileInterpreterFactory_UnknownInterpreter() {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("No implementation found for the given type: unknown");

        var factory = new FileInterpreterFactory();
        factory.get("unknown");

    }
}