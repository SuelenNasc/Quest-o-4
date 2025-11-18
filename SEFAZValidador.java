public class SEFAZValidador implements Validador {
    private final int TIMEOUT_MILLIS = 1000; 

    @Override
    public void validate(ValidationContext context) {
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (context.getNfe().getNumero().equals("SLOW_NFE")) {
            try {
                Thread.sleep(TIMEOUT_MILLIS + 100); 
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }

        if (context.getNfe().getNumero().equals("SLOW_NFE")) {
            context.addFailure("Falha SEFAZ: Timeout de " + TIMEOUT_MILLIS + "ms excedido.");
            return;
        }
        
        if (context.getNfe().getNumero().equals("REJEITADA_SEFAZ")) {
             context.addFailure("Falha SEFAZ: Lote rejeitado.");
             return;
        }

        System.out.println("Validando na SEFAZ (Consulta Online)... OK.");
    }
}