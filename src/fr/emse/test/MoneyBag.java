package fr.emse.test;

import java.util.*;

public class MoneyBag implements IMoney {
    private Set<IMoney> fMonies = new HashSet<>();


    public MoneyBag(IMoney m1, IMoney m2) {
        appendMoney(m1);
        appendMoney(m2);
    }

    public MoneyBag(IMoney[] monies) {
        for (IMoney m : monies) {
            appendMoney(m);
        }
    }

    private void appendMoney(IMoney m) {
        fMonies.add(m);
    }

    @Override
    public IMoney add(IMoney m) {
        return simplify(m.addMoneyBag(this));
    }

    @Override
    public IMoney addMoney(Money m) {
        List<IMoney> combined = new ArrayList<>(fMonies);
        combined.add(m);
        return simplify(new MoneyBag(combined.toArray(new IMoney[0])));
    }

    @Override
    public IMoney addMoneyBag(MoneyBag bag) {
        List<IMoney> combined = new ArrayList<>(fMonies);
        combined.addAll(bag.fMonies);
        return simplify(new MoneyBag(combined.toArray(new IMoney[0])));
    }

    /** Simplifie le MoneyBag en combinant les montants par devise */
    private IMoney simplify(IMoney result) {
        if (!(result instanceof MoneyBag bag)) {
            return result; // si ce n'est pas un MoneyBag, on retourne tel quel
        }

        Map<String, Integer> totalByCurrency = new HashMap<>();

        for (IMoney m : bag.fMonies) {
            if (m instanceof Money money) {
                totalByCurrency.put(money.currency(),
                        totalByCurrency.getOrDefault(money.currency(), 0) + money.amount());
            } else if (m instanceof MoneyBag innerBag) {
                IMoney simplifiedInner = simplify(innerBag);
                if (simplifiedInner instanceof Money sm) {
                    totalByCurrency.put(sm.currency(),
                            totalByCurrency.getOrDefault(sm.currency(), 0) + sm.amount());
                }
            }
        }

        // Créer une liste de Money pour les montants non nuls
        List<IMoney> simplifiedList = new ArrayList<>();
        for (var entry : totalByCurrency.entrySet()) {
            if (entry.getValue() != 0) {
                simplifiedList.add(new Money(entry.getValue(), entry.getKey()));
            }
        }

        // Si une seule monnaie, on retourne directement un Money
        if (simplifiedList.size() == 1) return simplifiedList.get(0);

        return new MoneyBag(simplifiedList.toArray(new IMoney[0]));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MoneyBag)) return false;
        MoneyBag other = (MoneyBag) obj;
        return Objects.equals(fMonies, other.fMonies);
    }

    @Override
    public int hashCode() {
        return fMonies.hashCode();
    }

    @Override
    public String toString() {
        return Arrays.toString(fMonies.toArray());
    }
}
