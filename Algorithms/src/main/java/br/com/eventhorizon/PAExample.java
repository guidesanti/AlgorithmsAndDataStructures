package br.com.eventhorizon;

import br.com.eventhorizon.common.pa.PA;

public class PAExample implements PA {

//    @Override
//    public void trivialSolution() {
//        System.out.println("OK");
//    }

    @Override
    public void finalSolution() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("OK");
    }
}
