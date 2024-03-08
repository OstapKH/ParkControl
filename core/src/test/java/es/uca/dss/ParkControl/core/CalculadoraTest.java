package es.uca.dss.ParkControl.core;
import org.junit.Assert;
import org.junit.Test;

public class CalculadoraTest {
    Calculadora calculadora = new CalculadoraFake();

    @Test
    public void testSuma()
    {
        calculadora.sumar(2,3);
        Assert.assertEquals(5, calculadora.sumar(2,3));

    }
    
}
