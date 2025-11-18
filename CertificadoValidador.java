public class CertificadoValidador implements Validador {
    @Override
    public void validate(ValidationContext context) {
        if (context.getNfe().getCertificado().equals("EXPIRADO")) {
            context.addFailure("Falha no Certificado: Assinatura expirada.");
        } else {
            System.out.println("Validando Certificado Digital... OK.");
        }
    }
}