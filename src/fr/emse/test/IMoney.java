package fr.emse.test;

public interface IMoney {
    IMoney add(IMoney aMoney);

    // double dispatch methods
    IMoney addMoney(Money m);
    IMoney addMoneyBag(MoneyBag bag);
}
