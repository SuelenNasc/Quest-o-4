public class XMLValidador implements Validador {
    @Override
    public void validate(ValidationContext context) {
        if (context.getNfe().getXmlContent() == null || !context.getNfe().getXmlContent().startsWith("<xml>")) {
            context.addFailure("Falha no Schema XML: Conteúdo inválido.");
        } else {
            System.out.println("Validando Schema XML... OK.");
        }
    }
}