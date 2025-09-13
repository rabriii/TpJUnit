package fr.emse.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyBagTest {

    private IMoney m12CHF, m14CHF, m7USD, m21USD;
    private IMoney mb1, mb2;

    @Before
    public void setUp() {
        m12CHF = new Money(12, "CHF");
        m14CHF = new Money(14, "CHF");
        m7USD = new Money(7, "USD");
        m21USD = new Money(21, "USD");

        mb1 = new MoneyBag(new IMoney[]{m12CHF, m7USD});
        mb2 = new MoneyBag(new IMoney[]{m14CHF, m21USD});
    }
    @Test
    public void testSimplification() {
        // Exemple : [12 CHF, 7 USD] + [-12 CHF] = [7 USD] → doit redevenir un Money
        IMoney m12CHF = new Money(12, "CHF");
        IMoney m7USD = new Money(7, "USD");
        IMoney minus12CHF = new Money(-12, "CHF");

        IMoney bag = new MoneyBag(new IMoney[]{m12CHF, m7USD});
        IMoney result = bag.add(minus12CHF);

        assertTrue(result instanceof Money); // doit simplifier en Money
        assertEquals(new Money(7, "USD"), result);
    }

    @Test
    public void testBagEquals() {
        assertEquals(mb1, mb1);            // égal à lui-même
        assertNotEquals(mb1, m12CHF);      // un MoneyBag ≠ un simple Money
        assertNotEquals(m12CHF, mb1);
        assertNotEquals(mb1, mb2);         // mb1 et mb2 sont différents
    }

    @Test
    public void testBagSimpleAdd() {
        // On calcule automatiquement la somme des CHF
        int totalCHF = ((Money)m14CHF).amount() + ((Money)m12CHF).amount();
        int totalUSD = ((Money)m21USD).amount(); // pas de fusion ici, reste inchangé

        IMoney expected = new MoneyBag(new IMoney[]{
                new Money(totalCHF, "CHF"),
                new Money(totalUSD, "USD")
        });

        assertEquals(expected, mb2.add(m12CHF));
    }

    @Test
    public void testSimpleBagAdd() {
        IMoney expected = new MoneyBag(new IMoney[]{m12CHF, m7USD});
        assertEquals(expected, m12CHF.add(m7USD));
    }

    @Test
    public void testBagBagAdd() {
        // Somme automatique pour chaque devise
        int totalCHF = ((Money)m12CHF).amount() + ((Money)m14CHF).amount();
        int totalUSD = ((Money)m7USD).amount() + ((Money)m21USD).amount();

        IMoney expected = new MoneyBag(new IMoney[]{
                new Money(totalCHF, "CHF"),
                new Money(totalUSD, "USD")
        });

        assertEquals(expected, mb1.add(mb2));
    }
}
