public class NFe {
    private final String numero;
    private final String xmlContent;
    private final String certificado;
    private final double valorImpostos;

    public NFe(String numero, String xmlContent, String certificado, double valorImpostos) {
        this.numero = numero;
        this.xmlContent = xmlContent;
        this.certificado = certificado;
        this.valorImpostos = valorImpostos;
    }

    public String getNumero() { return numero; }
    public String getXmlContent() { return xmlContent; }
    public String getCertificado() { return certificado; }
    public double getValorImpostos() { return valorImpostos; }
}