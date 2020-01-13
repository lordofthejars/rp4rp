package org.acme;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.test.Mock;

@Mock
@ApplicationScoped
public class PaymentStub implements Payment {

    private List<Billing> transactions = new ArrayList<>();

    @Override
    public void pay(Billing billing) {
        transactions.add(billing);
    }
    
    public Optional<Billing> getBillingForCustomerId(Long cusotomerId) {
        return transactions.stream().filter(b -> b.customerId == cusotomerId).findFirst();
    }

    public int size() {
        return transactions.size();
    }

}