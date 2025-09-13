package fr.emse.test;

import java.util.Objects;

public class Money implements IMoney {
    private int fAmount;
    private String fCurrency;

    public Money(int amount, String currency) {
        fAmount = amount;
        fCurrency = currency;
    }

    public int amount() { return fAmount; }
    public String currency() { return fCurrency; }

    @Override
    public IMoney add(IMoney m) {
        return m.addMoney(this);
    }

    @Override
    public IMoney addMoney(Money m) {
        if (m.currency().equals(currency())) {
            return new Money(amount() + m.amount(), currency());
        }
        return new MoneyBag(new IMoney[]{this, m});
    }

    @Override
    public IMoney addMoneyBag(MoneyBag bag) {
        return bag.addMoney(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Money)) return false;
        Money other = (Money) obj;
        return this.fAmount == other.fAmount && this.fCurrency.equals(other.fCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fAmount, fCurrency);
    }

    @Override
    public String toString() {
        return fAmount + " " + fCurrency;
    }
}
