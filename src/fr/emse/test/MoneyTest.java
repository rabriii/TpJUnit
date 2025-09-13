package fr.emse.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {

    private IMoney m12CHF;
    private IMoney m14CHF;
    private IMoney m7USD;

    @Before
    public void setUp() {
        m12CHF = new Money(12, "CHF");
        m14CHF = new Money(14, "CHF");
        m7USD = new Money(7, "USD");
    }

    @Test
    public void testSimpleAdd() {
        IMoney expected = new Money(26, "CHF");
        assertEquals(expected, m12CHF.add(m14CHF));
    }

    @Test
    public void testEquals() {
        assertEquals(new Money(12, "CHF"), m12CHF);
        assertNotEquals(m12CHF, m14CHF);
        assertNotNull(m7USD);
    }

    @Test
    public void testMixedSimpleAdd() {
        // [12 CHF] + [7 USD] == {[12 CHF][7 USD]}
        IMoney[] bag = {m12CHF, m7USD};
        IMoney expected = new MoneyBag(bag);
        assertEquals(expected, m12CHF.add(m7USD));
    }
}
