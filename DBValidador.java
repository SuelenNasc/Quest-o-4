public class DBValidador implements Validador, Rollbackable {
    private boolean inserido = false; 

    @Override
    public void validate(ValidationContext context) {
        if (context.getNfe().getNumero().equals("999")) {
            context.addFailure("Falha de DB: NF-e número 999 já existe.");
            return;
        }
        
        context.pushRollbackCommand(this);
        System.out.println("Validando Duplicidade de DB... OK. Inserindo NFe " + context.getNfe().getNumero() + " como 'PENDENTE'.");
        this.inserido = true;
    }

    @Override
    public void rollback(ValidationContext context) {
        if (this.inserido) {
            System.out.println("[ROLLBACK] Desfazendo inserção da NFe " + context.getNfe().getNumero() + " do DB (DELETE ou UPDATE status para 'CANCELADO').");
            this.inserido = false;
        }
    }
}