import java.util.ArrayList;
import java.util.List;

public class ValidationService {
    
    private List<Validador> chain;
    private static final int CIRCUIT_BREAKER_LIMIT = 3;

    public ValidationService() {
        this.chain = new ArrayList<>();
        this.chain.add(new XMLValidador());
        this.chain.add(new CertificadoValidador());
        this.chain.add(new RegraFiscalValidador());
        this.chain.add(new DBValidador());
        this.chain.add(new SEFAZValidador());
    }

    public List<String> validate(NFe nfe) {
        ValidationContext context = new ValidationContext(nfe);
        System.out.println("======================================================");
        System.out.println("Iniciando validação da NFe: " + nfe.getNumero());
        System.out.println("======================================================");

        for (Validador validador : chain) {
            
            if (context.getFailureCount() >= CIRCUIT_BREAKER_LIMIT) {
                System.out.println("!!! CIRCUIT BREAKER ATIVADO !!!");
                context.addFailure("Cadeia interrompida: Limite de " + CIRCUIT_BREAKER_LIMIT + " falhas atingido.");
                break;
            }

            boolean isCondicional = (validador instanceof RegraFiscalValidador) || 
                                    (validador instanceof DBValidador) || // DBValidador também é condicional
                                    (validador instanceof SEFAZValidador);
            
            if (isCondicional && context.hasFailures()) {
                System.out.println("[CADEIA] Pulando " + validador.getClass().getSimpleName() + " (condicional) devido a falhas anteriores.");
                continue;
            }

            System.out.println("--- Executando: " + validador.getClass().getSimpleName());
            try {
                validador.validate(context);
            } catch (Exception e) {
                context.addFailure("Falha inesperada em " + validador.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        if (context.hasFailures()) {
            System.out.println("\n!!! VALIDAÇÃO FALHOU. Iniciando Rollbacks... !!!");
            context.executeRollbacks();
        } else {
            System.out.println("\n--- VALIDAÇÃO CONCLUÍDA COM SUCESSO ---");
        }
        
        System.out.println("======================================================\n");
        return context.getFailures();
    }
}