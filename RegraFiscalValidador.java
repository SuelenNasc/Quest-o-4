public class RegraFiscalValidador implements Validador {
    @Override
    public void validate(ValidationContext context) {
        if (context.getNfe().getValorImpostos() < 10.0) {
            context.addFailure("Falha Fiscal: Valor de imposto (R$" + context.getNfe().getValorImpostos() + ") abaixo do mínimo.");
        } else {
            System.out.println("Validando Regras Fiscais... OK.");
        }
    }
}